package com.henrysican.rentaspot.services;

import com.henrysican.rentaspot.dao.BookingRepo;
import com.henrysican.rentaspot.models.Booking;
import com.henrysican.rentaspot.models.Location;
import com.henrysican.rentaspot.models.BookingStatus;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Log
@Service
@Transactional
public class BookingService {
    private final BookingRepo bookingRepo;

    @Autowired
    public BookingService(BookingRepo bookingRepo){
        this.bookingRepo = bookingRepo;
    }

    /**
     * Updates the booking record with the provided booking status.
     * @param bookingId     the ID of the booking record to be updated
     * @param bookingStatus the booking status to be applied to the booking
     */
    public void updateBookingStatus(int bookingId, BookingStatus bookingStatus){
        Booking booking = bookingRepo.getById(bookingId);
        booking.setBookingStatus(bookingStatus);
    }

    /**
     * Updates a location's booking records, with a pending booking status and time since
     * creation over 1 hour, to a booking status of expired.
     * @param locationId    the ID of the location
     * @return              the number of booking records updated
     */
    public long updateExpiredBookingsForLocation(int locationId){
        List<Booking> pendingBookings = bookingRepo.findAllByLocation_IdAndBookingStatusIsLike(locationId,BookingStatus.PENDING);
        List<Booking> expired = pendingBookings.stream()
                .filter(booking -> booking.calculateTimeFromCreate() < 0)
                .peek(booking -> booking.setBookingStatus(BookingStatus.EXPIRED))
                .collect(Collectors.toList());
        return expired.size();
    }

    /**
     * Updates a customer's booking records, with a pending booking status and time since
     * creation over 1 hour, to a booking status of expired.
     * @param customerId    the ID of the customer
     * @return              the number of booking records updated
     */
    public long updateExpiredBookingsForCustomer(int customerId){
        List<Booking> allBookingsForCustomer = getAllBookingsForCustomer(customerId);
        List<Booking> expired = allBookingsForCustomer.stream()
                .filter(booking -> booking.calculateTimeFromCreate() < 0 && booking.getBookingStatus() == BookingStatus.PENDING)
                .peek(booking -> booking.setBookingStatus(BookingStatus.EXPIRED))
                .collect(Collectors.toList());
        return expired.size();
    }

    /**
     * Updates the booking records of the locations, with a pending booking status and time since
     * creation over 1 hour, to a booking status of expired.
     * @param locations    the list of locations
     * @return             the number of booking records updated
     */
    public long updateExpiredBookingsForLocations(List<Location> locations){
        return locations.stream().mapToLong(location -> {
            List<Booking> expired = location.getBookings().stream()
                    .filter(booking -> booking.calculateTimeFromCreate() < 0 && booking.getBookingStatus() == BookingStatus.PENDING)
                    .peek(booking -> booking.setBookingStatus(BookingStatus.EXPIRED))
                    .collect(Collectors.toList());
            return expired.size();
        }).sum();
    }

    /**
     * Creates a new entry in the database table with the Booking provided and returns it.
     * @param booking   the booking to be saved
     * @return          the saved booking
     */
    public Booking saveBooking(Booking booking){
        return bookingRepo.save(booking);
    }

    /**
     * Returns a location's booking records that:
     * <p><ul>
     * <li>are confirmed
     * <li>are pending
     * <li>are within the pending period (1hr)
     * <li>have conflicting dates between existing bookings
     * </ul><p>
     * @param locationId    the ID of the location
     * @param booking       the booking used to find conflicting bookings
     * @return              the list of unavailable bookings
     */
    public List<Booking> getAllUnavailableBookingsForLocation(int locationId, Booking booking){
        updateExpiredBookingsForLocation(locationId);
        List<Booking> bookings = getAllBookingsForLocation(locationId);
        return bookings.stream()
                .filter(booking1 ->{
                    boolean isNotExpired = booking1.calculateTimeFromCreate() >= 0;
                    boolean isPendingOrConfirmed = booking1.getBookingStatus() == BookingStatus.PENDING || booking1.getBookingStatus() == BookingStatus.CONFIRMED;
                    boolean datesConflict = !booking1.getStartDate().after(booking.getEndDate()) && !booking.getStartDate().after(booking1.getEndDate());
                    return isNotExpired && isPendingOrConfirmed && datesConflict;
                })
                .collect(Collectors.toList());
    }

    /**
     * Returns a List of all Bookings in the database.
     * @return  the list of bookings
     */
    public List<Booking> getAllBookings(){
        return bookingRepo.findAll();
    }

    /**
     * Returns all booking records for the given location.
     * @param locationId    the ID of the location
     * @return              the list of bookings
     */
    public List<Booking> getAllBookingsForLocation(int locationId){
        updateExpiredBookingsForLocation(locationId);
        return bookingRepo.findAllByLocation_Id(locationId);
    }

    /**
     * Returns all booking records for the given customer.
     * @param customer_id   the ID of the customer
     * @return              the list of bookings
     */
    public List<Booking> getAllBookingsForCustomer(int customer_id){
        return bookingRepo.findAllByCustomerId(customer_id);
    }
}