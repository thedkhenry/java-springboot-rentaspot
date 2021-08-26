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

import java.util.List;
import java.util.Optional;

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
        List<AuthGroup> authGroups = authRepo.findAllByUserId(user.getId());
        return new AppUserPrincipal(user,authGroups);
    }

    public void saveUserRole(long userId, String userEmail, String role){
        authRepo.save(new AuthGroup(userId, userEmail, role));
    }
    public void updateUserEmail(long userId, String userEmail) {
        Optional<AuthGroup> authGroup = authRepo.findByUserId(userId);
        authGroup.get().setUserEmail(userEmail);
        authRepo.save(authGroup.get());
    }
}
