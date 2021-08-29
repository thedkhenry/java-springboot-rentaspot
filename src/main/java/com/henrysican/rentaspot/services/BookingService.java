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

    public long updateExpiredBookingsForLocation(int locationId){
        List<Booking> pendingBookings = bookingRepo.findAllByLocation_IdAndBookingStatusIsLike(locationId,BookingStatus.PENDING);
        List<Booking> expired = pendingBookings.stream()
                .filter(booking -> booking.calculateTimeFromCreate() < 0)
                .peek(booking -> booking.setBookingStatus(BookingStatus.EXPIRED))
                .collect(Collectors.toList());
        return expired.size();
    }
    
    public long updateExpiredBookingsForCustomer(int customerId){
        List<Booking> allBookingsForCustomer = getAllBookingsForCustomer(customerId);
        List<Booking> expired = allBookingsForCustomer.stream()
                .filter(booking -> booking.calculateTimeFromCreate() < 0 && booking.getBookingStatus() == BookingStatus.PENDING)
                .peek(booking -> booking.setBookingStatus(BookingStatus.EXPIRED))
                .collect(Collectors.toList());
        return expired.size();
    }

    public long updateExpiredBookingsForLocations(List<Location> locations){
        return locations.stream().mapToLong(location -> {
            List<Booking> expired = location.getBookings().stream()
                    .filter(booking -> booking.calculateTimeFromCreate() < 0 && booking.getBookingStatus() == BookingStatus.PENDING)
                    .peek(booking -> booking.setBookingStatus(BookingStatus.EXPIRED))
                    .collect(Collectors.toList());
            return expired.size();
        }).sum();
    }

    public Booking saveBooking(Booking booking){
        return bookingRepo.save(booking);
    }

    public Booking getBookingById(int id){
        return bookingRepo.findById(id);
    }

    public List<Booking> getAllBookings(){
        return bookingRepo.findAll();
    }

    public List<Booking> getAllUnavailableBookingsForLocation(int locationId, Booking booking){
        updateExpiredBookingsForLocation(locationId);
        List<Booking> bookings = getAllBookingsForLocation(locationId);
/*
        boolean overlaps = bookings.stream()
                .anyMatch(booking1 ->
                        booking1.calculateTimeFromCreate() >= 0 &&
                                booking1.getBookingStatus().equals("pending") ||
                                booking1.getBookingStatus().equals("confirmed") &&
                                        !booking1.getStartDate().after(booking.getEndDate()) && !booking.getStartDate().after(booking1.getEndDate()));
        long count = bookings.stream()
                .filter(booking1 ->
                        booking1.calculateTimeFromCreate() >= 0 &&
                                booking1.getBookingStatus().equals("pending") ||
                                booking1.getBookingStatus().equals("confirmed") &&
                                        !booking1.getStartDate().after(booking.getEndDate()) && !booking.getStartDate().after(booking1.getEndDate()))
                .count();
        bookings.stream()
                .filter(booking1 ->
                        booking1.calculateTimeFromCreate() >= 0 &&
                                booking1.getBookingStatus().equals("pending") ||
                                booking1.getBookingStatus().equals("confirmed") &&
                                        !booking1.getStartDate().after(booking.getEndDate()) && !booking.getStartDate().after(booking1.getEndDate()))
                .collect(Collectors.toList())
                .forEach(booking1 -> log.warning("conflictors - " + booking1));
        log.warning("overlaps ? " + overlaps);
        log.warning("count ? " + count);
*/
        //log.warning("overlaps# ? " + count);
        //bookings.stream().filter(booking1 -> )
        return bookings.stream()
                .filter(booking1 ->{
                    boolean isNotExpired = booking1.calculateTimeFromCreate() >= 0;
                    boolean isPendingOrConfirmed = booking1.getBookingStatus() == BookingStatus.PENDING || booking1.getBookingStatus() == BookingStatus.CONFIRMED;
                    boolean datesConflict = !booking1.getStartDate().after(booking.getEndDate()) && !booking.getStartDate().after(booking1.getEndDate());
                    return isNotExpired && isPendingOrConfirmed && datesConflict;
                        })
                .collect(Collectors.toList());
    }

    public List<Booking> getAllBookingsForLocation(int locationId){
        updateExpiredBookingsForLocation(locationId);
        return bookingRepo.findAllByLocation_Id(locationId);
    }
    public List<Booking> getAllBookingsForLocation(int locationId, BookingStatus status){
        updateExpiredBookingsForLocation(locationId);
        return bookingRepo.findAllByLocation_IdAndBookingStatusIsLike(locationId, status);
    }
    public List<Booking> getAllBookingsForLocation(int locationId, Date startDate, Date endDate){
        updateExpiredBookingsForLocation(locationId);
        return bookingRepo.findAllByLocation_IdAndStartDateGreaterThanEqualAndEndDateLessThanEqual(locationId, startDate, endDate);
    }
    public List<Booking> getAllBookingsForLocation(int locationId, String status, Date startDate, Date endDate){
        updateExpiredBookingsForLocation(locationId);
        return bookingRepo.findAllByLocation_IdAndBookingStatusIsLikeAndStartDateGreaterThanEqualAndEndDateLessThanEqual(locationId,status,startDate,endDate);
    }

    public boolean isAvailableForLocation(int locationId, Booking booking){
        updateExpiredBookingsForLocation(locationId);
        List<Booking> bookings = getAllUnavailableBookingsForLocation(locationId, booking);
        long conflicts = bookings.size();
        log.warning("conflicts# ? " + conflicts);
        return conflicts == 0;
    }

    public boolean getAvailabilityForBookingAtLocation(Booking booking, int locationId){
        updateExpiredBookingsForLocation(locationId);
        List<Booking> allBookings = bookingRepo.findAllByLocation_IdAndStartDateGreaterThanEqualAndEndDateLessThanEqual(locationId, booking.getStartDate(), booking.getEndDate());
        boolean noneMatchExpired = true;
        if(allBookings.size() > 0){
            noneMatchExpired = allBookings.stream().noneMatch(booking1 ->
                    !booking.getStartDate().after(booking1.getEndDate())  ||  booking.getEndDate().before(booking1.getStartDate()));
        }
        log.warning("getAvailabilityForBookingAtLocation   " + noneMatchExpired);
        return noneMatchExpired;
    }

    public List<Booking> getAllBookingsForCustomer(int customer_id){
        return bookingRepo.findAllByCustomerId(customer_id);
    }
    public List<Booking> getAllBookingsForCustomerEmail(String customer_email){
        return bookingRepo.findAllByCustomer_Email(customer_email);
    }
    public List<Booking> getAllBookingsByStatusForHost(int hostId, String status){
        return bookingRepo.findAllByHost_IdAndBookingStatusLikeOrderByCreatedAtDesc(hostId,status);
    }
}