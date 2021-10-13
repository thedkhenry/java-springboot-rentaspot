package com.henrysican.rentaspot.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.*;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString(exclude = {"wishlist","authGroups"})
public class User implements UserDetails {
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
    @JsonIgnore
    String email;
    @NonNull @NotBlank
    @JsonIgnore
    String password;
    @NonNull
    String phoneNumber;
    @NonNull
    @Column(columnDefinition = "TEXT")
    String summary;
    @OneToOne
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
    @JsonIgnore
    List<Location> wishlist;
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JsonIgnore
    List<AuthGroup> authGroups;
    @NonNull
    boolean isHost;
    @NonNull
    String status;
    @NonNull
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    Date createdAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(null == authGroups){
            return Collections.emptySet();
        }
        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
        authGroups.forEach(authGroup -> {
            grantedAuthorities.add(new SimpleGrantedAuthority(authGroup.getGroupName()));
        });
        return grantedAuthorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}