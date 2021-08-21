package com.henrysican.rentaspot.controllers;

import com.henrysican.rentaspot.models.Booking;
import com.henrysican.rentaspot.models.Location;
import com.henrysican.rentaspot.models.User;
import com.henrysican.rentaspot.services.BookingService;
import com.henrysican.rentaspot.services.LocationService;
import com.henrysican.rentaspot.services.ReviewService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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

    @GetMapping("/hostinglist/confirm/{bookingId}")
    public String confirmBooking(@PathVariable("bookingId") int bookingId, Model model){
        Booking booking = bookingService.getBookingById(bookingId);
        booking.setBookingStatus("confirmed");
        bookingService.saveBooking(booking);
        log.warning("PostMapping /hostinglist/CONFIRM/{bookingId} " + booking.getBookingStatus());
        return "redirect:/hostinglist";
    }

    @GetMapping("/hostinglist/cancel/{bookingId}")
    public String cancelBooking(@PathVariable("bookingId") int bookingId, Model model){
        Booking booking = bookingService.getBookingById(bookingId);
        booking.setBookingStatus("host cancel");
        bookingService.saveBooking(booking);
        log.warning("PostMapping /hostinglist/CANCEL/{bookingId} " + booking.getBookingStatus());
        return "redirect:/hostinglist";
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
        log.warning("Check Button pressed " + booking.getStartDate());
        log.warning("Check Button pressed " + booking.getEndDate());
//        Booking newBooking = bookingService.getBookingForLocationBetween(locationId,booking.getStartDate(),booking.getEndDate());
        Booking qBooking = bookingService.getBookingById(1);
//        log.warning("newBooking " + newBooking);
        log.warning("qBooking " + qBooking.getLocation());
        log.warning("qBooking " + qBooking.getStartDate());
        log.warning("qBooking " + qBooking.getEndDate());
//        double price = booking.calculatePrice();

        //Display table? 'Dates' 'Status' 'Expires in'
        //Add inputdate limti? Ex: max="1979-12-31"
        /*
        query booking dates
        cancel old created booking if time > hour
        or submit new booking  
         */

        redirectAttributes.addFlashAttribute("bookings",booking);
//        redirectAttributes.addFlashAttribute("price",price);
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
        booking.setBookingStatus("pending");
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
        bookingList.forEach(booking -> {
            log.warning("" + booking.getId() + "  " + booking.getLocation().getId());
            log.warning("DaysFrom: " + booking.calculateDaysFromEndDate() + " - " + booking.getBookingStatus() + " -  " + booking.getPrice() + "  (" + booking.calculateNumberOfDays() + " $" + booking.getLocation().getPrice() + ") = $" + (booking.calculateNumberOfDays() * booking.getLocation().getPrice()));
            log.warning("" + booking.needsReview());
        });
        model.addAttribute("bookingList", bookingList);
        return "reservations";
    }
}