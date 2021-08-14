package com.henrysican.rentaspot.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Location {

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
    @ManyToOne(cascade = CascadeType.ALL)
    @NonNull
    User user;
//    @JoinColumn(name = "address_id")
    @OneToOne(cascade = CascadeType.ALL)
    @NonNull
    Address address;
    @Temporal(TemporalType.TIMESTAMP)
//    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @UpdateTimestamp
    Date updatedAt;
    @Temporal(TemporalType.TIMESTAMP)
//    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
//            @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date createdAt;
}