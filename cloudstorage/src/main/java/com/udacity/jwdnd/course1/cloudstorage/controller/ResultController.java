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
@RequestMapping("/result")
public class ResultController {

	@GetMapping()
	public String getContent(Model model, RedirectAttributes redirectAttributes, Authentication authentication) {
		return "result";
	}

	@PostMapping
	public String handleResultConfirmation(Model model, @ModelAttribute String activeTab, Authentication authentication) {
		return "redirect:/home";
	}
}
