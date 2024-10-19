package com.mohit.jobportal.jobportal_practice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.mohit.jobportal.jobportal_practice.entity.Users;
import com.mohit.jobportal.jobportal_practice.entity.UsersType;
import com.mohit.jobportal.jobportal_practice.service.UsersService;
import com.mohit.jobportal.jobportal_practice.service.UsersTypeService;

import jakarta.validation.Valid;

@Controller
public class UsersController {
	@Autowired
	private UsersTypeService usersTypeService;
	@Autowired
	private UsersService usersService;

	@GetMapping("/register")
	public String register(Model model) {
		List<UsersType> usersTypes = usersTypeService.getAll();
		model.addAttribute("getAllTypes", usersTypes);
		model.addAttribute("user", new Users());
		return "register";
	}

	@PostMapping("/register/new")
	public String userRegistration(@Valid Users users, Model model) {

		Optional<Users> optionallUsers = usersService.getUserByEmail(users.getEmail());

		if (optionallUsers.isPresent()) {
			model.addAttribute("error", "Email Already Exists,try other email");
			List<UsersType> usersTypes = usersTypeService.getAll();
			model.addAttribute("getAllTypes", usersTypes);
			model.addAttribute("user", new Users());
			return "register";
		}
		usersService.addNew(users);
		return "dashboard";
	}

}
