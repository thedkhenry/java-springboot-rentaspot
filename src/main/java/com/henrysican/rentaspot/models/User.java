package com.henrysican.rentaspot.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
public class User {
//TODO Add validation for fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @NonNull
    String firstName;
    @NonNull
    String lastName;
    @NonNull
    String email;
    @NonNull
    String password;
    @NonNull
    String phoneNumber;
    @NonNull
    String summary;
    @NonNull
    String profileImage;
    @NonNull
    boolean isHost;
    @NonNull
    String status;
    @NonNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    Date createdAt;

    //@OneToMany(mappedBy = "user/host", cascade = CascadeType.ALL)
    //Set<Location> locationl = new HashSet<>();
}