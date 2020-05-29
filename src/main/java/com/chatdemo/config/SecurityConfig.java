package com.chatdemo.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.chatdemo.model.User;
import com.chatdemo.service.UserService;


@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

	// inject user service to get user log info from file
	@Autowired
	UserService userService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		List<User> users = userService.getUsersDataFromFile();
		
		/*
	    auth.inMemoryAuthentication()
	     .withUser("Omar").password("{noop}Omar").roles("") 
	     .and()
	     .withUser("Adel").password("{bcrypt}$2a$10$rwExHJyKaOEcOxDfjEKecucmMah1x0o3jYw3/YzigczsrMZDQWQUu").roles("") // Adel
	     .and()
	     .withUser("Ali").password("{bcrypt}$2a$10$3FPuqgPa1aa03oGBMUzKfOdXntOuY..gZKzAM/sp.MZgPkV7OEMmq").roles(""); //Ali
	    */
	    
	    if(users.size()>0) {
	    	for(User user : users) {
	    		
	    	    auth.inMemoryAuthentication()
	   	     .withUser(user.getUserName()).password(user.getPassword()).roles("") ;

	    	}
	    }

	    
	    
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

	http.csrf().disable()
	.authorizeRequests()
	.anyRequest().authenticated()
	.and()
	.formLogin()
	.loginPage("/showMyLoginPage")
	.loginProcessingUrl("/authenticateTheUser")
	.permitAll()
	.and()
	.logout()
	.permitAll();
	
	}
	
	
	
}
