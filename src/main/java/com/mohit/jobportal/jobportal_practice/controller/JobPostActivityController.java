package com.mohit.jobportal.jobportal_practice.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.mohit.jobportal.jobportal_practice.entity.JobPostActivity;
import com.mohit.jobportal.jobportal_practice.entity.Users;
import com.mohit.jobportal.jobportal_practice.service.JobPostActivityService;
import com.mohit.jobportal.jobportal_practice.service.UsersService;

@Controller
public class JobPostActivityController {
	@Autowired
	private UsersService usersService;
	@Autowired
	private JobPostActivityService jobPostActivityService;

	@GetMapping("/dashboard/")
	public String searchJobs(Model model) {

		Object currentUserProfile = usersService.getCurrentUserProfile();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            model.addAttribute("username", currentUsername);
        }
		model.addAttribute("user", currentUserProfile);
            
            
		return "dashboard";
	}

	@GetMapping("/dashboard/add")
	public String addJobs(Model model) {
		model.addAttribute("jobPostActivity", new JobPostActivity());
		model.addAttribute("user", usersService.getCurrentUserProfile());
		return "add-jobs";
	}

	@PostMapping("/dashboard/addNew")
	public String addNew(JobPostActivity jobPostActivity, Model model) {

		Users user = usersService.getCurrentUser();
		if (user != null) {
			jobPostActivity.setPostedById(user);
		}
		jobPostActivity.setPostedDate(new Date());
		model.addAttribute("jobPostActivity", jobPostActivity);
		JobPostActivity saved = jobPostActivityService.addNew(jobPostActivity);
		return "redirect:/dashboard/";
	}
}
