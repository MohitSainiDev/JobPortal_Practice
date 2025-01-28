package com.mohit.jobportal.jobportal_practice.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	@Autowired
	private PasswordEncoder passwordEncoder;

	public Object addNew(Users users) {
		users.setActive(true);
		users.setRegistrationDate(new Date(System.currentTimeMillis()));
		users.setPassword(passwordEncoder.encode(users.getPassword()));
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

	public Object getCurrentUserProfile() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String username = authentication.getName();
			Users users = usersRepository.findByEmail(username)
					.orElseThrow(() -> new UsernameNotFoundException("Could not found " + "user"));
			int userId = users.getUserId();
			if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
				RecruiterProfile recruiterProfile = recruiterProfileRepository.findById(userId)
						.orElse(new RecruiterProfile());
				return recruiterProfile;
			} else {
				JobSeekerProfile jobSeekerProfile = jobSeekerProfileRepository.findById(userId)
						.orElse(new JobSeekerProfile());
				return jobSeekerProfile;
			}
		}

		return null;
	}

	public Users getCurrentUser() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String username = authentication.getName();
			Users user = usersRepository.findByEmail(username)
					.orElseThrow(() -> new UsernameNotFoundException("Could not found " + "user"));
			return user;
		}

		return null;
	}
}
