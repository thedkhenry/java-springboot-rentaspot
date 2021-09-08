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

    public Message saveMessage(Message message){
        return messageRepo.save(message);
    }

    public List<Message> getAllMessagesBetweenUsers(int receiverId, int senderId){
        return messageRepo.findAllByReceiverIdAndSenderIdOrSenderIdAndReceiverId(receiverId, senderId,receiverId,senderId);
    }

    public List<Message> getAllMessagesForUser(int id){
        return messageRepo.findAllByReceiverIdOrSenderId(id, id);
    }
}