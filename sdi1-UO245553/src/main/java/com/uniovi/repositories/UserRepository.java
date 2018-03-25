package com.uniovi.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByEmail(String email);

	@Query("SELECT u FROM User u WHERE (u.id <> ?1)")
	Page<User> getUsersWithoutId(Long id, Pageable pageable);

	@Query("SELECT u FROM User u WHERE ((LOWER(u.email) LIKE LOWER(CONCAT('%', ?1, '%')) "
			+ "OR LOWER(u.name) LIKE LOWER(CONCAT('%', ?1, '%'))) AND (u.id <> ?2))")
	Page<User> searchUsersByEmailAndName(Pageable pageable, String searchText, Long id);

	@Query("SELECT u FROM User u WHERE (u.email=?1)")
	User findUserByUsernameAndPassword(String username);

	@Query("SELECT u FROM User u WHERE (u.id<>?1)")
	List<User> getUsersWithoutId(Long id);

	@Transactional
	@Modifying
	@Query("DELETE FROM User WHERE (id=?1)")
	void deleteUser(Long id);
}
