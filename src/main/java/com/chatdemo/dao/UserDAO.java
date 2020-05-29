package com.chatdemo.dao;

import java.util.List;

import com.chatdemo.model.User;

public interface UserDAO {
	public List<User> getUsersDataFromFile();

}
