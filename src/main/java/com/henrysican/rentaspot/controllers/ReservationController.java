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

//TODO: If Booking.LocId & LocId != ignore
//TODO: Update pathvar into Booking/Location model not int
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
    public String getReservationForm(@PathVariable("locationId") Location location, Model model){
        model.addAttribute("booking", new Booking());
        model.addAttribute("location",location);
        return "reservelocation";
    }

//TODO: Query booked dates && Display result(s) - Cancel old bookings ON REFRESH delete expired!!!
    @RequestMapping(value="/reservelocation/{locationId}", method=RequestMethod.POST,params = "action=check")
    public String checkAvailability(@PathVariable("locationId") Location location,
                                    @ModelAttribute Booking booking,
                                    RedirectAttributes redirectAttributes){
        log.warning("Check Button pressed1 " + booking);
        String message;
        if(!booking.isRangeValid()){
            message = "Invalid date range.";
            redirectAttributes.addFlashAttribute("errorMessage",message);
            return "redirect:/reservelocation/checkavailability/"+location.getId();
        }
        List<Booking> unavailableBookings = bookingService.getAllUnavailableBookingsForLocation(location.getId(), booking);
        unavailableBookings.forEach(booking1 -> log.warning("unavailableBookings " + booking1));
        boolean isAvailable = !(unavailableBookings.size() > 0);
        if(isAvailable){
            redirectAttributes.addFlashAttribute("isAvailable",isAvailable);
        }
        message = "$"+location.getPrice()+" × "+booking.calculateNumberOfDays()+" day(s)  =  $"+booking.calculateNumberOfDays()*location.getPrice();
        redirectAttributes.addFlashAttribute("message",message);
        redirectAttributes.addFlashAttribute("isAvailable",isAvailable);
        redirectAttributes.addFlashAttribute("unavailableBookings",unavailableBookings);
        redirectAttributes.addFlashAttribute("booking",booking);
        return "redirect:/reservelocation/checkavailability/"+location.getId();
    }

    @RequestMapping(value="/reservelocation/{locationId}", method=RequestMethod.POST,params = "action=reserve")
    public String submitReservation(@PathVariable("locationId") Location location,
                                    @ModelAttribute Booking booking,
                                    Model model,
                                    RedirectAttributes redirectAttributes){
        log.warning("reserve Button pressed -----" + booking);
        boolean isAvailable = bookingService.isAvailableForLocation(location.getId(),booking);
        if(!isAvailable){
            redirectAttributes.addFlashAttribute("isAvailable",!isAvailable);
            return "redirect:/reservelocation/checkavailability/"+location.getId();
        }
//TODO: Get actual Session User id
        User customer = new User();
        int randomId = ThreadLocalRandom.current().nextInt(2, 25 + 1);
        customer.setId(randomId);
        booking.setCustomer(customer);
        booking.setLocation(location);
        booking.setHost(location.getUser());
        booking.calculateNumberOfDays();
        booking.calculatePrice();
        booking.setBookingStatus("pending");
        booking = bookingService.saveBooking(booking);
        return "redirect:/reservation/"+booking.getId();
    }

    @GetMapping("/reservation/{bookingId}")
    public String getReservationDetails(@PathVariable("bookingId") Booking booking, Model model){
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