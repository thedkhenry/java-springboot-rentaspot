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

//TODO: use service instead of repo. HACKY!! Aug.9.4:13.30

    @Autowired
    public AppUserDetailsService(UserRepo userRepo, AuthRepo authRepo) {
        this.userRepo = userRepo;
        this.authRepo = authRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userRepo.findUserByEmail(s));
        if(user.isEmpty()){
            throw new UsernameNotFoundException("User not found. " + s);
        }
        List<AuthGroup> authGroups = authRepo.findByUserId(user.get().getId());
        return new AppUserPrincipal(user.get(),authGroups);
    }

    public void saveUserRole(long userId ){
        authRepo.save(new AuthGroup(userId, "ROLE_USER"));
    }
}
