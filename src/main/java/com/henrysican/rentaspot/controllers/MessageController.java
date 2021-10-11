package com.henrysican.rentaspot.controllers;

import com.henrysican.rentaspot.models.Message;
import com.henrysican.rentaspot.models.User;
import com.henrysican.rentaspot.services.MessageService;
import com.henrysican.rentaspot.services.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Log
@Controller
public class MessageController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService messageService;
    private final UserService userService;

    @Autowired
    public MessageController(MessageService messageService,
                             SimpMessagingTemplate simpMessagingTemplate,
                             UserService userService) {
        this.messageService = messageService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.userService = userService;
    }

    @GetMapping("/messages")
    public String getMessagesPage(@AuthenticationPrincipal User principal, Model model){
        model.addAttribute("users",userService.getAllContactsForUser(principal.getId()));
        return "messages";
    }

//TODO: Prevent messaging non-contacts
    @MessageMapping("/message/{userId}")
    public void processMessage(@DestinationVariable int userId,
                               @AuthenticationPrincipal User principal,
                               Message message){
        log.warning("process: "+ userId + " " + message);
        User userReceiver = userService.getUserById(userId);
        if (userReceiver == null || principal == null || principal.getId() == userReceiver.getId()){
            return;
        }
        message.setReceiverId(userReceiver.getId());
        message.setSenderId(principal.getId());
        message.setMessageContent(HtmlUtils.htmlEscape(message.getMessageContent()));
        message = messageService.saveMessage(message);
        simpMessagingTemplate.convertAndSendToUser(userReceiver.getEmail(),"/chat/messages",message);
        log.warning("process: "+ userId + " " + message);
    }

    @GetMapping("/fetch-messages/{recipientId}")
    public ResponseEntity<?> getChatMessages(@AuthenticationPrincipal User principal,
                                             @PathVariable int recipientId) {
        List<Message> messages = messageService.getAllMessagesBetweenUsers(recipientId, principal.getId());
        log.warning("getChatMessages r-s: "+ recipientId + " " + principal.getId());
        log.warning("getChatMessages size: "+ messages.size());
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/messageHost/{hostId}")
    public String sendHostMessage(@PathVariable("hostId") User host,
                                @AuthenticationPrincipal User principal,
                                Message message){
        message.setReceiverId(host.getId());
        message.setSenderId(principal.getId());
        message.setMessageContent(HtmlUtils.htmlEscape(message.getMessageContent()));
        message = messageService.saveMessage(message);
        simpMessagingTemplate.convertAndSendToUser(host.getEmail(),"/chat/messages",message);
        return "redirect:/messages";
    }
}