package com.henrysican.rentaspot.controllers;

import com.henrysican.rentaspot.models.Location;
import com.henrysican.rentaspot.models.User;
import com.henrysican.rentaspot.security.AppUserPrincipal;
import com.henrysican.rentaspot.services.BookingService;
import com.henrysican.rentaspot.services.LocationService;
import com.henrysican.rentaspot.services.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Log
@Controller
public class HomeController {
    private final LocationService locationService;
    private final BookingService bookingService;
    private final UserService userService;

    @Autowired
    public HomeController(LocationService locationService,
                          BookingService bookingService,
                          UserService userService){
        this.locationService = locationService;
        this.bookingService = bookingService;
        this.userService = userService;
    }

    @GetMapping({"/","/home"})
    public String getHomePage(Model model){
        List<Location> topRatedLocations = locationService.get10HighlyRatedLocations();
        List<Location> recentlyAddedLocations = locationService.get10RecentlyAddedLocations();
        model.addAttribute("topLocations", topRatedLocations);
        model.addAttribute("newLocations", recentlyAddedLocations);
        return "home";
    }

    @GetMapping({"/about"})
    public String getAboutPage(){
        return "about";
    }

    @GetMapping({"/faq"})
    public String getFaqPage(){
        return "faq";
    }

    @GetMapping({"/contact"})
    public String getContactPage(){
        return "contact";
    }

//TODO: Message 'You missed X reservations.'
    @GetMapping("/hostinglist")
    public String getHostingListPage(@AuthenticationPrincipal AppUserPrincipal principal, Model model){
        long updatedCount = bookingService.updateExpiredBookingsForLocations(locationService.getAllLocationsForUser(principal.getId()));
        List<Location> locations = locationService.getAllLocationsForUser(principal.getId());
        model.addAttribute("locations",locations);
        log.warning("/hostinglist UPDATED "+updatedCount);
        return "hostinglist";
    }

    @GetMapping("/wishlist")
    public String getWishlistPage(@AuthenticationPrincipal AppUserPrincipal principal, Model model){
        User user = userService.getUserById(principal.getId());
        model.addAttribute("locations",user.getWishlist());
        return "wishlist";
    }
}