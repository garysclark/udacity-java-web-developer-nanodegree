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
}
