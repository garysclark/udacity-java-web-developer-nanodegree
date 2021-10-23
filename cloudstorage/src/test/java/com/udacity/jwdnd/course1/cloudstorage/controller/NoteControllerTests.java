package com.udacity.jwdnd.course1.cloudstorage.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteTests;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.UserTests;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

public class NoteControllerTests {

	@Mock
	private UserService userService;
	@Mock
	private NoteService noteService;
	@Mock
	private RedirectAttributes redirectAttributes;
	@Mock
	private Authentication authentication;
	@Captor
	private ArgumentCaptor<String> keyCaptor;
	@Captor
	private ArgumentCaptor<Object> valueCaptor;
	
	private NoteController noteController;
	private Note note;
	private User user;

	@BeforeEach
	public void beforeEach() {
		MockitoAnnotations.initMocks(this);
		noteController = new NoteController(userService, noteService);
		note = NoteTests.getTestNote_1();
		user = UserTests.getTestUser_1();
		Mockito.when(authentication.getName()).thenReturn(user.getUsername());
		Mockito.when(userService.getUser(user.getUsername())).thenReturn(user);
	}
	
	@Test
	public void canCreateNoteController() {
		assertNotNull(noteController);
	}
	
	@Test
	public void canCreateNote() {
		Mockito.when(noteService.findNote(note.getId())).thenReturn(null);
		Mockito.when(noteService.createNote(note)).thenReturn(1);
		
		String response = noteController.createNote(note, redirectAttributes, authentication);
		
		Mockito.verify(noteService).createNote(note);
		verifyWithResult("success", true, "successMessage", "You successfully added a note.", response);
	}
	
	@Test
	public void canHandleCreateNoteError() {
		Mockito.when(noteService.findNote(note.getId())).thenReturn(null);
		Mockito.when(noteService.createNote(note)).thenReturn(0);
		
		String response = noteController.createNote(note, redirectAttributes, authentication);
		
		Mockito.verify(noteService).createNote(note);
		verifyWithResult("error", true, "errorMessage", "There was an error adding the note.  Please try again.", response);
	}
	
	@Test
	public void canUpdateNote() {
		Mockito.when(noteService.findNote(note.getId())).thenReturn(note);
		Mockito.when(noteService.updateNote(note)).thenReturn(1);
		
		String response = noteController.createNote(note, redirectAttributes, authentication);
		
		Mockito.verify(noteService).updateNote(note);
		verifyWithResult("success", true, "successMessage", "You successfully updated a note.", response);
	}
	
	@Test
	public void canHandleUpdateNoteError() {
		Mockito.when(noteService.findNote(note.getId())).thenReturn(note);
		Mockito.when(noteService.updateNote(note)).thenReturn(0);
		
		String response = noteController.createNote(note, redirectAttributes, authentication);
		
		Mockito.verify(noteService).updateNote(note);
		verifyWithResult("error", true, "errorMessage", "There was an error updating the note.  Please try again.", response);
	}
	
	@Test
	public void canDeleteNote() {
		Mockito.when(noteService.deleteNote(note)).thenReturn(1);
		String response = noteController.deleteNote(note, redirectAttributes, authentication);
		
		Mockito.verify(noteService).deleteNote(note);
		verifyWithResult("success", true, "successMessage", "You successfully deleted a note.", response);
	}
	
	@Test
	public void canHandleDeleteNoteError() {
		Mockito.when(noteService.deleteNote(note)).thenReturn(0);
		String response = noteController.deleteNote(note, redirectAttributes, authentication);
		
		Mockito.verify(noteService).deleteNote(note);
		verifyWithResult("error", true, "errorMessage", "There was an error deleting the note.  Please try again.", response);
	}
	
	private void verifyWithResult(String resultKey, boolean resultValue, String messageKey, String messageValue, String response) {
		Mockito.verify(redirectAttributes, times(3)).addFlashAttribute(keyCaptor.capture(), valueCaptor.capture());
		List<String> keys = keyCaptor.getAllValues();
		List<Object> values = valueCaptor.getAllValues();

		assertEquals(resultKey, keys.get(0));
		assertEquals(resultValue, values.get(0));

		assertEquals(messageKey, keys.get(1));
		assertEquals(messageValue, values.get(1));

		assertEquals("activeTab", keys.get(2));
		assertEquals("notes", values.get(2));

		assertEquals("redirect:/result", response);
	}
}
