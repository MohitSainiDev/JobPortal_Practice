package com.mohit.jobportal.jobportal_practice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mohit.jobportal.jobportal_practice.entity.RecruiterProfile;
import com.mohit.jobportal.jobportal_practice.repository.RecruiterProfileRepository;

@Service
public class RecruiterProfileService {

	@Autowired
	RecruiterProfileRepository recruiterProfileRepository;

	public Optional<RecruiterProfile> getOne(Integer id) {
		return recruiterProfileRepository.findById(id);
	}

	public RecruiterProfile addNew(RecruiterProfile recruiterProfile) {

		return recruiterProfileRepository.save(recruiterProfile);
	}
}
