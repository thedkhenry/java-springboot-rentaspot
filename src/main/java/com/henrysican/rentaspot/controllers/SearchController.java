package com.henrysican.rentaspot.controllers;

import com.henrysican.rentaspot.models.Address;
import com.henrysican.rentaspot.models.Location;
import com.henrysican.rentaspot.services.LocationService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log
@Controller
public class SearchController {

    private final LocationService locationService;

    @Autowired
    public SearchController(LocationService locationService) {
        this.locationService = locationService;
    }

//TODO: Trim leading/trailing whitespaces for City input
//TODO: Add location title to map marker
    @GetMapping("/search")
    public String getSearchResults(HttpServletRequest httpServletRequest, @RequestParam("city") String city,
                                   @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String startDate,
                                   @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String endDate,
                                   @RequestParam("cars") int cars,
                                   Model model){
        log.warning("SEARCH DATES " + startDate);
        log.warning("SEARCH DATES " + endDate);
        List<Location> resultLocations = new ArrayList<>();
        Date start = null;
        Date end = null;
        try {
            start = new SimpleDateFormat("yyy-MM-dd").parse(startDate);
            end = new SimpleDateFormat("yyy-MM-dd").parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //If all are empty
        if((city == null || city.isEmpty()) && start == null && end == null && cars == 0){
            resultLocations = locationService.get10HighlyRatedLocations();
        }
        //If all are empty but cars
        else if((city == null || city.isEmpty()) && start == null && end == null){
            resultLocations = locationService.searchLocationsByTotalOccupancy(cars);
        }
        //If all are empty but city
        else if(start == null && end == null && cars == 0){
            resultLocations = locationService.searchLocationsByCity(city);
        }
        //If dates are empty
        else if(start == null && end == null){
            resultLocations = locationService.searchLocationsByCityAndCars(city,cars);
        }
        //If all are empty but dates
        else if((city == null || city.isEmpty()) && cars == 0){
            resultLocations = locationService.searchLocationsByDates(start, end);
        }
        //If none are empty
        else{
            resultLocations = locationService.searchLocationsByCityStartEndDatesAndCars(city, start, end, cars);
        }

/*
        //If all are empty
        if((city == null || city.isEmpty()) && (startDate == null || startDate.isEmpty()) && (endDate == null || endDate.isEmpty()) && cars == 0){
            resultLocations = locationService.get10HighlyRatedLocations();
        }
        //If all are empty but cars
        else if((city == null || city.isEmpty()) && (startDate == null || startDate.isEmpty()) && (endDate == null || endDate.isEmpty())){
            resultLocations = locationService.searchLocationsByTotalOccupancy(cars);
        }
        //If all are empty but city
        else if((startDate == null || startDate.isEmpty()) && (endDate == null || endDate.isEmpty()) && cars == 0){
            resultLocations = locationService.searchLocationsByCity(city);
        }
        //If dates are empty
        else if((startDate == null || startDate.isEmpty()) && (endDate == null || endDate.isEmpty())){
            resultLocations = locationService.searchLocationsByCityAndCars(city,cars);
        }
        //If all are empty but dates
        else if((city == null || city.isEmpty()) && cars == 0){
            resultLocations = locationService.searchLocationsByCityAndCars(city,cars);
        }
*/
        List<Address> addresses = resultLocations.stream().map(Location::getAddress).collect(Collectors.toList());
        model.addAttribute("locations", resultLocations);
        model.addAttribute("addresses", addresses);
        model.addAttribute("city", city);
        log.warning("addresses "+ addresses.size() + " " + addresses);
        return "searchlist";
    }
}