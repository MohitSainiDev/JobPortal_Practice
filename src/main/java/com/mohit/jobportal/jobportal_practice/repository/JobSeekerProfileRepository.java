package com.mohit.jobportal.jobportal_practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohit.jobportal.jobportal_practice.entity.JobSeekerProfile;

public interface JobSeekerProfileRepository extends JpaRepository<JobSeekerProfile, Integer> {

}
