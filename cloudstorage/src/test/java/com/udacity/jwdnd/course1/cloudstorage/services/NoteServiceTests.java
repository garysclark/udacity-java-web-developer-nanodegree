package com.udacity.jwdnd.course1.cloudstorage.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteTests;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.UserTests;

public class NoteServiceTests {

	private static final List<Note> TEST_NOTES = new ArrayList<Note>();
	
	@Mock
	private NoteMapper noteMapper;
	@Captor
	private ArgumentCaptor<Note> noteCaptor;
	private NoteService noteService;

	private Note note;

	private User user;
	
	@BeforeEach
	public void beforeEach() {
		MockitoAnnotations.initMocks(this);
		noteService = new NoteService(noteMapper);
		note = NoteTests.getTestNote_1();
		user = UserTests.getTestUser_1();
	}
	
	@Test
	public void canCreateNote() {
		Mockito.when(noteMapper.create(Mockito.any(Note.class))).thenReturn(1);

		int rowCount = noteService.createNote(note);

		assertEquals(1, rowCount);
		Mockito.verify(noteMapper).create(noteCaptor.capture());
		Note createdNote = noteCaptor.getValue();
		assertNull(createdNote.getId());
		assertEquals(note.getTitle(), createdNote.getTitle());
		assertEquals(note.getDescription(), createdNote.getDescription());
		assertEquals(note.getUserid(), createdNote.getUserid());
		
	}
	
	@Test
	public void canHandleCreateError() {
		Mockito.when(noteMapper.create(Mockito.any(Note.class))).thenReturn(0);

		int rowCount = noteService.createNote(note);

		assertEquals(0, rowCount);
	}
	
	@Test
	public void canGetNotes() {
		Mockito.when(noteMapper.findByUserId(user.getUserId())).thenReturn(TEST_NOTES);

		List<Note> notes = noteService.getNotes(user.getUserId());

		assertEquals(TEST_NOTES, notes);
	}
	
	@Test
	public void canHandleInvalidUserIdGetNotes() {
		Mockito.when(noteMapper.findByUserId(user.getUserId())).thenReturn(null);

		List<Note> notes = noteService.getNotes(user.getUserId());

		assertNull(notes);
	}
	
	@Test
	public void canFindNoteById() {
		Mockito.when(noteMapper.findById(note.getId())).thenReturn(note);

		Note foundNote = noteService.findNote(note.getId());

		assertEquals(note, foundNote);
	}
	
	@Test
	public void canHandleInvalidIdFindNoteById() {
		Mockito.when(noteMapper.findById(note.getId())).thenReturn(null);

		Note foundNote = noteService.findNote(note.getId());

		assertNull(foundNote);
	}
	
	@Test
	public void canDeleteNote() {
		Mockito.when(noteMapper.delete(note)).thenReturn(1);

		int rowsAffected = noteService.deleteNote(note);
		
		assertEquals(1, rowsAffected);
	}
	
	@Test
	public void canHandleDeleteNoteError() {
		Mockito.when(noteMapper.delete(note)).thenReturn(0);

		int rowsAffected = noteService.deleteNote(note);
		
		assertEquals(0, rowsAffected);
	}
	
	@Test
	public void canUpdateNote() {
		Note note2 = NoteTests.getTestNote_2();
		Mockito.when(noteMapper.update(note.getId(), note2.getTitle(), note2.getDescription(), note2.getUserid())).thenReturn(1);
		
		note.setDescription(note2.getDescription());
		note.setTitle(note2.getTitle());
		note.setUserid(note2.getUserid());
		
		int rowsAffected = noteService.updateNote(note);
		
		assertEquals(1, rowsAffected);
	}
	
	@Test
	public void canHandleUpdateNoteError() {
		Note note2 = NoteTests.getTestNote_2();
		Mockito.when(noteMapper.update(note.getId(), note2.getTitle(), note2.getDescription(), note2.getUserid())).thenReturn(0);
		
		note.setDescription(note2.getDescription());
		note.setTitle(note2.getTitle());
		note.setUserid(note2.getUserid());
		
		int rowsAffected = noteService.updateNote(note);
		
		assertEquals(0, rowsAffected);
	}
}
