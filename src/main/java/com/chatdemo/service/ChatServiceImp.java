package com.chatdemo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatdemo.dao.ChatDAO;


@Service
public class ChatServiceImp implements ChatService {
	
	//@Autowired
	private ChatDAO chatDAO ;
	
	@Autowired
	public ChatServiceImp(ChatDAO chatDAO) {
		this.chatDAO=chatDAO;
	}
	
	// store users that in chat currently to overcome one browser session issue,
	// as if user log in and opens another tab he will be in same session and  
	 public	static List<String> users = new ArrayList<String>();


	@Override
	public void saveMessageInClientFile(String sender, String message) {
		
		String filePath = System.getProperty("user.dir")+"/ChatHistory/"+sender+".txt"; 
		
	//	System.out.println("Saving..");
		//save data to client dump file
		chatDAO.saveDataToFile(filePath , message);
		
	}
	
	
	
	@Override
	public void saveMessageInAllClintsChatHistory(String sender , String message) {
		
		//save message in full conversation file
		  String filePath = System.getProperty("user.dir")+"/ChatHistory/AllClients/History.txt";
	      String data = sender.concat(": ").concat(message);
		   
			
		   chatDAO.saveDataToFile(filePath , data); 

		
	}

	@Override
	public void addStatisticsToClientFile(String clientName) {
		
		String filePath = System.getProperty("user.dir")+"/ChatHistory/"+clientName+".txt"; 

		Map<String,Integer> wordsMap = chatDAO.getFileChatWordsAndNumberOfitsOucurrence(filePath);		

	    chatDAO.saveDataToFile(filePath , "<------------------------------------------------>" );
	    chatDAO.saveDataToFile(filePath , "<Number_of_each_word_ocurrences!>" );

	    if(wordsMap.size()>1) {
	    	for(Map.Entry<String,Integer> entry : wordsMap.entrySet()) {
	      
	    	//	String data = entry.getKey() .concat("<--->").concat(entry.getValue().toString());
	    		String data = "<".concat(entry.getKey()).concat(">").concat(" <----> ")
	    				.concat("<").concat(entry.getValue().toString()).concat(">");
	    		chatDAO.saveDataToFile(filePath , data );
	     
	    	}
	    }
	    chatDAO.saveDataToFile(filePath , "<------------------------------------------------>" );


	}

	@Override
	public void addStatisticsToAllClientsChatHistory(String clientName) {
		
		String filePath = System.getProperty("user.dir")+"/ChatHistory/AllClients/History.txt";
		
		Map<String,Integer> wordsMap = chatDAO.getFileChatWordsAndNumberOfitsOucurrence(filePath);		
		
	    chatDAO.saveDataToFile(filePath , "<------------------------------------------------>" );
	    chatDAO.saveDataToFile(filePath , "<Number_of_Each_word_oucrreence_After:"+ clientName + "_left>" );

	    
	    for(Map.Entry<String,Integer> entry : wordsMap.entrySet()) {
	      
	    //	String data = entry.getKey() .concat(" ---> ").concat(entry.getValue().toString());
    		String data = "<".concat(entry.getKey()).concat(">").concat(" <----> ")
    				.concat("<").concat(entry.getValue().toString()).concat(">");

	    	chatDAO.saveDataToFile(filePath , data );
	     
	    }
	    
	    chatDAO.saveDataToFile(filePath , "<------------------------------------------------>" );


	}



	@Override
	public List<String> getClientMessages(String clientName) {
		String filePath = System.getProperty("user.dir")+"/ChatHistory/"+clientName+".txt"; 
		return chatDAO.getClientMessages(filePath);
	}


}
