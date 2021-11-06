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

	public static final String DELETE_NOTES = "notes/delete";
	public static final String NOTES_ENDPOINT = "/notes";
	public static final String ADD_NOTE_SUCCESS_MESSAGE = "You successfully added a note.";
	public static final String ADD_NOTE_ERROR_MESSAGE = "There was an error adding the note.  Please try again.";
	public static final String UPDATE_NOTE_SUCCESS_MESSAGE = "You successfully updated a note.";
	public static final String UPDATE_NOTE_ERROR_MESSAGE = "There was an error updating the note.  Please try again.";
	public static final String DELETE_NOTE_SUCCESS_MESSAGE = "You successfully deleted a note.";
	public static final String DELETE_NOTE_ERROR_MESSAGE = "There was an error deleting the note.  Please try again.";
	public static final String NOTES_DATA_KEY = "notes";
	public static final String ACTIVE_TAB_NOTES = "notes";

	private UserService userService;
	private NoteService noteService;

	public NoteController(UserService userService, NoteService noteService) {
		this.userService = userService;
		this.noteService = noteService;
	}
	
	@PostMapping(NOTES_ENDPOINT)
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
		
		return ResultController.REDIRECT_RESULT_RESPONSE;
	}
	
	@PostMapping(DELETE_NOTES)
	public String deleteNote(@ModelAttribute Note note, RedirectAttributes redirectAttributes, Authentication authentication) {
		int rowsDeleted = noteService.deleteNote(note);
		
		if(rowsDeleted > 0) {
			setupResult(true, DELETE_NOTE_SUCCESS_MESSAGE, redirectAttributes);
		} else {
			setupResult(false, DELETE_NOTE_ERROR_MESSAGE, redirectAttributes);
		}
		
		return ResultController.REDIRECT_RESULT_RESPONSE;
	}
	
	private void setupResult(boolean isSuccess, String message, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute(CloudStorageController.SUCCESS_KEY, isSuccess);
		redirectAttributes.addFlashAttribute(CloudStorageController.MESSAGE_KEY, message);
		redirectAttributes.addFlashAttribute(CloudStorageController.ACTIVE_TAB_KEY, ACTIVE_TAB_NOTES);
	}

}
