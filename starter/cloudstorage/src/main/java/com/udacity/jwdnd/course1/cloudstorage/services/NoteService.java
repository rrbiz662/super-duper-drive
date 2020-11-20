package com.udacity.jwdnd.course1.cloudstorage.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;

@Service
public class NoteService {
	
	private NoteMapper noteMapper;
	
	public NoteService(NoteMapper noteMapper) {
		this.noteMapper = noteMapper;
	}
	
	public int insertNote(Note note) {
		return noteMapper.insertNote(note);
	}
	
	public ArrayList<Note> getNotes(int userId){
		return noteMapper.getNotes(userId);
	}
	
	public boolean updateNote(Note note) {
		return noteMapper.updateNote(note);		
	}
	
	public boolean deleteNote(int noteId) {
		return noteMapper.deleteNote(noteId);	
	}
}
