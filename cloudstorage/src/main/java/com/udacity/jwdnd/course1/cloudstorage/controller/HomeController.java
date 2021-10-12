package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;


@Controller
@RequestMapping("/home")
public class HomeController {
	
	private UserService userService;
	private NoteService noteService;

	public HomeController(UserService userService, NoteService noteService) {
		this.userService = userService;
		this.noteService = noteService;
	}

	@GetMapping()
	public String getNotes(Model model, Authentication authentication) {
		User user = userService.getUser(authentication.getName());
		model.addAttribute("notes", noteService.getNotes(user.getUserId()));
		return "home";
	}

}
