package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(ResultController.RESULT_ENDPOINT)
public class ResultController {

	public static final String REDIRECT_RESULT_RESPONSE = "redirect:/result";
	public static final String RESULT_ENDPOINT = "/result";
	public static final String RESULT_RESPONSE = "result";

	@GetMapping()
	public String getContent(Model model, RedirectAttributes redirectAttributes, Authentication authentication) {
		return RESULT_RESPONSE;
	}

	@PostMapping
	public String handleResultConfirmation(Model model, @ModelAttribute String activeTab, Authentication authentication) {
		return HomeController.REDIRECT_HOME_RESPONSE;
	}
}
