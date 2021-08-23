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
        model.addAttribute("message","$"+location.getPrice()+" × 0 day(s)  =  $0");
        model.addAttribute("booking", new Booking());
        model.addAttribute("location",location);
        return "reservelocation";
    }

//TODO: Query booked dates && Display result(s) - Cancel old bookings
    //Check valid range
        //Message if Not REDIRECT
    //Get/Show all unavailable
    //Check available
        //Message if Not REDIRECT
        //Message if Available REDIRECT
    @RequestMapping(value="/reservelocation/{locationId}", method=RequestMethod.POST,params = "action=check")
    public String checkAvailability(@PathVariable("locationId") Location location,
                                    @ModelAttribute Booking booking,
                                    Model model,
                                    RedirectAttributes redirectAttributes){
        log.warning("Check Button pressed1 " + booking);
//        if(!booking.isRangeValid()){
//            message = "Invalid date range.";
//            redirectAttributes.addFlashAttribute("message",message);
//            return "redirect:/reservelocation/checkavailability/"+location.getId();
//        }
        List<Booking> unavailableBookings = bookingService.getAllUnavailableBookingsForLocation(location.getId(), booking);
        String message = "$"+location.getPrice()+" × "+booking.calculateNumberOfDays()+" day(s)  =  $"+booking.calculateNumberOfDays()*location.getPrice();
        boolean isAvailable = unavailableBookings.size() > 0;
        log.warning("/reservelocation/{"+location.getId()+"} checkAvailability " + isAvailable + " " + unavailableBookings.size());
//        if(isAvailable){
//            redirectAttributes.addFlashAttribute("isAvailable",isAvailable);
//        }
        redirectAttributes.addFlashAttribute("message",message);
        redirectAttributes.addFlashAttribute("isAvailable",isAvailable);
        redirectAttributes.addFlashAttribute("unavailableBookings",unavailableBookings);
        redirectAttributes.addFlashAttribute("booking",booking);
        return "redirect:/reservelocation/checkavailability/"+location.getId();
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