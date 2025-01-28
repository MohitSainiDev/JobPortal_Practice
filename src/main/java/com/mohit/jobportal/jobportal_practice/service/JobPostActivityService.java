package com.mohit.jobportal.jobportal_practice.service;

import org.springframework.stereotype.Service;

import com.mohit.jobportal.jobportal_practice.entity.JobPostActivity;
import com.mohit.jobportal.jobportal_practice.repository.JobPostActivityRepository;

@Service
public class JobPostActivityService {

    private final JobPostActivityRepository jobPostActivityRepository;

    public JobPostActivityService(JobPostActivityRepository jobPostActivityRepository) {
        this.jobPostActivityRepository = jobPostActivityRepository;
    }

    public JobPostActivity addNew(JobPostActivity jobPostActivity) {
        return jobPostActivityRepository.save(jobPostActivity);
    }



}
