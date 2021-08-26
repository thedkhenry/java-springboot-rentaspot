package com.henrysican.rentaspot.controllers;

import com.henrysican.rentaspot.models.Booking;
import com.henrysican.rentaspot.models.Review;
import com.henrysican.rentaspot.security.AppUserPrincipal;
import com.henrysican.rentaspot.services.BookingService;
import com.henrysican.rentaspot.services.ReviewService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Log
@Controller
@RequestMapping("/review")
public class ReviewController {
    private final BookingService bookingService;
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(BookingService bookingService, ReviewService reviewService) {
        this.bookingService = bookingService;
        this.reviewService = reviewService;
    }

    @GetMapping("/create/{bookingId}")
    public String getReviewForm(@PathVariable("bookingId") Booking booking, Model model, @AuthenticationPrincipal AppUserPrincipal principal){
        if (booking.getCustomer().getId() != principal.getId()){
            return "redirect:/403";
        }
        model.addAttribute("booking", booking);
        model.addAttribute("review", new Review());
        return "createreview";
    }

    @PostMapping("/submit/{bookingId}")
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