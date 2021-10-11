package com.udacity.jwdnd.course1.cloudstorage.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NoteTests {

	private static final Integer TEST_ID = 99;
	private static final String TEST_TITLE = "Note Title";
	private static final String TEST_DESCRIPTION = "Note Description";
	private static final Integer TEST_USERID = 10;
	private static final Integer TEST_ID_2 = 100;
	private static final String TEST_TITLE_2 = "Note Title 2";
	private static final String TEST_DESCRIPTION_2 = "Note Description 2";
	private static final Integer TEST_USERID_2 = 11;

	private Note note;

	@BeforeEach
	public void beforeEach() {
		note = getTestNote();
	}
	
	@Test
	public void canCreateNote() {
		assertNotNull(note);
		assertEquals(TEST_ID, note.getNoteid());
		assertEquals(TEST_TITLE, note.getNotetitle());
		assertEquals(TEST_DESCRIPTION, note.getNotedescription());
		assertEquals(TEST_USERID, note.getUserid());
	}
	
	@Test
	public void canSetNoteAttributes() {
		note.setNoteid(TEST_ID_2);
		assertEquals(TEST_ID_2, note.getNoteid());
		note.setNotetitle(TEST_TITLE_2);
		assertEquals(TEST_TITLE_2, note.getNotetitle());
		note.setNotedescription(TEST_DESCRIPTION_2);
		assertEquals(TEST_DESCRIPTION_2, note.getNotedescription());
		note.setUserid(TEST_USERID_2);
		assertEquals(TEST_USERID_2, note.getUserid());
	}
	
	@Test
	public void canVerifyEquals() {
		Note note1 = getTestNote();
		Note note2 = getTestNote();
		assertEquals(note1, note2);
	}

	static public Note getTestNote() {
		return new Note(TEST_ID, TEST_TITLE, TEST_DESCRIPTION, TEST_USERID);
	}
}
