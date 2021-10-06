package com.mission.cure.api.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mission.cure.api.entity.Note;
import com.mission.cure.api.repository.NoteRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NoteService {

	@Autowired
	NoteRepository noteRepository;

	/**
	 * 
	 * Saves note object to database.
	 * 
	 * @param Note object to save
	 * @return Note persisted article
	 * 
	 **/
	public Note saveNote(Note note) {

		return noteRepository.save(note);
	}

	/**
	 * 
	 * Get single note from database by its id.
	 * 
	 * @param id to search for
	 * @Article retrieved note
	 * 
	 **/
	public Note getNote(long id) {
		Optional<Note> opt = noteRepository.findById(id);
		Note note = opt.orElse(null);

		return note;
	}

	/**
	 * 
	 * Get all notes from database.
	 * 
	 * @return List<Note> list with retrieved articles
	 * 
	 **/
	public List<Note> getNotes() {
		return noteRepository.findAll();
	}

	/**
	 * 
	 * Get all notes from database by date.
	 * 
	 * @return List<Note> list with retrieved articles
	 * 
	 **/
	public List<Note> getNotesByDateOrder(Long userId) {

		return noteRepository.findByUserIdOrderByDateCreated(userId);
	}

}
