package com.henrysican.rentaspot.dao;

import com.henrysican.rentaspot.models.AuthGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthRepo extends JpaRepository<AuthGroup,Long> {
    List<AuthGroup> findAllByUserId(long userId);
    Optional<AuthGroup> findByUserId(long userId);
}
