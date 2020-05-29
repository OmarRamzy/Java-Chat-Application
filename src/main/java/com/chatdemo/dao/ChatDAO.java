package com.chatdemo.dao;

import java.util.List;
import java.util.Map;

public interface ChatDAO {

	public void saveDataToFile(String filePath , String data);
	
	public Map<String, Integer> getFileChatWordsAndNumberOfitsOucurrence(String filePath);
	
	public List<String> getClientMessages(String filePath);
}
