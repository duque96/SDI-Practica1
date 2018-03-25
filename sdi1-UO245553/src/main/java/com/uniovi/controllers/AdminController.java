package com.uniovi.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.User;
import com.uniovi.services.AdminService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UserService;

@Controller
public class AdminController {
	@Autowired
	private AdminService adminService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserService userService;

	@Autowired
	private HttpSession httpSession;

	@RequestMapping("/admin/login")
	public String getAdminLogin(Model model) {
		model.addAttribute("error", httpSession.getAttribute("error"));
		return "admin/login";
	}

	@RequestMapping(value = "/admin/login", method = RequestMethod.POST)
	public String postAdminLogin(Model model, @RequestParam("username") String username,
			@RequestParam("password") String password) {
		User user = adminService.getAdminUser(username, password);
		String error = adminService.checkError(user);
		if (error == null) {
			httpSession.setAttribute("error", error);
			securityService.autoLogin(username, password);
			return "redirect:/admin/list";
		} else {
			httpSession.setAttribute("error", error);
			return "redirect:/admin/login/";
		}
	}

	@RequestMapping("/admin/list")
	public String getUserList(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = userService.getUserByEmail(email);

		List<User> users = userService.getUsersWithoutId(activeUser.getId());

		model.addAttribute("usersList", users);
		return "admin/list";
	}

	@RequestMapping("/admin/delete/{id}")
	public String getDeleteUser(Model model, @PathVariable Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = userService.getUserByEmail(email);

		if (activeUser.getRole().equals("ROLE_ADMIN")) {
			adminService.deleteUser(id);
			return "redirect:/admin/list";
		} else
			return "redirect:/";
	}
}
