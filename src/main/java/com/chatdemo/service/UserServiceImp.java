package com.chatdemo.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatdemo.dao.UserDAO;


@Service
public class UserServiceImp implements UserService {

	//@Autowired
	private UserDAO userDAO;
	
	@Autowired
	public UserServiceImp (UserDAO userDAO) {
		this.userDAO=userDAO;
	}
	
	
	@Override
	public Map<String, String> getUsersDataFromFile() {
		return userDAO.getUsersDataFromFile();
	}

}
