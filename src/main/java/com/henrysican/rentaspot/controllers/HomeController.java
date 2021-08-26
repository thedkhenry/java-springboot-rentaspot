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

    @Autowired
    public HomeController(LocationService locationService,
                          BookingService bookingService){
        this.locationService = locationService;
        this.bookingService = bookingService;
    }

    @GetMapping({"/","/home"})
    public String getHomePage(Model model){
        List<Location> topRatedLocations = locationService.get10HighlyRatedLocations();
        List<Location> recentlyAddedLocations = locationService.get10RecentlyAddedLocations();
        model.addAttribute("topLocations", topRatedLocations);
        model.addAttribute("newLocations", recentlyAddedLocations);
        return "home";
    }

//TODO: Message 'You missed X reservations.'
    @GetMapping("/hostinglist")
    public String getHostingListPage(@AuthenticationPrincipal AppUserPrincipal principal, Model model){
        long deletedCount = bookingService.deleteExpiredBookingsForLocations(locationService.getAllLocationsForUser(principal.getId()));
        List<Location> locations = locationService.getAllLocationsForUser(principal.getId());
        model.addAttribute("locations",locations);
        log.warning("/hostinglist DELETED "+deletedCount);
        return "hostinglist";
    }
}