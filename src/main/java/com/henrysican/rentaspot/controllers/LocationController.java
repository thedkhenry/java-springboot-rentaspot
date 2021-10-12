package com.henrysican.rentaspot.controllers;

import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
import com.henrysican.rentaspot.models.*;
import com.henrysican.rentaspot.services.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Log
@Controller
public class LocationController {
    private final LocationService locationService;
    private final UserService userService;
    private final GMapService gMapService;
    private final ImageService imageService;
    private final AmazonS3Service s3Service;

    @Autowired
    public LocationController(LocationService locationService,
                              UserService userService,
                              GMapService gMapService,
                              ImageService imageService,
                              AmazonS3Service s3Service) {
        this.locationService = locationService;
        this.userService = userService;
        this.gMapService = gMapService;
        this.imageService = imageService;
        this.s3Service = s3Service;
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
                                 @RequestParam("imageFile") MultipartFile multipartFile,
                                 @RequestParam(value = "action") String action,
                                 @AuthenticationPrincipal User principal,
                                 RedirectAttributes redirectAttributes){
        Location dbLocation = locationService.getLocationById(locationId);
        if(principal.getId() != dbLocation.getUser().getId()){
            return "redirect:/403";
        }
        if(action.equals("delete")){ //else "update"
            locationService.deleteLocation(dbLocation);
            return "redirect:/hostinglist";
        }
        if(!multipartFile.isEmpty()){
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            String fileType = multipartFile.getContentType();
            if(!((fileType.equals("image/png") || fileType.equals("image/jpeg") || fileType.equals("image/gif"))
                    && (multipartFile.getSize() < 5000000))){
                redirectAttributes.addFlashAttribute("message","Max 5MB image files allowed. (png/jpeg/gif)");
                return "redirect:/edit/"+locationId;
            }
            s3Service.uploadFile("location-images", multipartFile);
            if (dbLocation.getImage() == null) {
                Image image = imageService.saveImage(new Image(fileName,fileType));
                dbLocation.setImage(image);
            } else {
                s3Service.deleteFile("location-images", dbLocation.getImage().getName());
                dbLocation.getImage().setName(fileName);
                dbLocation.getImage().setType(fileType);
            }
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
                                 @RequestParam("imageFile") MultipartFile multipartFile,
                                 @RequestParam(value = "action") String action,
                                 RedirectAttributes redirectAttributes) throws IOException, InterruptedException, ApiException {
        if(!multipartFile.isEmpty()){
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            String fileType = multipartFile.getContentType();
            if(!((fileType.equals("image/png") || fileType.equals("image/jpeg") || fileType.equals("image/gif"))
                    && (multipartFile.getSize() < 5000000))){
                redirectAttributes.addFlashAttribute("message","Max 5MB image files allowed. (png/jpeg/gif)");
                return "redirect:/create";
            }
            s3Service.uploadFile("location-images", multipartFile);
            Image image = imageService.saveImage(new Image(fileName,fileType));
            location.setImage(image);
        }
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