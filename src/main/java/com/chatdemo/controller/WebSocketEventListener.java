package com.chatdemo.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.chatdemo.model.ChatMessage;
import com.chatdemo.service.ChatService;
import com.chatdemo.service.UserServiceImp;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);
    

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    
    @Autowired
    ChatService chatService;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
        System.out.println(event);
        		
		System.out.println("username from event: " + event.getUser().getName());
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username != null) {
            logger.info("User Disconnected : " + username);

            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setType(ChatMessage.MessageType.LEAVE);
            chatMessage.setSender(username);
            
            
            //remove user from chat list
            System.out.println("Reomve: "+username+" from chat users");
            UserServiceImp.activeUsers.remove(username);
    		System.out.println("Number of Current Active users: "+UserServiceImp.activeUsers.size());

            
            chatService.addStatisticsToClientFile(username);
            chatService.addStatisticsToAllClientsChatHistory(username);
            
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}
