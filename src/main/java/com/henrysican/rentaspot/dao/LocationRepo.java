package com.henrysican.rentaspot.dao;

import com.henrysican.rentaspot.models.Location;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepo extends JpaRepository<Location,Integer> {
    List<Location> findAllByIsActiveIsTrue();
    List<Location> findTop10ByIsActiveIsTrueOrderByCreatedAtDesc();
    List<Location> findAllByUser_Id(int user_id);
    List<Location> findAllByIsActiveIsTrueAndUser_Id(int user_id);
    List<Location> findAllByIsActiveIsTrueAndTotalOccupancyGreaterThanEqual(@NonNull int totalOccupancy);
    List<Location> findAllByIsActiveIsTrueAndAddress_CityLike(@NonNull String address_city);
    List<Location> findAllByIsActiveIsTrueAndAddress_CityLikeAndTotalOccupancyGreaterThanEqual(@NonNull String address_city, @NonNull int totalOccupancy);
}