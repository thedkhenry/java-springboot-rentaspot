package com.henrysican.rentaspot.controllers;

import com.henrysican.rentaspot.models.Booking;
import com.henrysican.rentaspot.models.Location;
import com.henrysican.rentaspot.models.Review;
import com.henrysican.rentaspot.models.User;
import com.henrysican.rentaspot.security.AppUserDetailsService;
import com.henrysican.rentaspot.security.AppUserPrincipal;
import com.henrysican.rentaspot.services.BookingService;
import com.henrysican.rentaspot.services.LocationService;
import com.henrysican.rentaspot.services.ReviewService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Log
@Controller
public class HomeController {
    private final LocationService locationService;
    private final BookingService bookingService;
    private final ReviewService reviewService;
    private final AppUserDetailsService appUserDetailsService;

    @Autowired
    public HomeController(LocationService locationService,
                          BookingService bookingService,
                          ReviewService reviewService,
                          AppUserDetailsService appUserDetailsService){
        this.locationService = locationService;
        this.bookingService = bookingService;
        this.reviewService = reviewService;
        this.appUserDetailsService = appUserDetailsService;
    }

    @GetMapping({"/","/home"})
    public String getHomePage(@AuthenticationPrincipal AppUserPrincipal principal, Model model){
        List<Location> topRatedLocations = locationService.get10HighlyRatedLocations();
        List<Location> recentlyAddedLocations = locationService.get10RecentlyAddedLocations();
        if (principal == null) {
            log.warning("AuthenticationPrincipal AppUserPrincipal  IT IS NULL");
        } else {
            log.warning("AuthenticationPrincipal AppUserPrincipal " + principal.getId());
            log.warning("AuthenticationPrincipal AppUserPrincipal " + principal.getUsername());
            log.warning("AuthenticationPrincipal AppUserPrincipal " + principal.getPassword());
        }
        model.addAttribute("topLocations", topRatedLocations);
        model.addAttribute("newLocations", recentlyAddedLocations);
        return "home";
    }


//TODO: Message 'You missed X reservations.'
    @GetMapping("/hostinglist")
    public String getHostingListPage(Principal principal, Model model){
        AppUserPrincipal userDetails = (AppUserPrincipal) appUserDetailsService.loadUserByUsername(principal.getName());
        long deletedCount = bookingService.deleteExpiredBookingsForLocations(locationService.getAllLocationsForUser(userDetails.getId()));
        List<Location> locations = locationService.getAllLocationsForUser(userDetails.getId());
        model.addAttribute("locations",locations);
        log.warning("/hostinglist DELETED "+deletedCount);
        return "hostinglist";
    }

    @GetMapping("/review/{bookingId}")
    public String getReviewForm(@PathVariable("bookingId") Booking booking, Model model, @AuthenticationPrincipal AppUserPrincipal principal){
        if (booking.getCustomer().getId() != principal.getId()){
            return "redirect:/403";
        }
        model.addAttribute("booking", booking);
        model.addAttribute("review", new Review());
        return "createreview";
    }

    @PostMapping("/submitReview/{bookingId}")
    public String submitReview(@PathVariable("bookingId") Booking booking, @ModelAttribute("review") Review review){
        log.warning(review.toString());
        if (booking.needsReview()) {
            review.setLocation(booking.getLocation());
            review.setUser(booking.getCustomer());
            review.setBooking(booking);
            booking.setHasReview(true);
            reviewService.saveReview(review);
            bookingService.saveBooking(booking);
        }else{
            log.warning(booking.getId() + " doesn't need a review!!!");
        }
        return "redirect:/location/"+booking.getLocation().getId();
    }
}