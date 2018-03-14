package com.uniovi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RelationshipController {
	@RequestMapping("/addFriend/{id}")
	public String addFriend(@PathVariable Long id) {
		return "redirect:/users/list";
	}
}
