package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.udacity.jwdnd.course1.cloudstorage.dto.FileDTO;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
public class FileController {

	private UserService userService;
	private FileService fileService;

	public FileController(FileService fileService, UserService userService) {
		this.fileService = fileService;
		this.userService = userService;
	}

	@ModelAttribute("fileDTO")
	public FileDTO getFileDTO() {
		return new FileDTO();
	}
	
	@PostMapping("/files")
	public String uploadFile(RedirectAttributes redirectAttributes, Authentication authentication, Model model, @ModelAttribute("fileDTO") MultipartFile file) {
		String userName = authentication.getName();
		User user = userService.getUser(userName);
		int rowAdded = fileService.addFile(user.getUserId(), file);
	
		if(rowAdded < 0) {
			redirectAttributes.addFlashAttribute("error", true);
			redirectAttributes.addFlashAttribute("errorMessage", "There was an error adding the note.  Please try again.");
		} else {
			redirectAttributes.addFlashAttribute("success", true);
			redirectAttributes.addFlashAttribute("successMessage", "You successfully added a file.");
		}

		redirectAttributes.addFlashAttribute("activeTab", "files");
		return "redirect:/result";
	}

}
