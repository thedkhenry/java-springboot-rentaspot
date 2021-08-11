package com.henrysican.rentaspot.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
//@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    int totalOccupancy;
    int price;
    String title;
    String description;
    boolean isActive;
    boolean isEnclosed;
    boolean isStreetParking;
    boolean hasVideoMonitoring;
    boolean hasRvParking;
    boolean hasEvCharging;
    double latitude;
    double longitude;
    @ManyToOne(cascade = CascadeType.ALL)
    User user;
    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "address_id")
    Address address;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    Date updatedAt;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    Date createdAt;
}