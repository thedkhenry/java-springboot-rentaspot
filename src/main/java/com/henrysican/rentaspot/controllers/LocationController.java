package com.henrysican.rentaspot.controllers;

import com.henrysican.rentaspot.models.Address;
import com.henrysican.rentaspot.models.Location;
import com.henrysican.rentaspot.models.Review;
import com.henrysican.rentaspot.models.User;
import com.henrysican.rentaspot.services.LocationService;
import com.henrysican.rentaspot.services.ReviewService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String getLocationDetails(@PathVariable("locationId") int id, Model model){
        Location location = locationService.getLocationById(id);
        List<Review> reviews = reviewService.getReviewsForLocation(id);
        double rating = reviewService.getWeightedAverageForLocation(id);
        model.addAttribute("location",location);
        model.addAttribute("reviews",reviews);
        model.addAttribute("rating",rating);
        return "locationdetails";
    }

//    @GetMapping("/hostinglist")
//    public String getHostingListPage(){
//        return "hostinglist";
//    }

//TODO: Add location creation form validation
    @GetMapping("/create")
    public String getCreateForm(Model model){
        model.addAttribute("location", new Location());
        model.addAttribute("address", new Address());
        return "createlisting";
    }

    @PostMapping("/create")
    public String submitLocation(@ModelAttribute("location") Location location, Model model){
        log.warning(location.toString());
        User user = new User();
        user.setId(1);
        location.setUser(user);
        location.setActive(true);
        location = locationService.saveLocation(location);
        log.warning(location.toString());
        return "redirect:/location/"+location.getId();
    }

//TODO: Add save functionality / not publish location / draft
    @PostMapping("/save")
    public String saveLocation(@ModelAttribute("location") Location location, Model model){
        log.warning(location.toString());
        //user
        //active
        //lat lon
        return "redirect:/location/"+location.getId();
    }
}