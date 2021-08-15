package com.henrysican.rentaspot.controllers;

import com.henrysican.rentaspot.models.Location;
import com.henrysican.rentaspot.models.Review;
import com.henrysican.rentaspot.services.LocationService;
import com.henrysican.rentaspot.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

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

    @GetMapping("/create")
    public String getCreateForm(Model model){
        model.addAttribute("location", new Location());
        return "createlisting";
    }

}