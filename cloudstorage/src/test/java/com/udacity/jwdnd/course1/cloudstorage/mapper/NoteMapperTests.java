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

	private User testUser1;

	private User createdUser1;

	private Note firstNote;

	private User testUser2;

	private User createdUser2;

	private Note secondNote;

	private Integer firstRowNum;

	private Integer secondRowNum;

	@BeforeEach
	public void beforeEach() {
		testUser1 = UserTests.getTestUser_1();
		userMapper.create(testUser1);
		createdUser1 = userMapper.findByUsername(testUser1.getUsername());

		testUser2 = UserTests.getTestUser_2();
		userMapper.create(testUser2);
		createdUser2 = userMapper.findByUsername(testUser2.getUsername());

		firstNote = NoteTests.getTestNote_1();
		firstNote.setUserid(createdUser1.getUserId());
		firstRowNum = noteMapper.create(firstNote);

		secondNote = NoteTests.getTestNote_2();
		secondNote.setUserid(createdUser2.getUserId());
		secondRowNum = noteMapper.create(secondNote);
	}
	
	@Test
	public void canAccessMapper() {
		assertNotNull(noteMapper);
		assertNotNull(userMapper);
	}
	
	@Test
	public void canCreateNote() {
		assertTrue(firstRowNum > 0);
		assertTrue(secondRowNum > 0);
	}
	
	@Test
	public void canFindNoteById() {
		Note foundNote = noteMapper.findById(firstNote.getNoteid());
		assertEquals(firstNote, foundNote);
	}
	
	@Test
	public void canFindByUserId() {
		noteMapper.update(secondNote.getNoteid(), secondNote.getNotetitle(), secondNote.getNotedescription(), createdUser1.getUserId());
		List<Note> notes = noteMapper.findByUserId(firstNote.getUserid());
		assertEquals(2, notes.size());
	}
	
	@Test
	public void canUpdateNote() {
		noteMapper.update(firstNote.getNoteid(), secondNote.getNotetitle(), secondNote.getNotedescription(), secondNote.getUserid());
		Note updatedNote = noteMapper.findById(firstNote.getNoteid());
		assertEquals(secondNote.getNotetitle(), updatedNote.getNotetitle());
		assertEquals(secondNote.getNotedescription(), updatedNote.getNotedescription());
		assertEquals(secondNote.getUserid(), updatedNote.getUserid());
	}
	
	@Test
	public void canDeleteNote() {
		noteMapper.delete(secondNote.getNoteid());
		assertNull(noteMapper.findById(secondNote.getNoteid()));
	}
}
