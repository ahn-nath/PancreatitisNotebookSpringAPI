package com.mission.cure.api.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mission.cure.api.entity.Note;
import com.mission.cure.api.service.NoteService;

//@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping({ "/notes" })
public class NotesController {

	@Autowired
	private NoteService notesService;

	static Logger logger = LoggerFactory.getLogger(UserController.class);

	/**
	 * 
	 * Get single note by id.
	 * 
	 * @return ResponseEntity<Note>
	 * @param String id of the note to get.
	 * 
	 **/
	@GetMapping({ "/get/{id}" })
	public ResponseEntity<Note> getNote(@PathVariable String id) throws IOException {

		Note note = notesService.getNote(Long.valueOf(id));

		return new ResponseEntity<Note>(note, HttpStatus.OK);

	}

	/**
	 * 
	 * Get all notes from the database and save them to a list.
	 * 
	 * @return ResponseEntity<List<Note>> list of notes with HTTP response
	 * 
	 **/
	@GetMapping({ "/get" })
	public ResponseEntity<List<Note>> getAllNotes() throws IOException {

		List<Note> notes = notesService.getNotes();

		return new ResponseEntity<List<Note>>(notes, HttpStatus.OK);

	}

	/**
	 * 
	 * Get all notes from the database by date and save them to a list.
	 * 
	 * @return ResponseEntity<List<Note>> list of notes with HTTP response
	 * 
	 **/
	@GetMapping({ "/get/orderBydate/{userId}" })
	public ResponseEntity<List<Note>> getAllNotesByDateOrder(@PathVariable String userId) throws IOException {

		List<Note> notes = notesService.getNotesByDateOrder(Long.valueOf(userId));

		return new ResponseEntity<List<Note>>(notes, HttpStatus.OK);

	}

	/**
	 * 
	 * Save single note.
	 * 
	 * @param RequestBody Note
	 * 
	 **/
	@GetMapping({ "/save" })
	public ResponseEntity<String> saveNote(@RequestBody Note noteData) throws IOException {

		// for testing
		ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
		logger.info(String.format("Note we are receiving %s", writer.writeValueAsString((Object) noteData)));

		// save to database
		notesService.saveNote(noteData);

		return new ResponseEntity<String>("Note successfully created", HttpStatus.OK);

	}
}
