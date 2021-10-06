package com.mission.cure.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import com.mission.cure.api.entity.User;


@Service
public class AuthenticationService {
	
	@Autowired
	UserService userService;
	
	@Autowired
	HashService hashService;

	public boolean authenticate(User auth) throws AuthenticationException {
		String email = auth.getEmail();
		String password = auth.getPassword();

		// get user object and check if input password equals password in database
		User user = userService.getUserByEmail(email);
		if (user != null) {
			String encodedSalt = user.getSalt();
			String hashedPassword = hashService.getHashedValue(password, encodedSalt);

			// if password is correct, return UsernamePasswordAuthenticationToken object
			if (user.getPassword().equals(hashedPassword)) {
				return true;
			}
		}

		return false;
	}
}