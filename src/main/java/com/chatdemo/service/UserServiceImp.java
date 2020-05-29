package com.chatdemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatdemo.dao.UserDAO;
import com.chatdemo.model.User;


@Service
public class UserServiceImp implements UserService {

	//@Autowired
	private UserDAO userDAO;
	
	@Autowired
	public UserServiceImp (UserDAO userDAO) {
		this.userDAO=userDAO;
	}
	
	
	@Override
	public List<User> getUsersDataFromFile() {
		return userDAO.getUsersDataFromFile();
	}

}
