package com.henrysican.rentaspot.dao;

import com.henrysican.rentaspot.models.Booking;
import com.henrysican.rentaspot.models.BookingStatus;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.stereotype.Repository;

import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepo extends JpaRepository<Booking,Integer> {
    List<Booking> findAllByCustomerId(int id);
    List<Booking> findAllByLocation_Id(int id);
    List<Booking> findAllByLocation_IdAndStartDateGreaterThanEqualAndEndDateLessThanEqual(int location_id, @Temporal(TemporalType.DATE) Date startDate, @Temporal(TemporalType.DATE) Date endDate);
    List<Booking> findAllByLocation_IdAndBookingStatusIsLike(int location_id, @NonNull BookingStatus bookingStatus);
}