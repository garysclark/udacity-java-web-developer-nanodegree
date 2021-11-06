package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
@RequestMapping(SignupController.SIGNUP_ENDPOINT)
public class SignupController {
	
	private static final String SIGNUP_SUCCESS_KEY = "signupSuccess";
	public static final String SIGNUP_ERROR_KEY = "signupError";
	public static final String SIGNUP_ERROR_MESSAGE = "There was an error signing you up. Please try again.";
	public static final String SIGNUP_ERROR_MESSAGE_USERNAME_ALREADY_EXISTS = "The username already exists";
	public static final String SIGNUP_RESPONSE = "signup";
	public static final String SIGNUP_ENDPOINT = "/signup";
	private UserService userService;

	public SignupController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping()
	public String signupView() {
		return SIGNUP_RESPONSE;
	}

	@PostMapping()
	public String signupUser(@ModelAttribute User user, Model model) {
		String signupError = null;
		
		if (!userService.isUserNameAvailable(user.getUsername())) {
			signupError = SIGNUP_ERROR_MESSAGE_USERNAME_ALREADY_EXISTS;
		} else {
			if (userService.createUser(user) > 0) {
				model.addAttribute(SIGNUP_SUCCESS_KEY, true);
			} else {
                signupError = SIGNUP_ERROR_MESSAGE;
			}
		}
		
		if (signupError != null) {
			model.addAttribute(SIGNUP_ERROR_KEY, signupError);
		}
		
		return SIGNUP_RESPONSE;
	}
}
