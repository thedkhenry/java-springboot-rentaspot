package com.henrysican.rentaspot;

import com.henrysican.rentaspot.models.Location;
import com.henrysican.rentaspot.services.LocationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class LocationServiceTests {
    @Autowired
    private LocationService locationService;

    @Test
    void testUpdateLocationActive(){
        int locationID = 5;
        locationService.updateLocationActive(locationID, false);
        Location location = locationService.getLocationById(locationID);
        Assertions.assertFalse(location.isActive());
    }

    @Test
    void testSearchByCityStartEndDatesAndCars(){
        String city = "Glendale";
        int carOccupancy = 5;
        int size = locationService.searchLocationsByCityStartEndDatesAndCars(city,new Date(), new Date(), carOccupancy).size();
        Assertions.assertTrue(size >= 1);
    }
}