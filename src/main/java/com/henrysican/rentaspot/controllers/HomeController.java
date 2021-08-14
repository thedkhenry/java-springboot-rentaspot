package com.henrysican.rentaspot.controllers;

import com.henrysican.rentaspot.models.Location;
import com.henrysican.rentaspot.models.Review;
import com.henrysican.rentaspot.models.User;
import com.henrysican.rentaspot.services.LocationService;
import com.henrysican.rentaspot.services.ReviewService;
import com.henrysican.rentaspot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
public class HomeController {
    private final LocationService locationService;
    private final ReviewService reviewService;
    private final UserService userService;

    @Autowired
    public HomeController(LocationService locationService, ReviewService reviewService, UserService userService){
        this.locationService = locationService;
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @GetMapping({"/","/home"})
    public String getHomePage(Model model){
        List<Location> topRatedLocations = locationService.get10HighlyRatedLocations();
        List<Location> recentlyAddedLocations = locationService.get10RecentlyAddedLocations();
        HashMap<Integer,Long> topUpdatedMap = new HashMap<>();
        HashMap<Integer,Long> newCreatedMap = new HashMap<>();
        HashMap<Integer,Double> topRatingMap = new HashMap<>();
        HashMap<Integer,Double> newRatingMap = new HashMap<>();
        HashMap<Integer,Integer> topReviewCountMap = new HashMap<>();
        HashMap<Integer,Integer> newReviewCountMap = new HashMap<>();
        topRatedLocations.forEach(location -> {
            long diffInMillies = Math.abs(location.getUpdatedAt().getTime() - System.currentTimeMillis());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            topUpdatedMap.put(location.getId(),diff);
            topRatingMap.put(location.getId(), reviewService.getWeightedAverageForLocation(location.getId()));
            topReviewCountMap.put(location.getId(),reviewService.getReviewCountForLocation(location.getId()));
        });
        recentlyAddedLocations.forEach(location -> {
            long diffInMillies = Math.abs(location.getCreatedAt().getTime() - System.currentTimeMillis());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            newCreatedMap.put(location.getId(),diff);
            newRatingMap.put(location.getId(), reviewService.getWeightedAverageForLocation(location.getId()));
            newReviewCountMap.put(location.getId(),reviewService.getReviewCountForLocation(location.getId()));
        });
        model.addAttribute("topLocations", topRatedLocations);
        model.addAttribute("newLocations", recentlyAddedLocations);
        model.addAttribute("topUpdatedMap", topUpdatedMap);
        model.addAttribute("newCreatedMap", newCreatedMap);
        model.addAttribute("topReviewCountMap", topReviewCountMap);
        model.addAttribute("newReviewCountMap", newReviewCountMap);
        model.addAttribute("topRatingMap", topRatingMap);
        model.addAttribute("newRatingMap", newRatingMap);
        return "home";
    }

    @GetMapping("/search")
    public String getSearchResults(Model model){
        List<Location> locations = locationService.getLocations();
        HashMap<Integer,Double> locationsRatingMap = new HashMap<>();
        HashMap<Integer,Integer> reviewCountMap = new HashMap<>();
        locations.forEach(location -> {
            locationsRatingMap.put(location.getId(), reviewService.getWeightedAverageForLocation(location.getId()));
            reviewCountMap.put(location.getId(),reviewService.getReviewCountForLocation(location.getId()));
        });
        model.addAttribute("locations",locations);
        model.addAttribute("locationsRatingMap",locationsRatingMap);
        model.addAttribute("reviewCountMap",reviewCountMap);
        return "searchlist";
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

    @GetMapping("/user/{userId}")
    public String getUserProfile(@PathVariable("userId") int id, Model model){
        List<Location> locations = locationService.getAllActiveLocationsForUser(id);
        HashMap<Integer,Long> locationsTimeMap = new HashMap<>();
        HashMap<Integer,Double> locationsRatingMap = new HashMap<>();
        HashMap<Integer,Integer> reviewCountMap = new HashMap<>();
        User user = userService.getUserById(id);
        locations.forEach(location -> {
            long diffInMillies = Math.abs(location.getUpdatedAt().getTime() - System.currentTimeMillis());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            locationsTimeMap.put(location.getId(),diff);
            locationsRatingMap.put(location.getId(), reviewService.getWeightedAverageForLocation(location.getId()));
            reviewCountMap.put(location.getId(),reviewService.getReviewCountForLocation(location.getId()));
        });
        model.addAttribute("locations",locations);
        model.addAttribute("user",user);
        model.addAttribute("locationsTimeMap",locationsTimeMap);
        model.addAttribute("locationsRatingMap",locationsRatingMap);
        model.addAttribute("reviewCountMap",reviewCountMap);
        return "profile";
    }

    @GetMapping("/reservationdetails")
    public String getreservationdetailsPage(){
        return "reservationdetails";
    }
    @GetMapping("/reservations")
    public String getreservationsPage(){
        return "reservations";
    }
}