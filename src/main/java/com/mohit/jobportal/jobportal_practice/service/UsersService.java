package com.mohit.jobportal.jobportal_practice.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mohit.jobportal.jobportal_practice.entity.Users;
import com.mohit.jobportal.jobportal_practice.repository.UsersRepository;

@Service
public class UsersService {
	@Autowired
	private UsersRepository usersRepository;

	public Object addNew(Users users) {
		users.setActive(true);
		users.setRegistrationDate(new Date(System.currentTimeMillis()));
		return usersRepository.save(users);
	}
}
