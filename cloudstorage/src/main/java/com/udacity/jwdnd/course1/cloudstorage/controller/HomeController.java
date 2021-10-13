package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
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
		String activeTab = (String) model.getAttribute("activeTab");
		if(activeTab == null) {
			model.addAttribute("activeTab", "files");
		}
		
		if(model.getAttribute("notes") == null) {
			User user = userService.getUser(authentication.getName());
			List<Note> notes = noteService.getNotes(user.getUserId());
			model.addAttribute("notes", notes);
		}
		return "home";
	}

}
