package com.chatdemo.service;

import java.util.List;

public interface ChatService {

	public void saveMessageInClientFile(String sender , String data);
	
	public List<String> getClientMessages(String clientName);
	
	public void saveMessageInAllClintsChatHistory(String sender , String data);
	
	public void addStatisticsToClientFile(String clientName);
	
	public void addStatisticsToAllClientsChatHistory(String clientName);

}
