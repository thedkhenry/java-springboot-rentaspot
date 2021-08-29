package com.henrysican.rentaspot.controllers;

import com.henrysican.rentaspot.models.Booking;
import com.henrysican.rentaspot.models.Location;
import com.henrysican.rentaspot.models.User;
import com.henrysican.rentaspot.security.AppUserPrincipal;
import com.henrysican.rentaspot.services.BookingService;
import com.henrysican.rentaspot.services.UserService;
import com.henrysican.rentaspot.models.BookingStatus;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Log
@Controller
@RequestMapping("/reservations")
public class ReservationController {
    private final BookingService bookingService;
    private final UserService userService;

    @Autowired
    public ReservationController(BookingService bookingService, UserService userService){
        this.bookingService = bookingService;
        this.userService = userService;
    }

//TODO: Add expiration time? Ex: Expires in (1 hour) (47 minutes)
//TODO: Order by soonest/upcoming
    @GetMapping("")
    public String getReservationsPage(@AuthenticationPrincipal AppUserPrincipal principal, Model model){
        bookingService.updateExpiredBookingsForCustomer(principal.getId());
        List<Booking> bookingList = bookingService.getAllBookingsForCustomer(principal.getId());
        bookingList.forEach(booking -> {
            log.warning("" + booking.getId() + "  " + booking.getLocation().getId());
            log.warning("DaysFrom: " + booking.calculateDaysFromEndDate() + " - " + booking.getBookingStatus() + " -  " + booking.getPrice() + "  (" + booking.calculateNumberOfDays() + " $" + booking.getLocation().getPrice() + ") = $" + (booking.calculateNumberOfDays() * booking.getLocation().getPrice()));
            log.warning("" + booking.needsReview());
        });
        model.addAttribute("bookingList", bookingList);
        return "reservations";
    }

    @GetMapping("/{action}/{locationId}/{bookingId}")
    public String updateBooking(@PathVariable("action") String action,
                                @PathVariable("locationId") int locationId,
                                @PathVariable("bookingId") Booking booking,
                                @AuthenticationPrincipal AppUserPrincipal principal){
        if(booking == null || booking.getHost().getId() != principal.getId()){
            return "redirect:/hostinglist";
        }
        log.warning("updateBooking " + booking);
        log.warning("updateBooking " + action + " " + locationId + " " + booking.getId());
        if (booking.calculateTimeFromCreate() >= 0 && booking.getLocation().getId() == locationId){
            if(action.equals("confirm")){
                booking.setBookingStatus(BookingStatus.CONFIRMED);
            } else{
                booking.setBookingStatus(BookingStatus.HOST_CANCELED);
            }
            bookingService.saveBooking(booking);
        }
        return "redirect:/hostinglist";
    }

    @GetMapping("/checkavailability/{locationId}")
    public String getReservationForm(@PathVariable("locationId") Location location, Model model){
        model.addAttribute("booking", new Booking());
        model.addAttribute("location",location);
        return "reservelocation";
    }

    @RequestMapping(value="/reserve/{locationId}", method=RequestMethod.POST)
    public String checkAvailability(@PathVariable("locationId") Location location,
                                    @ModelAttribute Booking booking,
                                    RedirectAttributes redirectAttributes,
                                    @RequestParam(value = "action") String action,
                                    @AuthenticationPrincipal AppUserPrincipal principal){
        log.warning("Check Button PARAM!!!! " + action);
        log.warning("Check Button booking-> " + booking);
        String message;
        if(!booking.isRangeValid()){
            message = "Invalid date range.";
            redirectAttributes.addFlashAttribute("errorMessage",message);
            return "redirect:/reservations/checkavailability/"+location.getId();
        }
        List<Booking> unavailableBookings = bookingService.getAllUnavailableBookingsForLocation(location.getId(), booking);
        boolean isAvailable = unavailableBookings.size() == 0;
        if(action.equals("check")){
            message = "$"+location.getPrice()+" × "+booking.calculateNumberOfDays()+" day(s)  =  $"+booking.calculateNumberOfDays()*location.getPrice();
            redirectAttributes.addFlashAttribute("message",message);
            redirectAttributes.addFlashAttribute("isAvailable",isAvailable);
            redirectAttributes.addFlashAttribute("unavailableBookings",unavailableBookings);
            redirectAttributes.addFlashAttribute("booking",booking);
            return "redirect:/reservations/checkavailability/"+location.getId();
        }else if(action.equals("reserve")){
            if (principal == null){
                return "redirect:/login";
            }
            if(!isAvailable){
                redirectAttributes.addFlashAttribute("isAvailable",isAvailable);
                return "redirect:/reservations/checkavailability/"+location.getId();
            }
            User user = userService.getUserById(principal.getId());
            booking.setCustomer(user);
            booking.setLocation(location);
            booking.setHost(location.getUser());
            booking.calculateNumberOfDays();
            booking.calculatePrice();
            booking.setBookingStatus(BookingStatus.PENDING);
            booking = bookingService.saveBooking(booking);
            return "redirect:/reservations/reservation/"+booking.getId();
        }
        redirectAttributes.addFlashAttribute("message","Error");
        return "redirect:/reservations/checkavailability/"+location.getId();
    }

    @GetMapping("/reservation/{bookingId}")
    public String getReservationDetails(@PathVariable("bookingId") Booking booking,
                                        @AuthenticationPrincipal AppUserPrincipal principal,
                                        Model model){
        int customerId = booking.getCustomer().getId();
        int hostId = booking.getHost().getId();
        if(customerId == principal.getId() || hostId == principal.getId()){
            model.addAttribute("booking", booking);
            model.addAttribute("message", new Object());
            return "reservationdetails";
        }
        return "redirect:/403";
    }
}