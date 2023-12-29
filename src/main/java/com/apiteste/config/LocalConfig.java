package com.apiteste.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.apiteste.domain.User;
import com.apiteste.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Configuration
@Profile("local")
public class LocalConfig {
	
	@Autowired
	private UserRepository repository;
	
	 @PostConstruct
	 public void  startDB() {
		User u1 = new User(null,"Carlos","crrsj@gmail.com","123");
		User u2 = new User(null,"Robetro","crrsj@hotmail.com","1234");
	   	repository.saveAll(List.of(u1, u2));
	}

}
