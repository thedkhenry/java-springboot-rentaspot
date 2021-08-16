package com.henrysican.rentaspot.controllers;

import com.henrysican.rentaspot.models.Booking;
import com.henrysican.rentaspot.models.Location;
import com.henrysican.rentaspot.models.User;
import com.henrysican.rentaspot.services.BookingService;
import com.henrysican.rentaspot.services.LocationService;
import com.henrysican.rentaspot.services.ReviewService;
import com.henrysican.rentaspot.services.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Log
@Controller
public class HomeController {
    private final LocationService locationService;
    private final ReviewService reviewService;
    private final UserService userService;
    private final BookingService bookingService;

    @Autowired
    public HomeController(LocationService locationService,
                          ReviewService reviewService,
                          UserService userService,
                          BookingService bookingService){
        this.locationService = locationService;
        this.reviewService = reviewService;
        this.userService = userService;
        this.bookingService = bookingService;
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
        List<Location> locations = locationService.getAllLocations();
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

    @GetMapping("/hostinglist")
    public String getHostingListPage(Model model){
        int userID = 1;
        List<Booking> bookings = bookingService.getAllBookingsByStatusForHost(userID,"pending");
        List<Location> locations = locationService.getAllLocationsForUser(userID);
        HashMap<Integer,List<Booking>> bookingsMap = new HashMap<>();
        locations.forEach(location -> {
            bookingsMap.put(location.getId(),bookings.stream().filter(booking -> booking.getLocation().getId() == location.getId() && location.isActive()).collect(Collectors.toList()));
        });
        model.addAttribute("bookingsMap",bookingsMap);
        model.addAttribute("locations",locations);
        return "hostinglist";
    }

    @GetMapping("/profile")
    public String getMyProfilePage(Model model){
        int userId = 1;
        return "redirect:/user/"+userId;
    }

    @GetMapping("/editProfile")
    public String getEditProfileForm(Model model){
        model.addAttribute("user", new User());
        return "editprofile";
    }

    @PostMapping("/saveProfile")
    public String saveProfile(@ModelAttribute("user") User user, Model model){
        log.warning(user.toString());
        user = userService.saveUser(user);
        log.warning(user.toString());
        return "redirect:/user/"+user.getId();
    }
}