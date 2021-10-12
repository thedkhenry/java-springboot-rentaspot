package com.henrysican.rentaspot.controllers;

import com.henrysican.rentaspot.models.AuthGroup;
import com.henrysican.rentaspot.models.User;
import com.henrysican.rentaspot.services.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Log
@Controller
public class AuthenticationController {
    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/403")
    public String getAccessDeniedPage(){
        return "403";
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
        String str = request.getParameter("confirmpassword");
        if(!user.getPassword().equals(str)){
            model.addAttribute("message","Passwords don't match.");
            return "redirect:/signup";
        }
        if(userService.checkUserEmailExists(user.getEmail())){
            model.addAttribute("message","That email is already in use.");
            return "redirect:/signup";
        }
        String email = user.getEmail();
        String password = user.getPassword();
        user.setPassword(new BCryptPasswordEncoder(4).encode(password));
        user.setAuthGroups(List.of(new AuthGroup(user,"ROLE_USER")));
        try {
            request.login(email, password);
        }
        catch (ServletException e) {
            e.printStackTrace();
        }
        return "redirect:/user/editProfile";
    }
}