package com.chatdemo.test;

import static org.junit.Assert.assertTrue;
import java.util.List;
//import org.junit.Test;
import com.chatdemo.dao.ChatDAO;
import com.chatdemo.dao.ChatDAOImp;
import com.chatdemo.service.ChatService;
import com.chatdemo.service.ChatServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChatServiceTest {
	
	ChatDAO chatDAO = new ChatDAOImp() ;
	ChatService chatService = new ChatServiceImp(chatDAO);
		
	@Test
	public void saveMessageInClientDumpFile () {
		
		chatService.saveMessageInClientFile("Omar", "Test Testing");
		
		
		assertTrue(chatService.getClientMessages("Omar").contains("Test Testing"));
	}
	
	@Test
	public void addStatisticsToClientDumpFile () {
		
		chatService.saveMessageInClientFile("Omar", "Hi Hi Testing");
		chatService.addStatisticsToClientFile("Omar");
		List<String> msgs = chatService.getClientMessages("Omar");
		
		//chatService.getClientMessages("Omar").stream().forEach(l-> System.out.println(l));
		
		
		String temp="";
		for(String s : msgs){
			if(s.contains("<Hi>"))
				temp=s;
		}
		// 4 is <Hi> size
		String wordOuccurencesNumber= " ";
		if(temp.length()>4) {
				String[] wordsOuccurencesNumber = temp.split("<---->");
				String number = wordsOuccurencesNumber[1];
				wordOuccurencesNumber= number.substring(2, number.length() -1);
		}

		int numberOfHiWordOccurence = Integer.parseInt(wordOuccurencesNumber);
		
		//we compare to 2, because of its number of repeat <Hi> word in test case.
		assertTrue(numberOfHiWordOccurence>= 2);
	}

	
	
}

