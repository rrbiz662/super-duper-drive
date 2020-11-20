package com.udacity.jwdnd.course1.cloudstorage.services;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;

@Service
public class UserService {
	
	private UserMapper userMapper;
	private HashService hashService;
	
	public UserService(UserMapper userMapper, HashService hashService) {
		this.userMapper = userMapper;
		this.hashService = hashService;
	}
	
	public int insertUser(User user) {
		byte[] salt = new byte[16];
		SecureRandom random = new SecureRandom();
		String encodedSalt = null;
		
		random.nextBytes(salt);
		encodedSalt = Base64.getEncoder().encodeToString(salt);
		
		// Update user. 
		user.setSalt(encodedSalt);
		user.setPassword(hashService.getHashedValue(user.getPassword(), encodedSalt));		
		
		return this.userMapper.insertUser(user);		
	}
	
	public User getUser(String userName) {
		return userMapper.getUser(userName);
	}
	
	public boolean updateUser(User user) {
		return userMapper.updateUser(user);	
	}
	
	public boolean deleteUser(String userName) {
		return userMapper.deleteUser(userName);
	}
	
}
