package com.henrysican.rentaspot.controllers;

import com.henrysican.rentaspot.models.Location;
import com.henrysican.rentaspot.models.User;
import com.henrysican.rentaspot.security.AppUserDetailsService;
import com.henrysican.rentaspot.security.AppUserPrincipal;
import com.henrysican.rentaspot.services.LocationService;
import com.henrysican.rentaspot.services.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Log
@Controller
public class UserController {
    private final LocationService locationService;
    private final UserService userService;
    private final AppUserDetailsService appUserDetailsService;
    @Autowired
    protected AuthenticationManager authenticationManager;
    @Autowired
    public UserController(LocationService locationService,
                          UserService userService,
                          AppUserDetailsService appUserDetailsService){
        this.locationService = locationService;
        this.userService = userService;
        this.appUserDetailsService = appUserDetailsService;
    }

    @GetMapping("/login")
    public String getLoginForm(Model model){
        model.addAttribute("user",new User());
        return "login";
    }

    @GetMapping("/signup")
    public String getSignUpForm(Model model){
        model.addAttribute("user",new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String registerNewUser(@ModelAttribute("user") User user, HttpServletRequest request, Model model){
        log.warning("/signup registerNewUser 1- " + user);
        if(userService.checkUserEmailExists(user.getEmail())){
            model.addAttribute("message","That email is already in use.");
            return "/signup";
        }
        String email = user.getEmail();
        String password = user.getPassword();
        user.setPassword(new BCryptPasswordEncoder(4).encode(password));
        User savedUser = userService.saveUser(user);
        appUserDetailsService.saveUserRole(savedUser.getId(),savedUser.getEmail(), "ROLE_USER");
        try {
            request.login(email, password);
        }
        catch (ServletException e) {
            e.printStackTrace();
        }
        log.warning("/signup registerNewUser 2- " + savedUser);
        return "redirect:/editProfile";
    }

    @GetMapping("/editProfile")
    public String getEditProfileForm(Model model, Principal principal){
        User user = userService.getUserByEmail(principal.getName());
        log.warning("/editProfile/{userId} getEditProfileForm 1- " + user);
        model.addAttribute("user", user);
        return "editprofile";
    }

    @PostMapping("/saveProfile")
    public String saveProfile(@ModelAttribute("user") User user, Principal principal, RedirectAttributes redirectAttributes){
        log.warning("/saveProfile 1- " + user);
        log.warning("/saveProfile 2.1- " + user.getEmail());
        User dbUser = userService.getUserById(user.getId());
        if(userService.checkUserEmailExists(user.getEmail()) && user.getId() != userService.getUserIdFromEmail(user.getEmail())){
            log.warning("/saveProfile if- That email is already in use");
            redirectAttributes.addFlashAttribute("message","That email is already in use.");
            return "redirect:/editProfile";
        }
        dbUser.setFirstName(user.getFirstName());
        dbUser.setLastName(user.getLastName());
        if (!dbUser.getEmail().equals(user.getEmail())) {
            dbUser.setEmail(user.getEmail());
            appUserDetailsService.updateUserEmail(dbUser.getId(),dbUser.getEmail());
        }
        dbUser.setPhoneNumber(user.getPhoneNumber());
        dbUser.setProfileImage(user.getProfileImage());
        dbUser.setSummary(user.getSummary());
        dbUser = userService.saveUser(dbUser);
        log.warning("/saveProfile 3- " + dbUser);
        return "redirect:/user/"+user.getId();
    }

    @GetMapping("/user/{userId}")
    public String getUserProfile(@PathVariable("userId") int id, Model model){
        List<Location> locations = locationService.getAllActiveLocationsForUser(id);
        User user = userService.getUserById(id);
        log.warning("/user/{userId} getUserProfile - " + user);
        model.addAttribute("locations",locations);
        model.addAttribute("user",user);
        return "profile";
    }

    @GetMapping("/profile")
    public String getMyProfilePage(Principal principal){
        AppUserPrincipal userDetails = (AppUserPrincipal) appUserDetailsService.loadUserByUsername(principal.getName());
        return "redirect:/user/"+userDetails.getId();
    }

}