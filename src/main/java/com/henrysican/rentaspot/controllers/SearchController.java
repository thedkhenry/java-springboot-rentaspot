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
                                   @RequestParam("startDate") String startDate,
                                   @RequestParam("endDate") String endDate,
//                                   @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
//                                   @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
                                   @RequestParam("cars") int cars,
                                   Model model){
/*
        Map<String, String[]> requestParameterMap = httpServletRequest.getParameterMap();
        for(String key : requestParameterMap.keySet()){
            log.warning("Key : "+ key +", Value: "+ requestParameterMap.get(key)[0]);
        }
*/
        List<Location> resultLocations = new ArrayList<>();
        if((city == null || city.isEmpty()) && (startDate == null || startDate.isEmpty()) && (endDate == null || endDate.isEmpty()) && cars == 0){
            resultLocations = locationService.get10HighlyRatedLocations();
        }
        else if((city == null || city.isEmpty()) && (startDate == null || startDate.isEmpty()) && (endDate == null || endDate.isEmpty())){
            resultLocations = locationService.searchLocationsByTotalOccupancy(cars);
        }
        else if((startDate == null || startDate.isEmpty()) && (endDate == null || endDate.isEmpty()) && cars == 0){
            resultLocations = locationService.searchLocationsByCity(city);
        }
        else if((startDate == null || startDate.isEmpty()) && (endDate == null || endDate.isEmpty())){
            resultLocations = locationService.searchLocationsByCityAndCars(city,cars);
        }
        List<Address> addresses = resultLocations.stream().map(Location::getAddress).collect(Collectors.toList());
        log.warning("addresses"+ addresses);
        model.addAttribute("locations", resultLocations);
        model.addAttribute("addresses", addresses);
        model.addAttribute("city", city);
        return "searchlist";
    }
}