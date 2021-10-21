package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
public class NoteController {
	
	private UserService userService;
	private NoteService noteService;

	public NoteController(UserService userService, NoteService noteService) {
		this.userService = userService;
		this.noteService = noteService;
	}
	
	@PostMapping("/notes")
	public String createNote(@ModelAttribute Note note, RedirectAttributes redirectAttributes, Authentication authentication) {
		String errorMessage = null;
		String successMessage = null;
		User user = userService.getUser(authentication.getName());
		
		if(noteService.findNote(note.getId()) == null)
		{
			Integer userId = user.getUserId();
			note.setUserid(userId);
			int rowsAdded = noteService.createNote(note);
			if(rowsAdded < 0) {
				errorMessage = "There was an error adding the note.  Please try again.";
			} else {
				successMessage = "You successfully added a note";
			}
		} else {
			noteService.updateNote(note);
			successMessage = "You successfully updated a note";
		}
		if(errorMessage == null) {
			redirectAttributes.addFlashAttribute("success", true);
			redirectAttributes.addFlashAttribute("successMessage", successMessage);
		} else {
			redirectAttributes.addFlashAttribute("error", true);
			redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
		}
		redirectAttributes.addFlashAttribute("notes", noteService.getNotes(user.getUserId()));
		redirectAttributes.addFlashAttribute("activeTab", "notes");
		
		return "redirect:/result";
	}
	
	@PostMapping("notes/delete")
	public String deleteNote(@ModelAttribute Note note, RedirectAttributes redirectAttributes, Authentication authentication) {
		User user = userService.getUser(authentication.getName());
		noteService.deleteNote(note.getId());
		String successMessage = "You successfully deleted a note";
		redirectAttributes.addFlashAttribute("success", true);
		redirectAttributes.addFlashAttribute("successMessage", successMessage);
		redirectAttributes.addFlashAttribute("notes", noteService.getNotes(user.getUserId()));
		redirectAttributes.addFlashAttribute("activeTab", "notes");
		
		return "redirect:/result";
	}

}
