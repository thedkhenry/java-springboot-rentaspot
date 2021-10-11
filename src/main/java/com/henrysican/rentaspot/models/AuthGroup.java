package com.henrysican.rentaspot.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
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
    @NonNull
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    User user;
    @NonNull @NotBlank
    String groupName;
}