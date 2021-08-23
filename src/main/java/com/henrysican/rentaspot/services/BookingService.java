package com.henrysican.rentaspot.services;

import com.henrysican.rentaspot.dao.BookingRepo;
import com.henrysican.rentaspot.models.Booking;
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

//TODO: Delete Expired Bookings
    public long deleteExpiredBookingsForLocation(int locationId){
        List<Booking> pendingBookings = bookingRepo.findAllByLocation_IdAndBookingStatusIsLike(locationId,"pending");
        List<Booking> expired = pendingBookings.stream()
                .filter(booking -> booking.calculateTimeFromCreate() < 0)
                .collect(Collectors.toList());
        long expiredCount = expired.size();
        if(expiredCount > 0){
            bookingRepo.deleteAll(expired);
        }
        return expiredCount;
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
        deleteExpiredBookingsForLocation(locationId);
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
                .filter(booking1 ->
                        booking1.calculateTimeFromCreate() >= 0 &&
                        (booking1.getBookingStatus().equals("pending") ||
                        booking1.getBookingStatus().equals("confirmed")) &&
                        !booking1.getStartDate().after(booking.getEndDate()) && !booking.getStartDate().after(booking1.getEndDate()))
                .collect(Collectors.toList());
    }

    public List<Booking> getAllBookingsForLocation(int locationId){
        deleteExpiredBookingsForLocation(locationId);
        return bookingRepo.findAllByLocation_Id(locationId);
    }
    public List<Booking> getAllBookingsForLocation(int locationId, String status){
        deleteExpiredBookingsForLocation(locationId);
        return bookingRepo.findAllByLocation_IdAndBookingStatusIsLike(locationId, status);
    }
    public List<Booking> getAllBookingsForLocation(int locationId, Date startDate, Date endDate){
        deleteExpiredBookingsForLocation(locationId);
        return bookingRepo.findAllByLocation_IdAndStartDateGreaterThanEqualAndEndDateLessThanEqual(locationId, startDate, endDate);
    }
    public List<Booking> getAllBookingsForLocation(int locationId, String status, Date startDate, Date endDate){
        deleteExpiredBookingsForLocation(locationId);
        return bookingRepo.findAllByLocation_IdAndBookingStatusIsLikeAndStartDateGreaterThanEqualAndEndDateLessThanEqual(locationId,status,startDate,endDate);
    }

    public boolean isAvailableForLocation(int locationId, Booking booking){
        deleteExpiredBookingsForLocation(locationId);
        List<Booking> bookings = getAllUnavailableBookingsForLocation(locationId, booking);
        long conflicts = bookings.size();
        log.warning("conflicts# ? " + conflicts);
        return conflicts > 0;
    }

    public boolean getAvailabilityForBookingAtLocation(Booking booking, int locationId){
        deleteExpiredBookingsForLocation(locationId);
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
    public List<Booking> getAllBookingsByStatusForHost(int hostId, String status){
        return bookingRepo.findAllByHost_IdAndBookingStatusLikeOrderByCreatedAtDesc(hostId,status);
    }
}