package com.henrysican.rentaspot.dao;

import com.henrysican.rentaspot.models.Booking;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepo extends JpaRepository<Booking,Integer> {
    Booking findById(int id);
    List<Booking> findAllByCustomerId(int id);
    List<Booking> findAllByLocation_Id(int id);
    List<Booking> findAllByHost_IdAndBookingStatusLikeOrderByCreatedAtDesc(int host_id, @NonNull String bookingStatus);
    Booking findByLocation_IdAndStartDateAfterAndEndDateBefore(int location_id, Date startDate, Date endDate);
    boolean existsBookingByLocation_IdAndStartDateAfterAndEndDateBefore(int location_id, @NonNull Date startDate, @NonNull Date endDate);
    List<Booking> findAllByLocation_IdAndStartDateIsIn(int location_id, Collection<@NonNull Date> startDate);
}