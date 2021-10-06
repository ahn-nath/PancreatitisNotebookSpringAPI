package com.mission.cure.api.repository;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.mission.cure.api.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Specifies methods used to obtain and modify user related information which is
 * stored in the database.
 */
@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByName(String username);

	User findByEmail(String email);
}
