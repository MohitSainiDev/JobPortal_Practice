package com.mohit.jobportal.jobportal_practice.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mohit.jobportal.jobportal_practice.entity.Users;
import com.mohit.jobportal.jobportal_practice.repository.UsersRepository;

@Service
public class UsersService {

	@Autowired
	UsersRepository userRepository;

	public Users addNew(Users users) {
		users.setActive(true);
		users.setRegistrationDate(new Date(System.currentTimeMillis()));
		return userRepository.save(users);
	}

	public Optional<Users> getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
}
