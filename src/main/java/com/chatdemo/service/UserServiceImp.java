package com.chatdemo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatdemo.dao.UserDAO;
import com.chatdemo.model.User;


@Service
public class UserServiceImp implements UserService {
	
	static {
		activeUsers= new ArrayList<String>();
	}

	//@Autowired
	private UserDAO userDAO;
	
	// store users that in chat currently to overcome one browser session issue,
	// as if user log in and opens another tab he will be in same session and  
	 public	static List<String> activeUsers ;

	
	@Autowired
	public UserServiceImp (UserDAO userDAO) {
		this.userDAO=userDAO;
	}
	
	
	@Override
	public List<User> getUsersDataFromFile() {
		return userDAO.getUsersDataFromFile();
	}

}
