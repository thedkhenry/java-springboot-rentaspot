package com.henrysican.rentaspot.services;

import com.henrysican.rentaspot.dao.UserRepo;
import com.henrysican.rentaspot.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    public User saveUser(User user){
        return userRepo.save(user);
    }

    public boolean checkUserEmailExists(String email){
        return userRepo.existsByEmail(email);
    }

    public int getUserIdFromEmail(String email){
        return userRepo.findUserByEmail(email).getId();
    }

    public User getUserById(int id){
        return userRepo.findById(id).orElse(new User());
    }

    public User getUserByEmail(String email){
        return userRepo.findUserByEmail(email);
    }
}