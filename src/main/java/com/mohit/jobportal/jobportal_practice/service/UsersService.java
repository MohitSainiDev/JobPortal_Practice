package com.mohit.jobportal.jobportal_practice.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mohit.jobportal.jobportal_practice.entity.JobSeekerProfile;
import com.mohit.jobportal.jobportal_practice.entity.RecruiterProfile;
import com.mohit.jobportal.jobportal_practice.entity.Users;
import com.mohit.jobportal.jobportal_practice.repository.JobSeekerProfileRepository;
import com.mohit.jobportal.jobportal_practice.repository.RecruiterProfileRepository;
import com.mohit.jobportal.jobportal_practice.repository.UsersRepository;

@Service
public class UsersService {

	@Autowired
	UsersRepository usersRepository;
	@Autowired
	JobSeekerProfileRepository jobSeekerProfileRepository;
	@Autowired
	RecruiterProfileRepository recruiterProfileRepository;

	public Users addNew(Users users) {
		users.setActive(true);
		users.setRegistrationDate(new Date(System.currentTimeMillis()));
		Users savedUser = usersRepository.save(users);
		int userTypeId = users.getUserTypeId().getUserTypeId();
		if (userTypeId == 1) {
			recruiterProfileRepository.save(new RecruiterProfile(users));
		} else
			jobSeekerProfileRepository.save(new JobSeekerProfile(users));
		return savedUser;
	}

	public Optional<Users> getUserByEmail(String email) {
		return usersRepository.findByEmail(email);
	}
}
