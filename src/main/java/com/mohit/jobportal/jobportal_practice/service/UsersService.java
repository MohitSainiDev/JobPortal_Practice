package com.mohit.jobportal.jobportal_practice.service;

import java.util.Date;

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
	private UsersRepository usersRepository;
	@Autowired
	private RecruiterProfileRepository recruiterProfileRepository;
	@Autowired
	private JobSeekerProfileRepository jobSeekerProfileRepository;

	public Object addNew(Users users) {
		users.setActive(true);
		users.setRegistrationDate(new Date(System.currentTimeMillis()));
		int userTypeId = users.getUserTypeId().getUserTypeId();
		Users savedUser = usersRepository.save(users);
		if (userTypeId == 1)
			recruiterProfileRepository.save(new RecruiterProfile(savedUser));
		else
			jobSeekerProfileRepository.save(new JobSeekerProfile(savedUser));

		return savedUser;
	}

	public Users getUserByEmail(String email) {
		return usersRepository.getUserByEmail(email);
	}
}
