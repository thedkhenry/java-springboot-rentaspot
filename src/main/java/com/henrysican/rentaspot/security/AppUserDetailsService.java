package com.henrysican.rentaspot.security;

import com.henrysican.rentaspot.dao.AuthRepo;
import com.henrysican.rentaspot.dao.UserRepo;
import com.henrysican.rentaspot.models.AuthGroup;
import com.henrysican.rentaspot.models.User;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Log
@Service
@Transactional
public class AppUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;
    private final AuthRepo authRepo;

    @Autowired
    public AppUserDetailsService(UserRepo userRepo, AuthRepo authRepo) {
        this.userRepo = userRepo;
        this.authRepo = authRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findUserByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("User not found. " + email);
        }
        return user;
    }

    public void saveUserRole(User user, String role){
        authRepo.save(new AuthGroup(user, role));
    }
}
