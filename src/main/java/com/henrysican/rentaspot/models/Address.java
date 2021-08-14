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
//    @OneToOne(cascade = CascadeType.ALL, mappedBy = "address")
//    Location location;

//    public Address(int id, String address1, String address2, String city, String state, String country, int zipCode) {
//        this.id = id;
//        this.address1 = address1;
//        this.address2 = address2;
//        this.city = city;
//        this.state = state;
//        this.country = country;
//        this.zipCode = zipCode;
//    }
}