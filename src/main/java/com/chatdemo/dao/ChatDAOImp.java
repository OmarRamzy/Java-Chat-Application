package com.chatdemo.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class ChatDAOImp implements ChatDAO {

	private static FileWriter fileWriter = null;
	private static BufferedWriter bufferedWriter = null ;
	private static BufferedReader reader = null;

	
	@Override
	public void saveDataToFile(String filePath, String data) {
		
	//	System.out.println("Saving message..");
        try {
            fileWriter = new FileWriter(new File(filePath) , true);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(data);
            bufferedWriter.newLine();
            
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

	}


	@Override
	public Map<String, Integer> getFileChatWordsAndNumberOfitsOucurrence(String filePath) {

		Map<String, Integer> wordsMap = new HashMap<String,Integer>();
		//System.out.println("get stats");
		
		try {
			reader = new BufferedReader(new FileReader(filePath));
			String line = reader.readLine();
			while (line != null) {
		//		System.out.println(line);
				String [] lineWords =	line.split(" ");
			 
			 	for(int i = 0 ; i< lineWords.length ;i++) {
			 		
			 		// to ignore statistics data in file which written in this pattern
			 		if(lineWords[i].startsWith("<") && lineWords[i].endsWith(">") )
			 				continue;
			 		
			 		//to ignore Clients name in all clients conversation file 
			 		if(lineWords[i].contains(":"))
			 				continue;
			 		
			 		if(wordsMap.containsKey(lineWords[i])) {
			 			int occurrences = wordsMap.get(lineWords[i]);
			 	       		occurrences++;
			 	     	        wordsMap.put(lineWords[i], occurrences);
			 		}
			 		else
			 			wordsMap.put(lineWords[i], 1);
			 	}
			 
				line = reader.readLine();
			}
	//		System.out.println("wordsMapSize--->"+wordsMap.size());
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		
		return wordsMap;
	}


	@Override
	public List<String> getClientMessages(String filePath) {
		
		List<String> clientMessages = new ArrayList<String>();
		
		try {
			reader = new BufferedReader(new FileReader(filePath));
			String line = reader.readLine();
			while (line != null) {
				clientMessages.add(line);	
			 
				line = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return clientMessages;
	}
	
	

}
