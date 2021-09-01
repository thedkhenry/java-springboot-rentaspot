package com.henrysican.rentaspot.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
public class Review {
//TODO Add validation for fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @NonNull
    int rating;
    @NonNull
    String textContent;
    @NonNull
    @ManyToOne(cascade = CascadeType.MERGE)
    User user;
    @JsonBackReference
    @NonNull
    @ManyToOne(cascade = CascadeType.MERGE)
    Location location;
    @OneToOne(cascade = CascadeType.MERGE)
    Booking booking;
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    Date createdAt;
}