package com.henrysican.rentaspot.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString(exclude = {"host","customer","location"})
public class Booking {
//TODO Add validation for fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @NonNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date startDate;
    @NonNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date endDate;
    @NonNull
    double price;
//    @NotNull
    @NonNull
    int cars;
    @NonNull
    @Enumerated(EnumType.STRING)
    BookingStatus bookingStatus;
    @NonNull
    @ManyToOne
    User host;
    @NonNull
    @ManyToOne
    User customer;
    @JsonBackReference
    @NonNull
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    Location location;
    String status;
    @NonNull
    boolean hasReview;
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    Date createdAt;

    public long calculateNumberOfDays(){
        if(startDate == null || endDate == null){
            return 0;
        }
        long days = ChronoUnit.DAYS.between(startDate.toInstant(), endDate.toInstant());
        if(days >= 0){
            return days+1;
        }
        return days;
    }

    public boolean isRangeValid(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        boolean isStartTodayOrAfter = !this.startDate.before(calendar.getTime());
        boolean isStartBeforeEnd = !this.startDate.after(this.endDate);
        return isStartTodayOrAfter && isStartBeforeEnd;
    }

    public long calculateTimeFromCreate(){
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.createdAt);
        calendar.add(Calendar.HOUR_OF_DAY,1);
        calendar.getTime().toInstant();
        return ChronoUnit.MINUTES.between(today.toInstant(), calendar.getTime().toInstant());
    }

    public long calculateDaysFromEndDate(){
        Date today = new Date();
        return  ChronoUnit.DAYS.between(this.endDate.toInstant(), today.toInstant());
    }

    public double calculatePrice(){
        this.price = calculateNumberOfDays() * location.getPrice();
        return price;
    }

    public boolean needsReview(){
        boolean isEndDate = calculateDaysFromEndDate() >= 0;
        boolean isConfirmed = this.bookingStatus == BookingStatus.CONFIRMED;
        return isConfirmed && isEndDate && !this.hasReview;
    }

    public boolean ableToCancel(){
        Date today = new Date();
        boolean isNotAfterEndDate = !today.after(this.endDate);
        boolean isConfirmedOrPending = this.bookingStatus == BookingStatus.CONFIRMED || this.bookingStatus == BookingStatus.PENDING;
        return isConfirmedOrPending && isNotAfterEndDate;
    }
}