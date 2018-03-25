package com.uniovi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.uniovi.entities.Publication;
import com.uniovi.entities.User;
import com.uniovi.services.PublicationService;
import com.uniovi.services.RelationshipService;
import com.uniovi.services.UserService;

@Controller
public class PublicationController {

	@Autowired
	private UserService userService;

	@Autowired
	private PublicationService publicationService;

	@Autowired
	private RelationshipService relationshipService;

	@RequestMapping(value = "/publications/create", method = RequestMethod.POST)
	public String createPublication(@RequestParam("title") String title, @RequestParam("text") String text,
			@RequestParam("image") MultipartFile image) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = userService.getUserByEmail(email);

		publicationService.addPublication(title, text, image, activeUser);

		return "redirect:/publications/myPublications";
	}

	@RequestMapping("/publications/create")
	public String createPublication(Model model) {
		return "publications/create";
	}

	@RequestMapping("/publications/myPublications")
	private String myPublications(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = userService.getUserByEmail(email);

		List<Publication> publicationsList = publicationService.getMyPublications(activeUser.getId());
		model.addAttribute("publicationsList", publicationsList);

		return "publications/myPublications";
	}

	@RequestMapping("/publications/friendPublications/{id}")
	private String friendPublications(@PathVariable Long id, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = userService.getUserByEmail(email);

		boolean result = relationshipService.checkIfFriend(activeUser.getId(), id);
		if (result) {
			List<Publication> publicationsList = publicationService.getMyPublications(id);
			User user = userService.getUser(id);

			model.addAttribute("publicationsList", publicationsList);
			model.addAttribute("name", user.getName());
			return "publications/friendPublications";
		} else
			return "redirect:/";
	}
}
