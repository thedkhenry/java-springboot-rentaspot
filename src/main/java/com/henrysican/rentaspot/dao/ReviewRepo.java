package com.henrysican.rentaspot.dao;

import com.henrysican.rentaspot.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepo extends JpaRepository<Review,Integer> {
    int countAllByLocation_Id(int id);
    List<Review> findAllByLocation_Id(int location_id);
}