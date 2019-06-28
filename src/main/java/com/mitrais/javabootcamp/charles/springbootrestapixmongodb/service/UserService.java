package com.mitrais.javabootcamp.charles.springbootrestapixmongodb.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.mitrais.javabootcamp.charles.springbootrestapixmongodb.entity.User;

public interface UserService extends UserDetailsService {
	
	void save(User user);
	
	User findByEmail(String email);

}
