package com.henrysican.rentaspot.services;

import com.henrysican.rentaspot.dao.UserRepo;
import com.henrysican.rentaspot.models.Location;
import com.henrysican.rentaspot.models.Message;
import com.henrysican.rentaspot.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserService {
    private final UserRepo userRepo;
    private final MessageService messageService;

    @Autowired
    public UserService(UserRepo userRepo,
                       MessageService messageService){
        this.userRepo = userRepo;
        this.messageService = messageService;
    }

    public User saveUser(User user){
        return userRepo.save(user);
    }

    public boolean addToWishlist(int userId, Location location){
        User user = userRepo.getById(userId);
        boolean wishlisted = user.getWishlist().stream().anyMatch(location1 -> location1.getId() == location.getId());
        if(!location.isActive() || wishlisted){
            return false;
        }
        return user.getWishlist().add(location);
    }

    public boolean removeFromWishlist(int userId, Location location){
        User user = userRepo.getById(userId);
        return user.getWishlist().removeIf(loc -> loc.getId() == location.getId());
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

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    public List<User> getAllUsersWithImages(){
        return userRepo.findAllByProfileImageNotNull();
    }

    public List<User> getAllContactsForUser(int id){
        Set<Integer> userIdSet = new HashSet<>();
        List<Message> messages = messageService.getAllMessagesForUser(id);
        messages.forEach(message -> {
            if (message.getReceiverId() != id) {
                userIdSet.add(message.getReceiverId());
            }
            if (message.getSenderId() != id) {
                userIdSet.add(message.getSenderId());
            }
        });
        return userRepo.findAllById(userIdSet);
    }
}