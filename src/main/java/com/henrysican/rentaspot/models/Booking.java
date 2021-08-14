package com.henrysican.rentaspot.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Booking {

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
    String bookingStatus = "pending";
    @NonNull
    @ManyToOne
    User host;
    @NonNull
    @ManyToOne
    User customer;
    @NonNull
    @ManyToOne
    Location location;
    String status;
    boolean hasReview;
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    Date createdAt;

    public long calculateNumberOfDays(){
        return ChronoUnit.DAYS.between(startDate.toInstant(), endDate.toInstant());
    }
}