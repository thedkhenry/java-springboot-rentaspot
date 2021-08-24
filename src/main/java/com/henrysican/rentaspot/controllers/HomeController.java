package com.henrysican.rentaspot.controllers;

import com.henrysican.rentaspot.models.Booking;
import com.henrysican.rentaspot.models.Location;
import com.henrysican.rentaspot.models.Review;
import com.henrysican.rentaspot.models.User;
import com.henrysican.rentaspot.security.AppUserDetailsService;
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Log
@Controller
public class HomeController {
    private final LocationService locationService;
    private final BookingService bookingService;
    private final ReviewService reviewService;

    @Autowired
    public HomeController(LocationService locationService,
                          BookingService bookingService,
                          ReviewService reviewService){
        this.locationService = locationService;
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