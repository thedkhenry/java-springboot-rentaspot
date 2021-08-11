package com.henrysican.rentaspot.services;

import com.henrysican.rentaspot.dao.UserRepo;
import com.henrysican.rentaspot.models.User;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<User> getUsers(){
        return userRepo.findAll();
    }

    public User getUserById(int id){
        return userRepo.findById(id).orElse(new User());
    }
}