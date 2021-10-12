package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
@RequestMapping("/notes")
public class NoteController {
	
	private UserService userService;
	private NoteService noteService;

	public NoteController(UserService userService, NoteService noteService) {
		this.userService = userService;
		this.noteService = noteService;
	}

	@GetMapping()
	public String getNotes(Model model, Authentication authentication) {
		User user = userService.getUser(authentication.getName());
		model.addAttribute("notes", noteService.getNotes(user.getUserId()));
		return "home";
	}
	
	@PostMapping()
	public String createNote(Model model, @ModelAttribute Note note, Authentication authentication) {
		String errorMessage = null;
		
		User user = userService.getUser(authentication.getName());
		Integer userId = user.getUserId();
		note.setUserid(userId);
		int rowsAdded = noteService.createNote(note);
		if(rowsAdded < 0) {
			errorMessage = "There was an error adding the note.  Please try again.";
		}
		if(errorMessage == null) {
			model.addAttribute("ifSuccess", true);
			model.addAttribute("successMessage", "You successfully added a note");
		} else {
			model.addAttribute("ifError", true);
			model.addAttribute("errorMessage", errorMessage);
		}
		model.addAttribute("notes", noteService.getNotes(user.getUserId()));
		
		return "home";
	}

}
