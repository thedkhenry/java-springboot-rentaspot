package com.henrysican.rentaspot.controllers;

import com.henrysican.rentaspot.models.*;
import com.henrysican.rentaspot.security.AppUserPrincipal;
import com.henrysican.rentaspot.services.LocationService;
import com.henrysican.rentaspot.services.ReviewService;
import com.henrysican.rentaspot.services.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@Controller
public class LocationController {
    private final LocationService locationService;
    private final ReviewService reviewService;
    private final UserService userService;

    @Autowired
    public LocationController(LocationService locationService,
                              ReviewService reviewService,
                              UserService userService) {
        this.locationService = locationService;
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @GetMapping("/location/{locationId}")
    public String getLocationDetails(@PathVariable("locationId") Location location, Model model){
        if(!location.isActive()){
            return "redirect:/403";
        }
        List<Review> reviews = reviewService.getReviewsForLocation(location.getId());
        model.addAttribute("location",location);
        model.addAttribute("reviews",reviews);
        return "locationdetails";
    }

    @GetMapping("/location/{action}/{locationId}")
    public String updateLocationActive(@PathVariable("action") String action,
                                       @PathVariable("locationId") Location location,
                                       @AuthenticationPrincipal AppUserPrincipal principal){
        if(principal.getId() != location.getUser().getId()){
            return "redirect:/403";
        }
        if(action.equals("unlist")){
            locationService.updateLocationActive(location.getId(), false);
        }else if (action.equals("publish")){
            locationService.updateLocationActive(location.getId(), true);
        }
        return "redirect:/hostinglist";
    }

//TODO: Add location edit form validation
    @GetMapping("/edit/{locationId}")
    public String getEditForm(@PathVariable("locationId") Location location,
                              @AuthenticationPrincipal AppUserPrincipal principal,
                              Model model){
        if(principal.getId() != location.getUser().getId()){
            return "redirect:/403";
        }
        model.addAttribute("location", location);
        return "editlisting";
    }

    @PostMapping("/update/{locationId}")
    public String updateLocation(@PathVariable("locationId") int locationId,
                                 @ModelAttribute Location location,
                                 @RequestParam(value = "action") String action,
                                 @AuthenticationPrincipal AppUserPrincipal principal){
        Location dbLocation = locationService.getLocationById(locationId);
        if(principal.getId() != dbLocation.getUser().getId()){
            return "redirect:/403";
        }
        if(action.equals("delete")){ //else "update"
            locationService.deleteLocation(dbLocation);
            return "redirect:/hostinglist";
        }
        dbLocation.setActive(location.isActive());
        dbLocation.setTitle(location.getTitle());
        dbLocation.setDescription(location.getDescription());
        dbLocation.setTotalOccupancy(location.getTotalOccupancy());
        dbLocation.setPrice(location.getPrice());
        dbLocation.setHasVideoMonitoring(location.isHasVideoMonitoring());
        dbLocation.setEnclosed(location.isEnclosed());
        dbLocation.setStreetParking(location.isStreetParking());
        dbLocation.setHasRvParking(location.isHasRvParking());
        dbLocation.setHasEvCharging(location.isHasEvCharging());
        locationService.saveLocation(dbLocation);
        return "redirect:/hostinglist";
    }

//TODO: Add form validation
    @GetMapping("/create")
    public String getCreateForm(Model model){
        model.addAttribute("location", new Location());
        model.addAttribute("address", new Address());
        model.addAttribute("states", UsState.values());
        return "createlisting";
    }

//TODO: User warning - "Address final"
//TODO: geocode address get lat/lon
    @PostMapping("/create")
    public String createLocation(@ModelAttribute Location location,
                                 @AuthenticationPrincipal AppUserPrincipal principal,
                                 @RequestParam(value = "action") String action){
        User user = userService.getUserById(principal.getId());
        user.setHost(true);
        location.setUser(user);
        location.getAddress().setCountry("US");
        location.setActive(action.equals("publish")); //else "save"
        locationService.saveLocation(location);
        userService.saveUser(user);
        return "redirect:/hostinglist";
    }
}