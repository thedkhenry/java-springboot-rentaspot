package com.henrysican.rentaspot.controllers;

import com.henrysican.rentaspot.models.*;
import com.henrysican.rentaspot.security.AppUserDetailsService;
import com.henrysican.rentaspot.security.AppUserPrincipal;
import com.henrysican.rentaspot.services.LocationService;
import com.henrysican.rentaspot.services.ReviewService;
import com.henrysican.rentaspot.services.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Log
@Controller
public class LocationController {
    private final LocationService locationService;
    private final ReviewService reviewService;
    private final UserService userService;
    private final AppUserDetailsService appUserDetailsService;

    @Autowired
    public LocationController(LocationService locationService,
                              ReviewService reviewService,
                              UserService userService,
                              AppUserDetailsService appUserDetailsService) {
        this.locationService = locationService;
        this.reviewService = reviewService;
        this.userService = userService;
        this.appUserDetailsService = appUserDetailsService;
    }

    @GetMapping("/location/{locationId}")
    public String getLocationDetails(@PathVariable("locationId") Location location, Model model){
//        Location location = locationService.getLocationById(id);
        List<Review> reviews = reviewService.getReviewsForLocation(location.getId());
        model.addAttribute("location",location);
        model.addAttribute("reviews",reviews);
        return "locationdetails";
    }

//TODO: Add location edit form validation
    @GetMapping("/edit/{locationId}")
    public String getEditForm(@PathVariable("locationId") Location location, Principal principal, Model model){
        //Location location = locationService.getLocationById(id);
        if(principal == null){
            return "error";
        }
        AppUserPrincipal userDetails = (AppUserPrincipal) appUserDetailsService.loadUserByUsername(principal.getName());
        if(userDetails.getId() != location.getUser().getId()){
            return "error";
        }
        log.warning("/edit/{"+location.getId()+"} getEditForm - " + location);
        model.addAttribute("location", location);
        return "editlisting";
    }
//TODO: Change to  /update/{locationId}  ?
    @RequestMapping(value="/update", method=RequestMethod.POST,params = "action=update")
    public String updateLocation(@ModelAttribute Location location){
        log.warning("/update UPDATE 1 " + location);
        Location dbLocation = locationService.getLocationById(location.getId());
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
        dbLocation = locationService.saveLocation(dbLocation);
        log.warning("/update UPDATE 2 " + dbLocation);
        return "redirect:/hostinglist";
    }

    @RequestMapping(value="/update", method=RequestMethod.POST,params = "action=delete")
    public String deleteLocation(@ModelAttribute Location location){
        log.warning("/update DELETE " + location);
        locationService.deleteLocation(location);
        return "redirect:/hostinglist";
    }

//TODO: Add location creation form validation  &&  Make User Host
    @GetMapping("/create")
    public String getCreateForm(Model model){
        model.addAttribute("location", new Location());
        model.addAttribute("address", new Address());
        return "createlisting";
    }

    @RequestMapping(value="/create", method=RequestMethod.POST,params = "action=save")
    public String saveLocation(@ModelAttribute Location location, Principal principal){
        log.warning("/create SAVE 1 " + location);
        AppUserPrincipal userDetails = (AppUserPrincipal) appUserDetailsService.loadUserByUsername(principal.getName());
        User user = userService.getUserById(userDetails.getId());
        user.setHost(true);
        location.setUser(user);
        location.getAddress().setCountry("US");
        location.setActive(false);
        //lat long
        location = locationService.saveLocation(location);
        userService.saveUser(user);
        log.warning("/create SAVE 2 " + location);
        return "redirect:/hostinglist";
    }

    @RequestMapping(value="/create", method=RequestMethod.POST,params = "action=publish")
    public String publishLocation(@ModelAttribute Location location, Principal principal){
        log.warning("/create PUBLISH 1 " + location);
        AppUserPrincipal userDetails = (AppUserPrincipal) appUserDetailsService.loadUserByUsername(principal.getName());
        User user = userService.getUserById(userDetails.getId());
        user.setHost(true);
        location.setUser(user);
        location.getAddress().setCountry("US");
        location.setActive(true);
        //lat long
        location = locationService.saveLocation(location);
        userService.saveUser(user);
        log.warning("/create PUBLISH 2 " + location);
        return "redirect:/hostinglist";
    }
}