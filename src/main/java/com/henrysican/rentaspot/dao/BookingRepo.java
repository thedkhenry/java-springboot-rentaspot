package com.henrysican.rentaspot.dao;

import com.henrysican.rentaspot.models.Booking;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.stereotype.Repository;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepo extends JpaRepository<Booking,Integer> {
    Booking findById(int id);
    List<Booking> findAllByCustomerId(int id);
    List<Booking> findAllByLocation_Id(int id);
    List<Booking> findAllByHost_IdAndBookingStatusLikeOrderByCreatedAtDesc(int host_id, @NonNull String bookingStatus);
    List<Booking> findAllByLocation_IdAndStartDateGreaterThanEqualAndEndDateLessThanEqual(int location_id, @Temporal(TemporalType.DATE) Date startDate, @Temporal(TemporalType.DATE) Date endDate);
    List<Booking> findAllByLocation_IdAndBookingStatusIsLike(int location_id, @NonNull String bookingStatus);
    List<Booking> findAllByLocation_IdAndBookingStatusIsLikeAndStartDateGreaterThanEqualAndEndDateLessThanEqual(int location_id, @NonNull String bookingStatus, @Temporal(TemporalType.DATE) Date startDate, @Temporal(TemporalType.DATE) Date endDate);
    boolean existsBookingByLocation_IdAndStartDateAfterAndEndDateBefore(int location_id, @Temporal(TemporalType.DATE) Date startDate, @Temporal(TemporalType.DATE) Date endDate);
    void deleteAllByLocation_IdAndBookingStatusIsLike(int location_id, @NonNull String bookingStatus);
}