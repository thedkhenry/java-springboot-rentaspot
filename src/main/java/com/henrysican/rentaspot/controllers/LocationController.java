package com.henrysican.rentaspot.controllers;

import com.henrysican.rentaspot.models.*;
import com.henrysican.rentaspot.services.LocationService;
import com.henrysican.rentaspot.services.ReviewService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@Controller
public class LocationController {
    private final LocationService locationService;
    private final ReviewService reviewService;

    @Autowired
    public LocationController(LocationService locationService, ReviewService reviewService) {
        this.locationService = locationService;
        this.reviewService = reviewService;
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
    public String getEditForm(@PathVariable("locationId") Location location, Model model){
        //Location location = locationService.getLocationById(id);
        log.warning("/edit/{"+location.getId()+"} getEditForm - " + location);
        model.addAttribute("location", location);
        return "editlisting";
    }

    @RequestMapping(value="/update", method=RequestMethod.POST,params = "action=update")
    public String updateLocation(@ModelAttribute Location location, Model model){
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
    public String deleteLocation(@ModelAttribute Location location, Model model){
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

//TODO: Add save functionality / not publish location / draft
    @RequestMapping(value="/create", method=RequestMethod.POST,params = "action=save")
    public String saveLocation(@ModelAttribute Location location, Model model){
        log.warning("/create SAVE 1 " + location);
        User user = new User();
        user.setId(1);
        location.setUser(user);
        location.setActive(false);
        location = locationService.saveLocation(location);
        //lat long
        log.warning("/create SAVE 2 " + location);
        return "redirect:/hostinglist";
    }

    @RequestMapping(value="/create", method=RequestMethod.POST,params = "action=publish")
    public String publishLocation(@ModelAttribute Location location, Model model){
        log.warning("/create PUBLISH 1 " + location);
        User user = new User();
        user.setId(1);
        location.setUser(user);
        location.setActive(true);
        //lat long
        location = locationService.saveLocation(location);
        log.warning("/create PUBLISH 2 " + location);
        return "redirect:/hostinglist";
    }
}