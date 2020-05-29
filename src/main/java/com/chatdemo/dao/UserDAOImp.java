package com.chatdemo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImp implements UserDAO {
	
	private  BufferedReader reader = null;


	@Override
	public Map<String, String> getUsersDataFromFile() {
		
		Map<String, String> usersMap = new HashMap<String,String>();
		String filePath = System.getProperty("user.dir")+"/UsersInfo/UsersData.txt"; 

		try {
			reader = new BufferedReader(new FileReader(filePath));
			System.out.println("Opening Users Info File..");
			String line = reader.readLine();
			while (line != null) {
				String [] lineWords =	line.split(" ");
				
				if(lineWords[0]!=null && lineWords[1]!=null)
				usersMap.put(lineWords[0], lineWords[1]);
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

		
		return usersMap;

	}

}
