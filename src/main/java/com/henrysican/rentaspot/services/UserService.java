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

    /**
     * Creates a new entry in the database table with the User provided and returns it.
     * @param user      the user to be saved
     * @return          the saved user
     */
    public User saveUser(User user){
        return userRepo.save(user);
    }

    /**
     * Adds the given Location to the specified User's wishlist. No duplicates are added and
     * only an active Location can be added.
     * @param userId        the ID of the user
     * @param location      the location to be added
     * @return              true if the location was added, false otherwise
     */
    public boolean addToWishlist(int userId, Location location){
        User user = userRepo.getById(userId);
        boolean wishlisted = user.getWishlist().stream().anyMatch(location1 -> location1.getId() == location.getId());
        if(!location.isActive() || wishlisted){
            return false;
        }
        return user.getWishlist().add(location);
    }

    /**
     * Removes the given Location from the specified User's wishlist, if present.
     * @param userId        the ID of the user
     * @param location      the location to be removed
     * @return              true if the location was removed, false otherwise
     */
    public boolean removeFromWishlist(int userId, Location location){
        User user = userRepo.getById(userId);
        return user.getWishlist().removeIf(loc -> loc.getId() == location.getId());
    }

    /**
     * Checks if a User in the database exists with the given email.
     * @param email     the email to be checked
     * @return          true if email exists, false otherwise
     */
    public boolean checkUserEmailExists(String email){
        return userRepo.existsByEmail(email);
    }

    /**
     * Returns the ID of the User with the given email.
     * @param email     the user email
     * @return          the ID of the user
     */
    public int getUserIdFromEmail(String email){
        return userRepo.findUserByEmail(email).getId();
    }

    /**
     * Returns the User with the specified ID.
     * @param id    the ID of the user
     * @return      the user
     */
    public User getUserById(int id){
        return userRepo.findById(id).orElse(new User());
    }

    /**
     * Returns the User with the specified email.
     * @param email     the email of the user
     * @return          the user
     */
    public User getUserByEmail(String email){
        return userRepo.findUserByEmail(email);
    }

    /**
     * Returns a List of all Users in the database.
     * @return  the list of users
     */
    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    /**
     * Returns a List of all Users that have set a profile image.
     * @return  the list of users
     */
    public List<User> getAllUsersWithImages(){
        return userRepo.findAllByProfileImageNotNull();
    }

    /**
     * Returns a List of all Users that have messaged the specified User.
     * @param id    the ID of the user
     * @return      the list of users
     */
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