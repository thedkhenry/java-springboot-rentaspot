package com.henrysican.rentaspot.controllers;

import com.henrysican.rentaspot.models.Image;
import com.henrysican.rentaspot.models.Location;
import com.henrysican.rentaspot.models.User;
import com.henrysican.rentaspot.services.FileService;
import com.henrysican.rentaspot.services.ImageService;
import com.henrysican.rentaspot.services.LocationService;
import com.henrysican.rentaspot.services.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Log
@Controller
@RequestMapping("/user")
public class UserController {
    private final LocationService locationService;
    private final UserService userService;
    private final FileService fileService;
    private final ImageService imageService;

    @Autowired
    public UserController(LocationService locationService,
                          UserService userService,
                          FileService fileService,
                          ImageService imageService){
        this.locationService = locationService;
        this.userService = userService;
        this.fileService = fileService;
        this.imageService = imageService;
    }

    @GetMapping("")
    public String getMyProfilePage(@AuthenticationPrincipal User principal){
        return "redirect:/user/"+principal.getId();
    }

    @GetMapping("/editProfile")
    public String getEditProfileForm(Model model, @AuthenticationPrincipal User principal){
        model.addAttribute("user", principal);
        return "editprofile";
    }

    @PostMapping("/saveProfile")
    public String saveProfile(@ModelAttribute("user") com.henrysican.rentaspot.models.User user,
                              @RequestParam("image") MultipartFile multipartFile,
                              @AuthenticationPrincipal User principal,
                              RedirectAttributes redirectAttributes){
        if(userService.checkUserEmailExists(user.getEmail()) && principal.getId() != userService.getUserIdFromEmail(user.getEmail())){
            log.warning("/saveProfile if- That email is already in use");
            redirectAttributes.addFlashAttribute("message","That email is already in use.");
            return "redirect:/user/editProfile";
        }
        if (!principal.getEmail().equals(user.getEmail())) {
            principal.setEmail(user.getEmail());
        }
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            String fileType = multipartFile.getContentType();
            if(!(fileType.equals("image/png") || fileType.equals("image/jpeg") || fileType.equals("image/gif"))){
                redirectAttributes.addFlashAttribute("message","Only image files allowed. (png/jpeg/gif)");
                return "redirect:/user/editProfile";
            }
            System.out.println("*****");
            System.out.println("*****");
            System.out.println("ctrl UPLOADING FILE...");
            System.out.println("*****");
            System.out.println("*****");
            fileService.uploadFile("user-images", multipartFile);
            if (principal.getProfileImage() == null) {
                Image image = imageService.saveImage(new Image(fileName,fileType));
                principal.setProfileImage(image);
            } else {
                fileService.deleteFile("user-images", principal.getProfileImage().getName());
                principal.getProfileImage().setName(fileName);
                principal.getProfileImage().setType(fileType);
            }
        }
        principal.setFirstName(user.getFirstName());
        principal.setLastName(user.getLastName());
        principal.setPhoneNumber(user.getPhoneNumber());
        principal.setSummary(user.getSummary());
        principal = userService.saveUser(principal);
        return "redirect:/user/"+principal.getId();
    }

    @GetMapping("/{userId}")
    public String getUserProfile(@PathVariable("userId") int id, Model model){
        List<Location> locations = locationService.getAllActiveLocationsForUser(id);
        User user = userService.getUserById(id);
        model.addAttribute("locations",locations);
        model.addAttribute("user",user);
        return "profile";
    }
}