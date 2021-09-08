package com.henrysican.rentaspot.controllers;

import com.henrysican.rentaspot.models.*;
import com.henrysican.rentaspot.services.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Log
@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AddressService addressService;
    private final LocationService locationService;
    private final UserService userService;
    private final BookingService bookingService;
    private final ImageService imageService;
    private final CsvExportService csvExportService;

    @Autowired
    public AdminController(AddressService addressService,
                           LocationService locationService,
                           UserService userService,
                           BookingService bookingService,
                           ImageService imageService,
                           CsvExportService csvExportService) {
        this.addressService = addressService;
        this.locationService = locationService;
        this.userService = userService;
        this.bookingService = bookingService;
        this.imageService = imageService;
        this.csvExportService = csvExportService;
    }

    @GetMapping("/addresses")
    public String getAllAddressesPage(Model model){
        List<Address> addresses = addressService.getAddresses();
        model.addAttribute("addresses", addresses);
        return "adminaddresses";
    }

    @GetMapping("/locations")
    public String getAllLocationsPage(Model model){
        List<Location> locations = locationService.getAllLocations();
        model.addAttribute("locations", locations);
        return "adminlocations";
    }

    @GetMapping("/users")
    public String getAllUsersPage(Model model){
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "adminusers";
    }

    @GetMapping("/bookings")
    public String getAllBookingsPage(Model model){
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "adminbookings";
    }

    @GetMapping("/images")
    public String getAllImagesPage(Model model){
        List<User> users = userService.getAllUsersWithImages();
        model.addAttribute("users", users);
        return "adminuserimages";
    }

    @GetMapping("/export/addresses")
    public void exportAddresses(HttpServletResponse response) throws IOException {
        String filename = "addressdata_all.csv";
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition","attachment; filename=\""+filename+"\"");
        csvExportService.writeAddressesToCsv(response.getWriter());
    }

    @GetMapping("/export/locations")
    public void exportLocations(HttpServletResponse response) throws IOException {
        String filename = "locationdata_all.csv";
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition","attachment; filename=\""+filename+"\"");
        csvExportService.writeLocationsToCsv(response.getWriter());
    }

    @GetMapping("/export/users")
    public void exportUsers(HttpServletResponse response) throws IOException {
        String filename = "userdata_all.csv";
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition","attachment; filename=\""+filename+"\"");
        csvExportService.writeUsersToCsv(response.getWriter());
    }

    @GetMapping("/export/bookings")
    public void exportBookings(HttpServletResponse response) throws IOException {
        String filename = "bookingdata_all.csv";
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition","attachment; filename=\""+filename+"\"");
        csvExportService.writeBookingsToCsv(response.getWriter());
    }

    @GetMapping("/export/user-images")
    public void exportUserImageData(HttpServletResponse response) throws IOException {
        String filename = "userimagedata_all.csv";
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition","attachment; filename=\""+filename+"\"");
        csvExportService.writeUsersImagesToCsv(response.getWriter());
    }
}