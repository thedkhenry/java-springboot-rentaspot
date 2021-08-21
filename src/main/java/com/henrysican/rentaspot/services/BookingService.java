package com.henrysican.rentaspot.services;

import com.henrysican.rentaspot.dao.BookingRepo;
import com.henrysican.rentaspot.models.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class BookingService {
    private final BookingRepo bookingRepo;

    @Autowired
    public BookingService(BookingRepo bookingRepo){
        this.bookingRepo = bookingRepo;
    }

    public Booking saveBooking(Booking booking){
        return bookingRepo.save(booking);
    }

    public List<Booking> getAllBookings(){
        return bookingRepo.findAll();
    }

    public List<Booking> getAllBookingsForLocation(int id){
        return bookingRepo.findAllByLocation_Id(id);
    }

    public Booking getBookingForLocationBetween(int location_id, Date startDate, Date endDate){
        return bookingRepo.findByLocation_IdAndStartDateAfterAndEndDateBefore(location_id,startDate,endDate);
    }

    public List<Booking> getAllBookingsForCustomer(int id){
        return bookingRepo.findAllByCustomerId(id);
    }


    public Booking getBookingById(int id){
        return bookingRepo.findById(id);
    }


    public List<Booking> getAllBookingsByStatusForHost(int id, String status){
        return bookingRepo.findAllByHost_IdAndBookingStatusLikeOrderByCreatedAtDesc(id,status);
    }
}