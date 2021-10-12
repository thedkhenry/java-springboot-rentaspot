package com.henrysican.rentaspot.dao;

import com.henrysican.rentaspot.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<Message, Long> {
    List<Message> findAllByReceiverIdAndSenderIdOrSenderIdAndReceiverId(int receiverId, int senderId, int senderId2, int receiverId2);
    List<Message> findAllByReceiverIdOrSenderId(int receiverId, int senderId);
    Message findFirstByReceiverIdAndSenderIdOrSenderIdAndReceiverIdOrderByCreatedAtDesc(int receiverId, int senderId, int senderId2, int receiverId2);
}