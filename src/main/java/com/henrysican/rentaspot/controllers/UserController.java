package com.henrysican.rentaspot.controllers;

import com.henrysican.rentaspot.models.Image;
import com.henrysican.rentaspot.models.Location;
import com.henrysican.rentaspot.models.User;
import com.henrysican.rentaspot.security.AppUserDetailsService;
import com.henrysican.rentaspot.security.AppUserPrincipal;
import com.henrysican.rentaspot.services.FileService;
import com.henrysican.rentaspot.services.ImageService;
import com.henrysican.rentaspot.services.LocationService;
import com.henrysican.rentaspot.services.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Log
@Controller
@RequestMapping("/user")
public class UserController {
    private final LocationService locationService;
    private final UserService userService;
    private final AppUserDetailsService appUserDetailsService;
    private final FileService fileService;
    private final ImageService imageService;

    @Autowired
    public UserController(LocationService locationService,
                          UserService userService,
                          AppUserDetailsService appUserDetailsService,
                          FileService fileService,
                          ImageService imageService){
        this.locationService = locationService;
        this.userService = userService;
        this.appUserDetailsService = appUserDetailsService;
        this.fileService = fileService;
        this.imageService = imageService;
    }

    @GetMapping("")
    public String getMyProfilePage(Principal principal){
        AppUserPrincipal userDetails = (AppUserPrincipal) appUserDetailsService.loadUserByUsername(principal.getName());
        return "redirect:/user/"+userDetails.getId();
    }

    @GetMapping("/editProfile")
    public String getEditProfileForm(Model model, Principal principal){
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);
        return "editprofile";
    }

    @PostMapping("/saveProfile")
    public String saveProfile(@ModelAttribute("user") User user,
                              @RequestParam("image") MultipartFile multipartFile,
                              Principal principal,
                              RedirectAttributes redirectAttributes){
        User dbUser = userService.getUserByEmail(principal.getName());
        if(userService.checkUserEmailExists(user.getEmail()) && dbUser.getId() != userService.getUserIdFromEmail(user.getEmail())){
            log.warning("/saveProfile if- That email is already in use");
            redirectAttributes.addFlashAttribute("message","That email is already in use.");
            return "redirect:/user/editProfile";
        }
        if (!dbUser.getEmail().equals(user.getEmail())) {
            dbUser.setEmail(user.getEmail());
            appUserDetailsService.updateUserEmail(dbUser.getId(),dbUser.getEmail());
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
            if (dbUser.getProfileImage() == null) {
                Image image = imageService.saveImage(new Image(fileName,fileType));
                dbUser.setProfileImage(image);
            } else {
                fileService.deleteFile("user-images", dbUser.getProfileImage().getName());
                dbUser.getProfileImage().setName(fileName);
                dbUser.getProfileImage().setType(fileType);
            }
        }
        dbUser.setFirstName(user.getFirstName());
        dbUser.setLastName(user.getLastName());
        dbUser.setPhoneNumber(user.getPhoneNumber());
        dbUser.setSummary(user.getSummary());
        dbUser = userService.saveUser(dbUser);
        return "redirect:/user/"+dbUser.getId();
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