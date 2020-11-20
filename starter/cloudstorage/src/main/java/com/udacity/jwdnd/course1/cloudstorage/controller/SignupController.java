package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
@RequestMapping("/signup")
public class SignupController {
	private UserService userService;
	
	public SignupController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	public String getSignupPage() {		
		return "signup";
	}
	
	@PostMapping
	public String signupUser(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes) {
		boolean signupSucess;
		int userId = -1;
		
		if(userService.getUser(user.getUserName()) == null) {			
			userId = userService.insertUser(user);
			
			signupSucess = true;
			redirectAttributes.addFlashAttribute("signupSuccess", signupSucess);
			
			return "redirect:/login";
		}
		else {
			signupSucess = false;
			model.addAttribute("signupSuccess", signupSucess);
			return "signup";
		}
			
	}
	
}
