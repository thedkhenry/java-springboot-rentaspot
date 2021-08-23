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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
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

    @GetMapping("/login")
    public String getLoginForm(Model model){
        model.addAttribute("user",new User());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") User user, Model model){
        log.warning("/signup registerNewUser - " + user);
//        if(userService.checkUserEmailExists(user.getEmail())){
//            model.addAttribute("message","That email is already in use.");
//            return "/signup";
//        }
//        user = userService.saveUser(user);
        log.warning("/signup registerNewUser - " + user);
        //model.addAttribute("user",user);
        return "home";
    }

    @GetMapping("/signup")
    public String getSignUpForm(Model model){
        model.addAttribute("user",new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String registerNewUser(@ModelAttribute("user") User user, Model model){
        log.warning("/signup registerNewUser - " + user);
        if(userService.checkUserEmailExists(user.getEmail())){
            model.addAttribute("message","That email is already in use.");
            return "/signup";
        }
        user = userService.saveUser(user);
        log.warning("/signup registerNewUser - " + user);
        //model.addAttribute("user",user);
        return "redirect:/editProfile/"+user.getId();
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
        log.warning("/user/{userId} getUserProfile - " + user);
        model.addAttribute("locations",locations);
        model.addAttribute("user",user);
        return "profile";
    }

//TODO: Implement Session User access to own data
//TODO: DELETE EXPIRED BOOKINGS!!!!!!!!!!!!!!!!
//TODO: Message 'You missed XXX reservations.'
    @GetMapping("/hostinglist")
    public String getHostingListPage(Model model){
        int userID = 1;
        long deletedCount = bookingService.deleteExpiredBookingsForLocations(locationService.getAllLocationsForUser(userID));
        List<Location> locations = locationService.getAllLocationsForUser(userID);
        model.addAttribute("locations",locations);
        log.warning("/hostinglist DELETED "+deletedCount);
        return "hostinglist";
    }
//TODO: Remove default ID:1  &  Update to session User
    @GetMapping("/profile")
    public String getMyProfilePage(Model model){
        int userId = 1;
        return "redirect:/user/"+userId;
    }

    @GetMapping("/editProfile/{userId}")
    public String getEditProfileForm(@PathVariable("userId") int id, Model model){
        User user = userService.getUserById(id);
        log.warning("/editProfile/{userId} getEditProfileForm - " + user);
        model.addAttribute("user", user);
        return "editprofile";
    }

    @PostMapping("/saveProfile")
    public String saveProfile(@ModelAttribute("user") User user, Model model, RedirectAttributes redirectAttributes){
        log.warning("/saveProfile 1- " + user);
        log.warning("/saveProfile 2.1- " + user.getEmail());
        log.warning("/saveProfile 2.2- " + userService.getUserIdFromEmail(user.getEmail()));
        if(userService.checkUserEmailExists(user.getEmail()) && user.getId() != userService.getUserIdFromEmail(user.getEmail())){
            log.warning("/saveProfile if- That email is already in use");
            redirectAttributes.addFlashAttribute("message","That email is already in use.");
            return "redirect:/editProfile/"+user.getId();
        }
        user = userService.saveUser(user);
        log.warning("/saveProfile 3- " + user);
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
        }else{
            log.warning(bookingId + "already has a review!!!");
        }
        return "redirect:/location/"+booking.getLocation().getId();
    }
}