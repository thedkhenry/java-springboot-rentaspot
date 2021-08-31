package com.henrysican.rentaspot.services;

import com.henrysican.rentaspot.dao.LocationRepo;
import com.henrysican.rentaspot.models.BookingStatus;
import com.henrysican.rentaspot.models.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
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

    public void updateLocationActive(int locationId, boolean active){
        Location location = locationRepo.getById(locationId);
        location.setActive(active);
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

    public List<Location> searchLocationsByDates(Date start, Date end){
        List<Location> locations = locationRepo.findAllByIsActiveIsTrue();
        return locations.stream()
                .filter(location -> {
                    return location.getBookings().stream()
                            .noneMatch(booking -> {
                                return booking.calculateTimeFromCreate() >= 0 &&
                                        (booking.getBookingStatus() == BookingStatus.PENDING || booking.getBookingStatus() == BookingStatus.CONFIRMED) &&
                                        !booking.getStartDate().after(end) && !start.after(booking.getEndDate());
                            });
                })
                .collect(Collectors.toList());
    }

    public List<Location> searchLocationsByCityStartEndDatesAndCars(String city, Date start, Date end, int cars){
        List<Location> locations = locationRepo.findAllByIsActiveIsTrueAndAddress_CityLikeAndTotalOccupancyGreaterThanEqual(city,cars);
        return locations.stream()
                .filter(location -> {
                    return location.getBookings().stream()
                            .noneMatch(booking -> {
                                return booking.calculateTimeFromCreate() >= 0 &&
                                        (booking.getBookingStatus() == BookingStatus.PENDING || booking.getBookingStatus() == BookingStatus.CONFIRMED) &&
                                        !booking.getStartDate().after(end) && !start.after(booking.getEndDate());
                            });
                })
                .collect(Collectors.toList());
    }
}