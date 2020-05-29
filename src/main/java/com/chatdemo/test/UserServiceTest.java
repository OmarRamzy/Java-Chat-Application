package com.chatdemo.test;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//import org.junit.Test;

import com.chatdemo.dao.UserDAOImp;
import com.chatdemo.model.User;
import com.chatdemo.service.UserServiceImp;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
	
	private UserDAOImp userDAO = new UserDAOImp();
	private UserServiceImp userService = new UserServiceImp(userDAO) ;

	@Test
	public void getUsersInfoTest () {
		List<User> usersInfo = userService.getUsersDataFromFile();

		assertTrue(usersInfo.size() > 0 );

	}
	
}

