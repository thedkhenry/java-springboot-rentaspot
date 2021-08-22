package com.henrysican.rentaspot.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
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
    String bookingStatus;
    @NonNull
    @ManyToOne
    User host;
    @NonNull
    @ManyToOne
    User customer;
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
        return ChronoUnit.DAYS.between(startDate.toInstant(), endDate.toInstant());
    }

    public double calculatePrice(){
        this.price = calculateNumberOfDays() * location.getPrice();
        return price;
    }

    public long calculateDaysFromEndDate(){
        Date today = new Date();
        return  ChronoUnit.DAYS.between(endDate.toInstant(), today.toInstant());
    }

    public boolean needsReview(){
        Date today = new Date();
        boolean isEndDate = ChronoUnit.DAYS.between(endDate.toInstant(), today.toInstant()) >= 0;
        boolean isConfirmed = this.bookingStatus.equals("confirmed");
        return isConfirmed && isEndDate && !hasReview;
    }
}