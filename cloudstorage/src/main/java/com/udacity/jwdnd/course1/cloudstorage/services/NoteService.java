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
		return noteMapper.create(note);
	}

	public List<Note> getNotes(Integer userId) {
		return noteMapper.findByUserId(userId);
	}

	public void deleteNote(Integer noteid) {
		noteMapper.delete(noteid);
	}

	public Note findNote(Integer noteid) {
		return noteMapper.findById(noteid);
	}

	public void updateNote(Note note) {
		noteMapper.update(note.getNoteid(), note.getNotetitle(), note.getNotedescription(), note.getUserid());
	}
	
}
