package com.henrysican.rentaspot.dao;

import com.henrysican.rentaspot.models.Booking;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepo extends JpaRepository<Booking,Integer> {
    //Booking findById(int id);
    List<Booking> findAllByCustomerId(int id);
    List<Booking> findAllByLocation_Id(int id);
    List<Booking> findAllByHost_IdAndBookingStatusLikeOrderByCreatedAtDesc(int host_id, @NonNull String bookingStatus);
    List<Booking> findAllByLocation_IdAndStartDateAfterAndEndDateBefore(int location_id, Date startDate, Date endDate);
}