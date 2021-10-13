package com.udacity.jwdnd.course1.cloudstorage.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteTests;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.UserTests;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
public class NoteServiceTests {

	private static final String TEST_EDITED_NOTE = " - EDITED";

	@Autowired
	private NoteService noteService;
	
	@Autowired
	private UserService userService;

	private User newUser;

	private Note newNote;

	private Integer rowsAdded;

	@BeforeEach
	public void beforeEach() {
		User testUser = UserTests.getTestUser_1();
		assertTrue(userService.createUser(testUser) > 0);
		newUser = userService.getUser(testUser.getUsername());
		newNote = NoteTests.getTestNote_1();
		newNote.setUserid(newUser.getUserId());
		rowsAdded = noteService.createNote(newNote);
	}
	
	@Test
	public void canAccessService() {
		assertNotNull(noteService);
	}
	
	@Test
	public void canCreateNote() {
		assertTrue(rowsAdded > 0);
	}
	
	@Test
	public void canGetNotes() {
		List<Note> notes = noteService.getNotes(newUser.getUserId());
		assertEquals(1, notes.size());
	}
	
	@Test
	public void canFindNoteById() {
		List<Note> notes = noteService.getNotes(newUser.getUserId());
		Note noteToBeFound = notes.get(0);
		Note noteFoundById = noteService.findNote(noteToBeFound.getNoteid());
		assertEquals(noteToBeFound, noteFoundById);
	}
	
	@Test
	public void canUpdateNote() {
		List<Note> notes = noteService.getNotes(newUser.getUserId());
		Note noteToEdit = notes.get(0);

		String editedTitle = noteToEdit.getNotetitle() + TEST_EDITED_NOTE;
		String editedDescription = noteToEdit.getNotedescription() + TEST_EDITED_NOTE;
		noteToEdit.setNotetitle(editedTitle);
		noteToEdit.setNotedescription(editedDescription);

		noteService.updateNote(noteToEdit);
		Note updatedNote = noteService.findNote(noteToEdit.getNoteid());
		
		assertEquals(noteToEdit, updatedNote);
	}
}
