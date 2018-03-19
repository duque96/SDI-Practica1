package com.uniovi.controllers;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uniovi.entities.Relationship;
import com.uniovi.entities.User;
import com.uniovi.services.RelationshipService;
import com.uniovi.services.UserService;

@Controller
public class RelationshipController {

	@Autowired
	private UserService userService;

	@Autowired
	private RelationshipService relationshipService;

	@RequestMapping("/addFriend/{id}")
	public String addFriend(@PathVariable Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = userService.getUserByEmail(email);

		User recipientUser = userService.getUser(id);

		relationshipService.addRelationship(new Relationship(activeUser, recipientUser, "REQUEST"));
		userService.updateUser(recipientUser);

		return "redirect:/users/list";
	}

	@RequestMapping("/friendRequests")
	public String friendRequest(Model model, Pageable pageable) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = userService.getUserByEmail(email);

		Page<User> requests = new PageImpl<User>(new LinkedList<User>());

		requests = relationshipService.getRequests(pageable, activeUser);

		model.addAttribute("requestList", requests.getContent());
		model.addAttribute("page", requests);
		return "friendRequest/friendRequests";
	}

	@RequestMapping("/friendRequests/accept/{id}")
	public String friendRequest(@PathVariable Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = userService.getUserByEmail(email);

		User user = userService.getUser(id);

		relationshipService.acceptFriend(activeUser, user);
		return "redirect:/friendRequests";
	}

	@RequestMapping("/friendsList")
	public String friendsList(Model model, Pageable pageable) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = userService.getUserByEmail(email);

		Page<User> list = new PageImpl<User>(new LinkedList<User>());

		list = relationshipService.getFriends(pageable, activeUser.getId());

		model.addAttribute("friendsList", list.getContent());
		model.addAttribute("page", list);
		return "friend/friendsList";
	}
}
