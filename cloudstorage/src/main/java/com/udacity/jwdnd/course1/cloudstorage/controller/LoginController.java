package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(LoginController.LOGIN_ENDPOINT)
public class LoginController {
	
	static final String LOGIN_ENDPOINT = "/login";
	public static final String LOGIN_RESPONSE = "login";

	@GetMapping()
	public String signupView() {
		return LOGIN_RESPONSE;
	}

}
