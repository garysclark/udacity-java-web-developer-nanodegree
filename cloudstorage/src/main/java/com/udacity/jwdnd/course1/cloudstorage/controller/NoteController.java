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
	
	static final String MAPPING_RESULT = "redirect:/result";
	static final String DELETE_NOTE_ERROR_MESSAGE = "There was an error deleting the note.  Please try again.";
	static final String DELETE_NOTE_SUCCESS_MESSAGE = "You successfully deleted a note.";
	static final String UPDATE_NOTE_ERROR_MESSAGE = "There was an error updating the note.  Please try again.";
	static final String UPDATE_NOTE_SUCCESS_MESSAGE = "You successfully updated a note.";
	static final String ADD_NOTE_ERROR_MESSAGE = "There was an error adding the note.  Please try again.";
	static final String ADD_NOTE_SUCCESS_MESSAGE = "You successfully added a note.";

	private UserService userService;
	private NoteService noteService;

	public NoteController(UserService userService, NoteService noteService) {
		this.userService = userService;
		this.noteService = noteService;
	}
	
	@PostMapping("/notes")
	public String createNote(@ModelAttribute Note note, RedirectAttributes redirectAttributes, Authentication authentication) {
		User user = userService.getUser(authentication.getName());
		
		if(noteService.findNote(note.getId()) == null)
		{
			Integer userId = user.getUserId();
			note.setUserid(userId);
			int rowsAdded = noteService.createNote(note);
			if(rowsAdded > 0) {
				setupResult(true, ADD_NOTE_SUCCESS_MESSAGE, redirectAttributes);
			} else {
				setupResult(false, ADD_NOTE_ERROR_MESSAGE, redirectAttributes);
			}
		} else {
			int rowsUpdated = noteService.updateNote(note);
			if(rowsUpdated > 0) {
				setupResult(true, UPDATE_NOTE_SUCCESS_MESSAGE, redirectAttributes);
			} else {
				setupResult(false, UPDATE_NOTE_ERROR_MESSAGE, redirectAttributes);
			}
		}
		
		return MAPPING_RESULT;
	}
	
	@PostMapping("notes/delete")
	public String deleteNote(@ModelAttribute Note note, RedirectAttributes redirectAttributes, Authentication authentication) {
		int rowsDeleted = noteService.deleteNote(note);
		
		if(rowsDeleted > 0) {
			setupResult(true, DELETE_NOTE_SUCCESS_MESSAGE, redirectAttributes);
		} else {
			setupResult(false, DELETE_NOTE_ERROR_MESSAGE, redirectAttributes);
		}
		
		return MAPPING_RESULT;
	}
	
	private void setupResult(boolean isSuccess, String message, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("success", isSuccess);
		redirectAttributes.addFlashAttribute("message", message);
		redirectAttributes.addFlashAttribute("activeTab", "notes");
	}

}
