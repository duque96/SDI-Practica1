package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.RelationshipKey;
import com.uniovi.entities.User;
import com.uniovi.repositories.RelationshipRepository;
import com.uniovi.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private RelationshipRepository relationshipRepository;

	@PostConstruct
	public void init() {
	}

	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		userRepository.findAll().forEach(users::add);
		return users;
	}

	public User getUser(Long id) {
		return userRepository.findOne(id);
	}

	public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	public void updateUser(User user) {
		userRepository.save(user);
	}

	public void deleteUser(Long id) {
		userRepository.delete(id);
	}

	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public Page<User> getUsersWithoutId(Long id, Pageable pageable) {
		Page<User> list = userRepository.getUsersWithoutId(id, pageable);

		for (User user : list.getContent()) {
			user.setStatus(relationshipRepository.getStatus(new RelationshipKey(id, user.getId())));
		}

		return list;
	}

	public Page<User> searchUsersByEmailAndName(Pageable pageable, String searchText, Long id) {
		Page<User> list = userRepository.searchUsersByEmailAndName(pageable, searchText, id);

		for (User user : list.getContent()) {
			user.setStatus(relationshipRepository.getStatus(new RelationshipKey(id, user.getId())));
		}

		return list;
	}
}
