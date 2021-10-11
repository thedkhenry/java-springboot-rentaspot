package com.henrysican.rentaspot.controllers;

import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
import com.henrysican.rentaspot.models.*;
import com.henrysican.rentaspot.services.GMapService;
import com.henrysican.rentaspot.services.LocationService;
import com.henrysican.rentaspot.services.ReviewService;
import com.henrysican.rentaspot.services.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Log
@Controller
public class LocationController {
    private final LocationService locationService;
    private final UserService userService;
    private final GMapService gMapService;

    @Autowired
    public LocationController(LocationService locationService,
                              UserService userService,
                              GMapService gMapService) {
        this.locationService = locationService;
        this.userService = userService;
        this.gMapService = gMapService;
    }

    @GetMapping("/location/{locationId}")
    public String getLocationDetails(@PathVariable("locationId") Location location,
                                     @AuthenticationPrincipal User principal,
                                     Model model){
        if(!location.isActive()){
            return "redirect:/403";
        }
        if(principal != null){
            location.getWishlistUsers().forEach(user -> log.warning(user.toString()));
            boolean isSaved = location.getWishlistUsers().stream().anyMatch(user -> user.getId() == principal.getId());
            model.addAttribute("isSaved",isSaved);
        }
        model.addAttribute("location",location);
        model.addAttribute("reviews",location.getReviews());
        return "locationdetails";
    }

    @GetMapping("/location/{action}/{locationId}")
    public String updateLocationActive(@PathVariable("action") String action,
                                       @PathVariable("locationId") Location location,
                                       @AuthenticationPrincipal User principal){
        if(principal.getId() != location.getUser().getId()){
            return "redirect:/403";
        }
        if(action.equals("unlist")){
            locationService.updateLocationActive(location.getId(), false);
        }else if (action.equals("publish")){
            locationService.updateLocationActive(location.getId(), true);
        }
        return "redirect:/hostinglist";
    }

//TODO: Add location edit form validation
    @GetMapping("/edit/{locationId}")
    public String getEditForm(@PathVariable("locationId") Location location,
                              @AuthenticationPrincipal User principal,
                              Model model){
        if(principal.getId() != location.getUser().getId()){
            return "redirect:/403";
        }
        model.addAttribute("location", location);
        return "editlisting";
    }

    @PostMapping("/update/{locationId}")
    public String updateLocation(@PathVariable("locationId") int locationId,
                                 @ModelAttribute Location location,
                                 @RequestParam(value = "action") String action,
                                 @AuthenticationPrincipal User principal){
        Location dbLocation = locationService.getLocationById(locationId);
        if(principal.getId() != dbLocation.getUser().getId()){
            return "redirect:/403";
        }
        if(action.equals("delete")){ //else "update"
            locationService.deleteLocation(dbLocation);
            return "redirect:/hostinglist";
        }
        dbLocation.setActive(location.isActive());
        dbLocation.setTitle(location.getTitle());
        dbLocation.setDescription(location.getDescription());
        dbLocation.setTotalOccupancy(location.getTotalOccupancy());
        dbLocation.setPrice(location.getPrice());
        dbLocation.setHasVideoMonitoring(location.isHasVideoMonitoring());
        dbLocation.setEnclosed(location.isEnclosed());
        dbLocation.setStreetParking(location.isStreetParking());
        dbLocation.setHasRvParking(location.isHasRvParking());
        dbLocation.setHasEvCharging(location.isHasEvCharging());
        locationService.saveLocation(dbLocation);
        return "redirect:/hostinglist";
    }

//TODO: Add form validation
    @GetMapping("/create")
    public String getCreateForm(Model model){
        model.addAttribute("location", new Location());
        model.addAttribute("address", new Address());
        model.addAttribute("states", UsState.values());
        return "createlisting";
    }

    @PostMapping("/create")
    public String createLocation(@ModelAttribute Location location,
                                 @AuthenticationPrincipal User principal,
                                 @RequestParam(value = "action") String action) throws IOException, InterruptedException, ApiException {
        LatLng latLng = gMapService.getLatLng(location.getAddress().getFullAddress());
        principal.setHost(true);
        location.setUser(principal);
        location.setActive(action.equals("publish"));
        location.getAddress().setCountry("US");
        location.getAddress().setLatitude(latLng.lat);
        location.getAddress().setLongitude(latLng.lng);
        locationService.saveLocation(location);
        userService.saveUser(principal);
        return "redirect:/hostinglist";
    }

    @PostMapping("/wishlist/{locationId}")
    public ResponseEntity<?> wishlistLocation(@PathVariable("locationId") Location location,
                                 @AuthenticationPrincipal User principal,
                                 HttpServletRequest request){
        String action = request.getParameter("action");
        if(action.equals("add")){
            userService.addToWishlist(principal.getId(), location);
        }else if(action.equals("remove")){
            userService.removeFromWishlist(principal.getId(), location);
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
}