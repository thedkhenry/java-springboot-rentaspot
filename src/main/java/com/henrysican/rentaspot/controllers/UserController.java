package com.henrysican.rentaspot.controllers;

import com.henrysican.rentaspot.models.Booking;
import com.henrysican.rentaspot.models.Location;
import com.henrysican.rentaspot.models.Review;
import com.henrysican.rentaspot.models.User;
import com.henrysican.rentaspot.security.AppUserDetailsService;
import com.henrysican.rentaspot.security.AppUserPrincipal;
import com.henrysican.rentaspot.services.BookingService;
import com.henrysican.rentaspot.services.LocationService;
import com.henrysican.rentaspot.services.ReviewService;
import com.henrysican.rentaspot.services.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
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
        User savedUser = userService.saveUser(user);
        appUserDetailsService.saveUserRole(savedUser.getId());
        Authentication authentication = authenticateUserAndSetSession(user.getEmail(),request);
        log.warning("/signup registerNewUser 2- " + savedUser);
        log.warning("/signup registerNewUser 3- " + authentication.getAuthorities());
        return "redirect:/editProfile/"+savedUser.getId();
    }
    public Authentication authenticateUserAndSetSession(String username, HttpServletRequest request){
          UserDetails userDetails = appUserDetailsService.loadUserByUsername(username);
          Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),userDetails.getAuthorities());
          SecurityContextHolder.getContext().setAuthentication(authentication);
          request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
          return authentication;
    }

    @GetMapping("/editProfile/{userId}")
    public String getEditProfileForm(@PathVariable("userId") int id, Model model, Principal principal){
        //User user = userService.getUserById(id);
        if(principal == null){
            return "/error";
        }
        AppUserPrincipal userDetails = (AppUserPrincipal) appUserDetailsService.loadUserByUsername(principal.getName());
        if(userDetails.getId() != id){
            return "/error";
        }
        User user = userService.getUserById(userDetails.getId());
        log.warning("/editProfile/{userId} getEditProfileForm 1- " + user);
        log.warning("/editProfile/{userId} getEditProfileForm 2- " + userDetails.getId());
        model.addAttribute("user", user);
        return "editprofile";
    }

    @PostMapping("/saveProfile")
    public String saveProfile(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes){
        log.warning("/saveProfile 1- " + user);
        log.warning("/saveProfile 2.1- " + user.getEmail());
        User user1 = userService.getUserById(user.getId());
        if(userService.checkUserEmailExists(user.getEmail()) && user.getId() != userService.getUserIdFromEmail(user.getEmail())){
            log.warning("/saveProfile if- That email is already in use");
            redirectAttributes.addFlashAttribute("message","That email is already in use.");
            return "redirect:/editProfile/"+user.getId();
        }
        user.setPassword(user1.getPassword());
        user = userService.saveUser(user);
        log.warning("/saveProfile 3- " + user);
        return "redirect:/user/"+user.getId();
    }

    @GetMapping("/user/{userId}")
    public String getUserProfile(@PathVariable("userId") int id, Principal principal, Model model){
        List<Location> locations = locationService.getAllActiveLocationsForUser(id);
        User user = userService.getUserById(id);
        log.warning("/user/{userId} getUserProfile - " + user);
        model.addAttribute("locations",locations);
        model.addAttribute("user",user);
        return "profile";
    }

//TODO: Remove default ID:1  &  Update to session User
    @GetMapping("/profile")
    public String getMyProfilePage(Principal principal, Model model){
        AppUserPrincipal userDetails = (AppUserPrincipal) appUserDetailsService.loadUserByUsername(principal.getName());
        return "redirect:/user/"+userDetails.getId();
    }

}