package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
@RequestMapping("/signup")
public class SignupController {
	private UserService userService;
	private HashService hashService;

	public SignupController(UserService userService, HashService hashService) {
		this.userService = userService;
		this.hashService = hashService;
	}
	
	@GetMapping
	public String getSignupPage() {		
		return "signup";
	}
	
	@PostMapping
	public String signupUser(@ModelAttribute User user, Model model) {
		String messageDisplay;
		int userId = -1;
		
		if(userService.getUser(user.getUserName()) == null) {			
			userId = userService.insertUser(user);
			messageDisplay = "success";
		}
		else {
			messageDisplay = "error";
		}
		
		model.addAttribute("messageDisplay", messageDisplay);
		
		return "signup";		
	}
	
}
