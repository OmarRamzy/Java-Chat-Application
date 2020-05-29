package com.chatdemo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.chatdemo.model.ChatMessage;
import com.chatdemo.service.ChatService;



@Controller
public class ChatController {
	
	// store users that in chat currently to overcome one browser session issue,
	// as if user log in and opens another tab he will be in same session and  
	 public	static List<String> users = new ArrayList<String>();
	 
	 //ChatService object injection
	 @Autowired
	 private ChatService chatService ;

	
	@GetMapping("/")
	public String Hello (Model theModel) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//get current user name
		String userName= auth.getName();
		
		
		// if user already in chat (opens another tab) he will be forced to log in again 
		if (users.contains(userName)) {
			System.out.println(users.size());
			return "login-page";
		}
	    System.out.println("Add User: "+ userName );
		users.add(userName);
				
		theModel.addAttribute("userName", userName);
		return "chat-page";
	}
	
	
	@GetMapping("/showMyLoginPage")
	public String showMyLoginPage() {
		return "login-page";
	}


    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
    	
    	//save message in client dump files
    	chatService.saveMessageInClientFile(chatMessage.getSender(),chatMessage.getContent() );
    	chatService.saveMessageInAllClintsChatHistory(chatMessage.getSender(),chatMessage.getContent() );
    	
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

}
