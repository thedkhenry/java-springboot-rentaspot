package com.henrysican.rentaspot.services;

import com.henrysican.rentaspot.dao.LocationRepo;
import com.henrysican.rentaspot.models.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LocationService {
    private final LocationRepo locationRepo;

    @Autowired
    public LocationService(LocationRepo locationRepo){
        this.locationRepo = locationRepo;
    }

    public Location saveLocation(Location location){
        return locationRepo.save(location);
    }

    public Location getLocationById(int id){
        return locationRepo.findById(id).orElse(new Location());
    }

    public List<Location> getAllLocations(){
        return locationRepo.findAll();
    }

    public List<Location> get10HighlyRatedLocations(){
        return locationRepo.findTop10ByIsActiveIsTrue();
    }

    public List<Location> get10RecentlyAddedLocations(){
        return locationRepo.findTop10ByIsActiveIsTrueOrderByCreatedAtDesc();
    }

    public List<Location> getAllLocationsForUser(int id){
        return locationRepo.findAllByUser_Id(id);
    }

    public List<Location> getAllActiveLocationsForUser(int id){
        return locationRepo.findAllByIsActiveIsTrueAndUser_Id(id);
    }
}