package com.uniovi.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.uniovi.entities.Publication;
import com.uniovi.entities.User;
import com.uniovi.repositories.PublicationRepository;

@Service
public class PublicationService {

	@Autowired
	private PublicationRepository publicationRepository;

	public void addPublication(String title, String text, MultipartFile image, User user) {
		String filename = null;
		try {
			if (!image.getOriginalFilename().equals("")) {
				filename = user.getId() + "_" + new Date().getTime() + ".png";
				Files.copy(image.getInputStream(), Paths.get("src/main/resources/static/fotossubidas/" + filename),
						StandardCopyOption.REPLACE_EXISTING);
			}
			publicationRepository.save(new Publication(title, text, filename, user));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public List<Publication> getMyPublications(Long id) {
		return publicationRepository.getPublicationsByCreator(id);
	}
}
