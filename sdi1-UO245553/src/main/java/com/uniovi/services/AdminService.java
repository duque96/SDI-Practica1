package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;
import com.uniovi.repositories.PublicationRepository;
import com.uniovi.repositories.RelationshipRepository;
import com.uniovi.repositories.UserRepository;

@Service
public class AdminService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RelationshipRepository relationshipRepository;

	@Autowired
	private PublicationRepository publicationRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public User getAdminUser(String username, String password) {
		User user = userRepository.findUserByUsernameAndPassword(username);

		if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
			return user;
		}

		return null;
	}

	public String checkError(User user) {
		if (user == null)
			return "errorNull";
		else if (!user.getRole().equals("ROLE_ADMIN"))
			return "errorNoAdmin";
		else
			return null;
	}

	public void deleteUser(Long id) {
		publicationRepository.deletePublication(id);
		relationshipRepository.deleteRelationshipSender(id);
		relationshipRepository.deleteRelationshipRecipient(id);
		userRepository.deleteUser(id);
	}
}
