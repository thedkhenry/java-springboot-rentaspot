package com.henrysican.rentaspot.dao;

import com.henrysican.rentaspot.models.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    boolean existsByEmail(@NonNull String email);
    User findUserByEmail(@NonNull String email);
    List<User> findAllByProfileImageNotNull();
}