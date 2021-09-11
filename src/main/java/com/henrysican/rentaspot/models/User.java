package com.henrysican.rentaspot.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString(exclude = {"wishlist"})
public class User {
//TODO Add validation for fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
//    @NotBlank
    @NonNull
    String firstName;
    @NonNull
    String lastName;
    @NonNull
    @Email
    @Column(unique = true)
    String email;
    @NonNull @NotBlank
    String password;
    @NonNull
    String phoneNumber;
    @NonNull
    String summary;
    @OneToOne(cascade = CascadeType.ALL)
    Image profileImage;
    @ManyToMany(
            cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH},
            targetEntity = Location.class)
    @JoinTable(
            name = "user_location_wishlist",
            joinColumns = @JoinColumn(name = "user_id",
                    nullable = false,
                    updatable = false),
            inverseJoinColumns = @JoinColumn(name = "location_id",
                    nullable = false,
                    updatable = false),
            foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),
            inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    List<Location> wishlist;
    @NonNull
    boolean isHost;
    @NonNull
    String status;
    @NonNull
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    Date createdAt;
}