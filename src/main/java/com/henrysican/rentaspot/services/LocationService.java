package com.henrysican.rentaspot.services;

import com.google.maps.model.LatLng;
import com.henrysican.rentaspot.dao.LocationRepo;
import com.henrysican.rentaspot.models.BookingStatus;
import com.henrysican.rentaspot.models.Location;
import com.henrysican.rentaspot.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * Creates a new entry in the database table with the Location provided and returns it.
     * @param location  the location to be saved
     * @return          the saved location
     */
    public Location saveLocation(Location location){
        return locationRepo.save(location);
    }

    /**
     * Deletes the given Location from the database.
     * @param location  the location to be deleted
     */
    public void deleteLocation(Location location){
        locationRepo.delete(location);
    }

    /**
     * Returns the Location with the specified ID
     * @param id    the ID of the location
     * @return      the location
     */
    public Location getLocationById(int id){
        return locationRepo.findById(id).orElse(new Location());
    }

    /**
     * Returns a List of all Locations in the database.
     * @return  the list of locations
     */
    public List<Location> getAllLocations(){
        return locationRepo.findAll();
    }

    /**
     * Updates the Location with the specified ID to active or inactive
     * @param locationId    the ID of the location
     * @param active        the active state
     */
    public void updateLocationActive(int locationId, boolean active){
        Location location = locationRepo.getById(locationId);
        location.setActive(active);
    }

    /**
     * Returns a List of the 10 highly rated Locations in descending order based on their weighted average.
     * @return  the list of locations
     */
    public List<Location> get10HighlyRatedLocations(){
        final long LIMIT = 10;
        List<Location> locations = locationRepo.findAllByIsActiveIsTrue();
        return locations.stream()
                .sorted(Comparator.comparingDouble(Location::getWeightedAverage).reversed())
                .limit(LIMIT)
                .collect(Collectors.toList());
    }

    /**
     * Returns a List of the 10 recently added Locations
     * @return  the list of locations
     */
    public List<Location> get10RecentlyAddedLocations(){
        return locationRepo.findTop10ByIsActiveIsTrueOrderByCreatedAtDesc();
    }

    /**
     * Returns all locations in the database for the given host.
     * @param id    the ID of the host
     * @return      the list of locations
     */
    public List<Location> getAllLocationsForHost(int id){
        return locationRepo.findAllByUser_Id(id);
    }

    /**
     * Returns all active locations for the given host.
     * @param id    the ID of the host
     * @return      the list of locations
     */
    public List<Location> getAllActiveLocationsForUser(int id){
        return locationRepo.findAllByIsActiveIsTrueAndUser_Id(id);
    }

    /**
     * Returns all active locations with the specified total occupancy minimum.
     * @param total     the occupancy minimum
     * @return          the list of locations
     */
    public List<Location> searchLocationsByTotalOccupancy(int total){
        return locationRepo.findAllByIsActiveIsTrueAndTotalOccupancyGreaterThanEqual(total);
    }

    /**
     * Returns all active locations with the specified city.
     * @param city  the locations' city
     * @return      the list of locations
     */
    public List<Location> searchLocationsByCity(String city){
        return locationRepo.findAllByIsActiveIsTrueAndAddress_CityLike(city);
    }

    /**
     * Returns all active locations with the specified city and total occupancy minimum.
     * @param city  the locations' city
     * @param cars  the occupancy minimum
     * @return      the list of locations
     */
    public List<Location> searchLocationsByCityAndCars(String city, int cars){
        return locationRepo.findAllByIsActiveIsTrueAndAddress_CityLikeAndTotalOccupancyGreaterThanEqual(city,cars);
    }

    /**
     * Returns all active locations that are available with the specified start date and end date.
     * Available locations are ones that can be booked. So, an available location's bookings should not any match these conditions:
     * <p><ul>
     * <li>a confirmed booking status
     * <li>a pending booking status
     * <li>is within the pending period (1hr)
     * <li>conflicting start and end dates between existing bookings
     * </ul><p>
     * @param start     the preferred start date
     * @param end       the preferred end date
     * @return          the list of locations
     */
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

    /**
     * Returns all active locations that are available with the specified city , start date , end date , and total occupancy minimum.
     * Available locations are ones that can be booked. So, an available location's bookings should not any match these conditions:
     * <p><ul>
     * <li>a confirmed booking status
     * <li>a pending booking status
     * <li>is within the pending period (1hr)
     * <li>conflicting start and end dates between existing bookings
     * </ul><p>
     * @param city     the locations' city
     * @param start    the preferred start date
     * @param end      the preferred end date
     * @param cars     the occupancy minimum
     * @return         the list of locations
     */
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