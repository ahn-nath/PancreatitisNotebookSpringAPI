package com.mission.cure.api.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mission.cure.api.entity.User;
import com.mission.cure.api.repository.UserRepository;
import java.security.SecureRandom;
import java.util.Base64;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	HashService hashService;

	/**
	 * 
	 * Saves User object to database.
	 * 
	 * @param User object to save
	 * @return User persisted User
	 * 
	 **/
	public User saveUser(User user) {
		// hash new password
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		String encodedSalt = Base64.getEncoder().encodeToString(salt);
		String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);

		// update object
		user.setPassword(hashedPassword);
		user.setSalt(encodedSalt);

		return userRepository.save(user);
	}

	/**
	 * 
	 * Get single User from database by its id.
	 * 
	 * @param id to search for
	 * @User retrieved User
	 * 
	 **/
	public User getUser(long id) {
		Optional<User> opt = userRepository.findById(id);
		User user = opt.orElse(null);

		return user;
	}

	/**
	 * 
	 * Get single User from database by its username.
	 * 
	 * @param username to search for
	 * @User retrieved User
	 * 
	 **/
	public User getUserByName(String username) {

		return userRepository.findByName(username);
	}

	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	/**
	 * 
	 * Get all Users from database.
	 * 
	 * @return List<Users> list with retrieved Users
	 **/
	public List<User> getUsers() {
		return userRepository.findAll();
	}

}
