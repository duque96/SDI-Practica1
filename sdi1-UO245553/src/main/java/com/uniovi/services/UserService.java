package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

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
		logger.debug("Info: Se obtiene una lista de todos los usuarios de la base de datos");
		return users;
	}

	public User getUser(Long id) {
		logger.debug("Info: Se obtiene el usuario con id " + id);
		return userRepository.findOne(id);
	}

	public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		logger.debug("Info: Se almacena en la base de datos el usuario con id " + user.getId());
	}

	public void updateUser(User user) {
		userRepository.save(user);
		logger.debug("Info: Se actualiza la información en la base de datos del usuario con id " + user.getId());
	}

	public void deleteUser(Long id) {
		userRepository.delete(id);
		logger.debug("Info: Se elimina de la base de datos el usuario con id " + id);
	}

	public User getUserByEmail(String email) {
		logger.debug("Info: Se obtiene de la base de datos el usuario con email " + email);
		return userRepository.findByEmail(email);
	}

	public Page<User> getUsersWithoutId(Long id, Pageable pageable) {
		Page<User> list = userRepository.getUsersWithoutId(id, pageable);

		for (User user : list.getContent()) {
			user.setStatus(relationshipRepository.getStatus(new RelationshipKey(id, user.getId())));
		}

		logger.debug("Info: Se obtiene una lista de usuarios sin el usuario activo que tiene el id " + id);

		return list;
	}

	public Page<User> searchUsersByEmailAndName(Pageable pageable, String searchText, Long id) {
		Page<User> list = userRepository.searchUsersByEmailAndName(pageable, searchText, id);

		for (User user : list.getContent()) {
			user.setStatus(relationshipRepository.getStatus(new RelationshipKey(id, user.getId())));
		}

		logger.debug("Info: Se obtiene una lista de usuarios que coinciden parcialmente en nombre o "
				+ "email con el siguiente texto " + searchText);

		return list;
	}
}
