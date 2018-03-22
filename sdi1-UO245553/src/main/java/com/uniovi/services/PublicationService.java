package com.uniovi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Publication;
import com.uniovi.entities.User;
import com.uniovi.repositories.PublicationRepository;

@Service
public class PublicationService {

	@Autowired
	private PublicationRepository publicationRepository;

	public void addPublication(String title, String text, User user) {
		publicationRepository.save(new Publication(title, text, user));
	}

	public List<Publication> getMyPublications(Long id) {
		return publicationRepository.getPublicationsByCreator(id);
	}
}
