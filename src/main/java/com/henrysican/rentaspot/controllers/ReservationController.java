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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Log
@Controller
public class ReservationController {
    private final LocationService locationService;
    private final ReviewService reviewService;
    private final BookingService bookingService;

    @Autowired
    public ReservationController(LocationService locationService, ReviewService reviewService, BookingService bookingService){
        this.locationService = locationService;
        this.reviewService = reviewService;
        this.bookingService = bookingService;
    }

    @GetMapping("/reservelocation/checkavailability/{locationId}")
    public String getReservationForm(@PathVariable("locationId") int locationId, Model model){
        Location location = locationService.getLocationById(locationId);
        model.addAttribute("booking", new Booking());
        model.addAttribute("location",location);
        return "reservelocation";
    }

//TODO: Query booked dates && Display result(s) - Cancel old bookings
    @RequestMapping(value="/reservelocation/{locationId}", method=RequestMethod.POST,params = "action=check")
    public String checkAvailability(@PathVariable("locationId") int locationId,
                                    @ModelAttribute Booking booking,
                                    Model model,
                                    RedirectAttributes redirectAttributes){
        log.warning("Check Button pressed " + booking);
        List<Booking> bookings = bookingService.getAllBookingsForLocationBetween(locationId,booking.getStartDate(),booking.getEndDate());
//        double price = booking.calculatePrice();

        //Display table? 'Dates' 'Status' 'Expires in'
        //Add inputdate max="1979-12-31" ?
        /*
        query booking dates
        cancel old created booking if time > hour
        or submit new booking
         */

        redirectAttributes.addFlashAttribute("bookings",bookings);
//        redirectAttributes.addFlashAttribute("price",price);
        redirectAttributes.addFlashAttribute("numberOfDays",booking.calculateNumberOfDays());
        redirectAttributes.addFlashAttribute("startDate",booking.getStartDate().toString());
        redirectAttributes.addFlashAttribute("endDate",booking.getEndDate().toString());
        return "redirect:/reservelocation/checkavailability/"+locationId;
    }

    @RequestMapping(value="/reservelocation/{locationId}", method=RequestMethod.POST,params = "action=submit")
    public String submitReservation(@PathVariable("locationId") int locationId,
                                    @ModelAttribute Booking booking,
                                    Model model,
                                    RedirectAttributes redirectAttributes){
        log.warning("submit Button pressed -----" + booking);
        Location location = new Location();
        location.setId(locationId);
        User host = new User();
//TODO: Get actual host id - create a service method?
        host.setId(1);
        User customer = new User();
        int randomId = ThreadLocalRandom.current().nextInt(2, 25 + 1);
        customer.setId(randomId);
        booking.setLocation(location);
        booking.setHost(host);
        booking.setCustomer(customer);
        booking.calculateNumberOfDays();
        booking.calculatePrice();
        log.warning(booking.toString());
        booking = bookingService.saveBooking(booking);
        model.addAttribute("booking",new Booking());
        return "redirect:/reservation/"+booking.getId();
    }

    @GetMapping("/reservation/{bookingId}")
    public String getReservationDetails(@PathVariable("bookingId") int bookingId, Model model){
        Booking booking = bookingService.getBookingById(bookingId);
        model.addAttribute("booking", booking);
        model.addAttribute("message", new Object());
        return "reservationdetails";
    }

//TODO: Implement review creation for Booking/Location
//TODO: Add expiration time? Ex: Expires in (1 hour) (47 minutes)
    @GetMapping("/reservations")
    public String getReservationsPage(Model model){
        int randomId = ThreadLocalRandom.current().nextInt(2, 25 + 1);
        List<Booking> bookingList = bookingService.getAllBookingsForCustomer(randomId);
        model.addAttribute("bookingList", bookingList);
        return "reservations";
    }
}