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
	public static final String TEST_EDITED_NOTE_DESCRIPTION = "Edited Note Description";
	public static final String TEST_EDITED_NOTE_TITLE = "Edited Note Title";
	public static final String TEST_NOTE_DESCRIPTION = "Test note description.";
	public static final String TEST_NOTE_TITLE = "Test Note Title";

	@BeforeEach
	public void beforeEach() {
		note = getTestNote_1();
	}
	
	@Test
	public void canCreateNote() {
		assertNotNull(note);
		assertEquals(TEST_ID, note.getId());
		assertEquals(TEST_TITLE, note.getTitle());
		assertEquals(TEST_DESCRIPTION, note.getDescription());
		assertEquals(TEST_USERID, note.getUserid());
	}
	
	@Test
	public void canSetNoteAttributes() {
		note.setId(TEST_ID_2);
		assertEquals(TEST_ID_2, note.getId());
		note.setTitle(TEST_TITLE_2);
		assertEquals(TEST_TITLE_2, note.getTitle());
		note.setDescription(TEST_DESCRIPTION_2);
		assertEquals(TEST_DESCRIPTION_2, note.getDescription());
		note.setUserid(TEST_USERID_2);
		assertEquals(TEST_USERID_2, note.getUserid());
	}
	
	@Test
	public void canVerifyEquals() {
		Note note1 = getTestNote_1();
		Note note2 = getTestNote_1();
		assertEquals(note1, note2);
	}

	static public Note getTestNote_1() {
		return new Note(TEST_ID, TEST_TITLE, TEST_DESCRIPTION, TEST_USERID);
	}

	public static Note getTestNote_2() {
		return new Note(TEST_ID_2, TEST_TITLE_2, TEST_DESCRIPTION_2, TEST_USERID_2);
	}
}
