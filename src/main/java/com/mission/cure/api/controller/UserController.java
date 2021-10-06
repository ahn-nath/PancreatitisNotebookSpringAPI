package com.mission.cure.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mission.cure.api.entity.User;
import com.mission.cure.api.service.AuthenticationService;
import com.mission.cure.api.service.UserService;

import org.springframework.http.ResponseEntity;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping({ "/users" })
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationService authService;

	static Logger logger = LoggerFactory.getLogger(UserController.class);

	/**
	 * GET request used to return test User entity. Used for demo purposes only.
	 * 
	 * @throws JsonProcessingException
	 * @returns ResponseEntity HTTP response
	 * 
	 **/
	@GetMapping({ "/data" })
	public ResponseEntity<String> getTestUser() throws JsonProcessingException {
		logger.info("Receiving a request");
		User user = new User();
		user.setName("Joe");
		user.setType(0);

		ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String userData = writer.writeValueAsString((Object) user);
		logger.info(String.format("Model we are sending %s", userData));
		return new ResponseEntity<String>(userData, HttpStatus.OK);
	}

	/**
	 * 
	 * Save user to database.
	 * 
	 * @param RequestBody User object with data to save
	 * 
	 **/
	@PostMapping({ "/user" })
	public ResponseEntity<String> createUser(@RequestBody User userData) throws JsonProcessingException {

		// for testing
		ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
		logger.info(String.format("User we are receiving %s", writer.writeValueAsString((Object) userData)));

		// save to database
		userService.saveUser(userData);

		return new ResponseEntity<String>("User successfully created", HttpStatus.OK);
	}

	/**
	 * 
	 * Validate user data by comparing input with stored values in database
	 * 
	 * @param @RequestBody User with values to compare.
	 * 
	 **/
	@PostMapping({ "/auth" })
	public ResponseEntity<String> authenticateUser(@RequestBody User userData) throws JsonProcessingException {

		boolean wasAuthenticated = authService.authenticate(userData);
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		String message = "Incorrect user details. Check the password and email and try again";

		if (wasAuthenticated) {
			status = HttpStatus.OK;
			message = "The user was authenticated";
		}

		return new ResponseEntity<String>(message, status);
	}

}
