package com.udacity.jwdnd.course1.cloudstorage.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
public class NoteMapperTests {

	@Autowired
	private NoteMapper noteMapper;

	@Autowired
	private UserMapper userMapper;

	private User user;

	private Note note;

	private Integer rowsCreated;

	@BeforeEach
	public void beforeEach() {
		user = UserTests.getTestUser_1();
		userMapper.create(user);

		note = NoteTests.getTestNote_1();
		note.setUserid(user.getUserId());
		rowsCreated = noteMapper.create(note);
	}
	
	@Test
	public void canAccessMapper() {
		assertNotNull(noteMapper);
		assertNotNull(userMapper);
	}
	
	@Test
	public void canCreateNote() {
		assertTrue(rowsCreated > 0);
	}
	
	@Test
	public void canFindNoteById() {
		Note foundNote = noteMapper.findById(note.getId());
		assertEquals(note, foundNote);
	}
	
	@Test
	public void canFindByUserId() {
		Note secondNote = NoteTests.getTestNote_2();
		secondNote.setUserid(user.getUserId());
		noteMapper.create(secondNote);
		List<Note> notes = noteMapper.findByUserId(note.getUserid());
		assertEquals(2, notes.size());
	}
	
	@Test
	public void canUpdateNote() {
		Note secondNote = NoteTests.getTestNote_2();
		note.setDescription(secondNote.getDescription());
		note.setTitle(secondNote.getTitle());
		
		User user2 = UserTests.getTestUser_2();
		userMapper.create(user2);
		secondNote.setUserid(user2.getUserId());
		
		note.setUserid(secondNote.getUserid());
		
		noteMapper.update(note);
		
		Note updatedNote = noteMapper.findById(note.getId());
		assertEquals(note, updatedNote);
	}
	
	@Test
	public void canDeleteNote() {
		noteMapper.delete(note);
		assertNull(noteMapper.findById(note.getId()));
	}
	
	@Test
	public void canDetectUpdateError() {
		Note note2 = NoteTests.getTestNote_2();
		Integer rowsAffected = noteMapper.update(note2);
		assertEquals(0, rowsAffected);
	}
	
	@Test
	public void canDetectDeleteError() {
		Note note2 = NoteTests.getTestNote_2();
		Integer rowsAffected = noteMapper.delete(note2);
		assertEquals(0, rowsAffected);
	}
}
