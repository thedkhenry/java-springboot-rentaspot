package com.henrysican.rentaspot.services;

import com.henrysican.rentaspot.dao.MessageRepo;
import com.henrysican.rentaspot.models.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MessageService {
    private final MessageRepo messageRepo;

    public MessageService(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    /**
     * Creates a new entry in the database table with the Message provided and returns it.
     * @param message   the message to be saved
     * @return          the saved message
     */
    public Message saveMessage(Message message){
        return messageRepo.save(message);
    }

    /**
     * Returns a List of Messages between 2 users.
     * @param receiverId    the ID of the receiver
     * @param senderId      the ID of the sender
     * @return              the list of messages
     */
    public List<Message> getAllMessagesBetweenUsers(int receiverId, int senderId){
        return messageRepo.findAllByReceiverIdAndSenderIdOrSenderIdAndReceiverId(receiverId, senderId,receiverId,senderId);
    }

    /**
     * Returns message records for the specified user.
     * @param id    the ID of the user
     * @return      the list of messages
     */
    public List<Message> getAllMessagesForUser(int id){
        return messageRepo.findAllByReceiverIdOrSenderId(id, id);
    }
}