package com.udacity.jwdnd.course1.cloudstorage.services;

import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;

@Service
public class AuthenticationService implements AuthenticationProvider {
	
	private HashService hashService;
	private UserMapper userMapper;
	
	public AuthenticationService(UserMapper userMapper, HashService hashService) {
		this.userMapper = userMapper;
		this.hashService = hashService;
	}
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userName = authentication.getName();
		String password = authentication.getCredentials().toString();
		User storedUser = userMapper.getUser(userName);
		
		if(storedUser != null) {
			String storedSalt = storedUser.getSalt();
			String newHash = hashService.getHashedValue(password, storedSalt);
		
			if(newHash.equals(storedUser.getPassword())) {
				return new UsernamePasswordAuthenticationToken(userName, password, new ArrayList<>());
			}
		}
		
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
