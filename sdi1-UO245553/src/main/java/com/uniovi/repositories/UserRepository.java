package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByEmail(String email);
	
	@Query("SELECT u FROM User u WHERE (u.id <> ?1)")
	Page<User> getUsersWithoutId(Long id, Pageable pageable);
}
