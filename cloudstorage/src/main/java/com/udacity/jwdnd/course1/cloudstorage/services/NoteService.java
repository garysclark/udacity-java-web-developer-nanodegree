package com.udacity.jwdnd.course1.cloudstorage.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;

@Service
public class NoteService {

	private NoteMapper noteMapper;

	public NoteService(NoteMapper noteMapper) {
		this.noteMapper = noteMapper;
	}

	public Integer createNote(Note note) {
		return noteMapper.create(new Note(null, note.getTitle(), note.getDescription(), note.getUserid()));
	}

	public List<Note> getNotes(Integer userId) {
		return noteMapper.findByUserId(userId);
	}

	public Integer deleteNote(Note note) {
		return noteMapper.delete(note);
	}

	public Note findNote(Integer noteid) {
		return noteMapper.findById(noteid);
	}

	public Integer updateNote(Note note) {
		return noteMapper.update(note.getId(), note.getTitle(), note.getDescription(), note.getUserid());
	}
	
}
