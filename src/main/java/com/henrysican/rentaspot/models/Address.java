package com.henrysican.rentaspot.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Address {
//TODO Add validation for fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @NonNull
    String address1;
    @NonNull
    String address2;
    @NonNull
    String city;
    @NonNull
    String state;
    @NonNull
    String country;
    @NonNull
    int zipCode;
    @NonNull
    double latitude;
    @NonNull
    double longitude;
//    @OneToOne(mappedBy = "address")
//    Location location;
//    @OneToOne(cascade = CascadeType.ALL, mappedBy = "address")
//    Location location;
}