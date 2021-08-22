package com.henrysican.rentaspot.services;

import com.henrysican.rentaspot.dao.LocationRepo;
import com.henrysican.rentaspot.models.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public void deleteLocation(Location location){
        locationRepo.delete(location);
    }

    public Location getLocationById(int id){
        return locationRepo.findById(id).orElse(new Location());
    }

    public List<Location> getAllLocations(){
        return locationRepo.findAll();
    }

    public List<Location> getAllActiveLocations(){
        return locationRepo.findAllByIsActiveIsTrue();
    }

    public List<Location> get10HighlyRatedLocations(){
        final long LIMIT = 10;
        List<Location> locations = locationRepo.findAllByIsActiveIsTrue();
        return locations.stream()
                .sorted(Comparator.comparingDouble(Location::calculateWeightedAverage).reversed())
                .limit(LIMIT)
                .collect(Collectors.toList());
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

    public List<Location> searchLocationsByTotalOccupancy(int total){
        return locationRepo.findAllByIsActiveIsTrueAndTotalOccupancyGreaterThanEqual(total);
    }

    public List<Location> searchLocationsByCity(String city){
        return locationRepo.findAllByIsActiveIsTrueAndAddress_CityLike(city);
    }

    public List<Location> searchLocationsByCityAndCars(String city, int cars){
        return locationRepo.findAllByIsActiveIsTrueAndAddress_CityLikeAndTotalOccupancyGreaterThanEqual(city,cars);
    }

//TODO: Implement full search. Booking Dates need to be queried.
    public List<Location> searchLocationsByCityStartEndDatesAndCars(String city, String start, String end, int cars){
        //DateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse("2021-05-07 23:11:19")
        //Date date = format.parse("string");
        if((city == null || city.isEmpty()) && (start == null || start.isEmpty()) && (end == null || end.isEmpty()) && cars == 0){
            return get10HighlyRatedLocations();
        }
        return null;
    }
}