package com.henrysican.rentaspot.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthGroup implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @NonNull @NotNull
    long userId;
    @NonNull @NotBlank
    String userEmail;
    @NonNull @NotBlank
    String authGroup;
}