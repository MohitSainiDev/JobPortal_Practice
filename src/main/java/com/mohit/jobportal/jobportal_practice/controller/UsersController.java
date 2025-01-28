package com.mohit.jobportal.jobportal_practice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.mohit.jobportal.jobportal_practice.entity.Users;
import com.mohit.jobportal.jobportal_practice.entity.UsersType;
import com.mohit.jobportal.jobportal_practice.service.UsersService;
import com.mohit.jobportal.jobportal_practice.service.UsersTypeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class UsersController {

	@Autowired
	private UsersTypeService usersTypeService;


	@Autowired
	private UsersService usersService;

	@GetMapping("/register")
	public String register(Model model) {
		List<UsersType> usersType = usersTypeService.getAll();
		model.addAttribute("getAllTypes", usersType);
		model.addAttribute("user", new Users());
		return "register";
	}

	@PostMapping("/register/new")
	public String userRegistration(@Valid Users users,Model model) {
		
		Users extractedUser=usersService.getUserByEmail(users.getEmail());
		if(extractedUser!=null)
		{
			model.addAttribute("error", "Email is already present");
			List<UsersType> usersType = usersTypeService.getAll();
			model.addAttribute("getAllTypes", usersType);
			model.addAttribute("user", new Users());
			return "register";
		}

		usersService.addNew(users);
		return "redirect:/dashboard/";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null) {
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}

		return "redirect:/";
	}

}
