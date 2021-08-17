package com.henrysican.rentaspot.controllers;

import com.henrysican.rentaspot.models.Booking;
import com.henrysican.rentaspot.models.Location;
import com.henrysican.rentaspot.models.Review;
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
    private final UserService userService;
    private final BookingService bookingService;
    private final ReviewService reviewService;

    @Autowired
    public HomeController(LocationService locationService,
                          UserService userService,
                          BookingService bookingService,
                          ReviewService reviewService){
        this.locationService = locationService;
        this.userService = userService;
        this.bookingService = bookingService;
        this.reviewService = reviewService;
    }

    @GetMapping({"/","/home"})
    public String getHomePage(Model model){
        List<Location> topRatedLocations = locationService.get10HighlyRatedLocations();
        List<Location> recentlyAddedLocations = locationService.get10RecentlyAddedLocations();
        model.addAttribute("topLocations", topRatedLocations);
        model.addAttribute("newLocations", recentlyAddedLocations);
        return "home";
    }

    @GetMapping("/user/{userId}")
    public String getUserProfile(@PathVariable("userId") int id, Model model){
        List<Location> locations = locationService.getAllActiveLocationsForUser(id);
        User user = userService.getUserById(id);
        model.addAttribute("locations",locations);
        model.addAttribute("user",user);
        return "profile";
    }

//TODO: Implement booking Acceptance/Confirmation/Cancellation
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

    @GetMapping("/review/{bookingId}")
    public String getReviewForm(@PathVariable("bookingId") int bookingId, Model model){
        Booking booking = bookingService.getBookingById(bookingId);
        model.addAttribute("booking", booking);
        model.addAttribute("review", new Review());
        return "createreview";
    }

//TODO: Add BookingId to new Review
    @PostMapping("/submitReview/{bookingId}")
    public String submitReview(@PathVariable("bookingId") int bookingId, @ModelAttribute("review") Review review, Model model){
        //check if reviewed already
        log.warning(review.toString());
        Booking booking = bookingService.getBookingById(bookingId);
        if(!booking.needsReview()){
            log.warning(bookingId + "already has a review!!!");
        }
        if (booking.needsReview()) {
            Location newLoc = new Location();
            newLoc.setId(booking.getLocation().getId());
            review.setLocation(newLoc);
            User newUser = new User();
            newUser.setId(booking.getLocation().getId());
            review.setUser(newUser);
            review = reviewService.saveReview(review);
            booking.setHasReview(true);
            bookingService.saveBooking(booking);
        }
        return "redirect:/location/"+booking.getLocation().getId();
    }
}