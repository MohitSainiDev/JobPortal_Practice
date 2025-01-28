package com.mohit.jobportal.jobportal_practice.controller;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mohit.jobportal.jobportal_practice.entity.RecruiterProfile;
import com.mohit.jobportal.jobportal_practice.entity.Users;
import com.mohit.jobportal.jobportal_practice.repository.UsersRepository;
import com.mohit.jobportal.jobportal_practice.service.RecruiterProfileService;
import com.mohit.jobportal.jobportal_practice.util.FileUploadUtil;

@Controller
@RequestMapping("/recruiter-profile")
public class RecruiterProfileController {

	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	RecruiterProfileService recruiterProfileService;

	@GetMapping("/")
	public String recruiterProfile(Model model) {
				
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String username = authentication.getName();
			Users user = usersRepository.findByEmail(username)
					.orElseThrow(() -> new UsernameNotFoundException("Could not found user"));
			Optional<RecruiterProfile> recruiterProfile = recruiterProfileService.getOne(user.getUserId());

			if (!recruiterProfile.isEmpty())
				model.addAttribute("profile", recruiterProfile.get());
		}
		return "recruiter_profile";
		
	}

	@PostMapping("/addNew")
	public String addNew(RecruiterProfile recruiterProfile, @RequestParam("image") MultipartFile multipartFile,
			Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String username = authentication.getName();
			Users user = usersRepository.findByEmail(username)
					.orElseThrow(() -> new UsernameNotFoundException("Could not found user"));

			recruiterProfile.setUserId(user);
			recruiterProfile.setUserAccountId(user.getUserId());

		}
		model.addAttribute("profile", recruiterProfile);
		String fileName = "";
		if (!multipartFile.getOriginalFilename().equals("")) {
			fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
			recruiterProfile.setProfilePhoto(fileName);
		}
		RecruiterProfile savedUser = recruiterProfileService.addNew(recruiterProfile);

		String uploadDir = "photos/recruiter/" + savedUser.getUserAccountId();
		try {
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "redirect:/dashboard/";
	}

}
