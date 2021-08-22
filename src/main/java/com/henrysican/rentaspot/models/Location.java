package com.henrysican.rentaspot.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Location {
//TODO Add validation for fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @NonNull
    int totalOccupancy;
    @NonNull
    int price;
    @NonNull
    String title;
    @NonNull
    String description;
    @NonNull
    boolean isActive;
    @NonNull
    boolean isEnclosed;
    @NonNull
    boolean isStreetParking;
    @NonNull
    boolean hasVideoMonitoring;
    @NonNull
    boolean hasRvParking;
    @NonNull
    boolean hasEvCharging;
    @NonNull
    double latitude;
    @NonNull
    double longitude;
    @ManyToOne(cascade = CascadeType.MERGE)
    @NonNull
//    @ManyToOne(cascade = CascadeType.REFRESH) @JoinColumn(name = "user_id"
    User user;
    @OneToOne(cascade = CascadeType.ALL)
    @NonNull
    Address address;
    @OneToMany(mappedBy = "location")
    List<Review> reviews;
    @OneToMany(mappedBy = "location")
    List<Booking> bookings;
    @Temporal(TemporalType.TIMESTAMP)
//    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @UpdateTimestamp
    Date updatedAt;
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    Date createdAt;

    public long calculateDaysSinceUpdated(){
        Date today = new Date();
        return ChronoUnit.DAYS.between(updatedAt.toInstant(), today.toInstant());
    }

//TODO: Update pages where used. Ex: "Created 0 days ago" to " Created today"
    public long calculateDaysSinceCreated(){
        Date today = new Date();
        return ChronoUnit.DAYS.between(createdAt.toInstant(), today.toInstant());
    }

    public double calculateWeightedAverage(){
        double weightedAvg = 0;
        if (!this.reviews.isEmpty()) {
            long countFives = this.reviews.stream().filter(review -> review.getRating() == 5).count();
            long countFours = this.reviews.stream().filter(review -> review.getRating() == 4).count();
            long countThrees = this.reviews.stream().filter(review -> review.getRating() == 3).count();
            long countTwos = this.reviews.stream().filter(review -> review.getRating() == 2).count();
            long countOnes = this.reviews.stream().filter(review -> review.getRating() == 1).count();
            weightedAvg = Math.round(((5*countFives + 4*countFours + 3*countThrees + 2*countTwos + 1*countOnes) / (double)reviews.size()) * 100d)/100d;
        }
        return weightedAvg;
    }

    public long calculateTotalPendingBookings(){
        if (this.bookings.isEmpty()){
            return 0;
        }
        return this.bookings.stream().filter(booking -> booking.getBookingStatus().equals("pending")).count();
    }
}