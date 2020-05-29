package com.chatdemo.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.chatdemo.service.UserService;


@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

	@Autowired
	UserService userService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		Map<String,String> users = userService.getUsersDataFromFile();
		/*
	    auth.inMemoryAuthentication()
	     .withUser("Omar").password("{noop}test").roles("") 
	     .and()
	     .withUser("Adel").password("{bcrypt}$2a$10$rwExHJyKaOEcOxDfjEKecucmMah1x0o3jYw3/YzigczsrMZDQWQUu").roles("") // Adel
	     .and()
	     .withUser("Ali").password("{bcrypt}$2a$10$3FPuqgPa1aa03oGBMUzKfOdXntOuY..gZKzAM/sp.MZgPkV7OEMmq").roles(""); //Ali
	    */
	    
	    if(users.size()>0) {
	    	for(Map.Entry<String,String> entry : users.entrySet()) {
	    		
	    	    auth.inMemoryAuthentication()
	   	     .withUser(entry.getKey()).password(entry.getValue()).roles("") ;

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
