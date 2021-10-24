package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

	static final String MAPPING_RESULT = "redirect:/result";
	static final String ADD_FILE_SUCCESS_MESSAGE = "You successfully added a file.";
	static final String ADD_FILE_ERROR_MESSAGE = "There was an error adding the note.  Please try again.";

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
	
		if(rowAdded > 0) {
			setupResult(true, ADD_FILE_SUCCESS_MESSAGE, redirectAttributes);
		} else {
			setupResult(false, ADD_FILE_ERROR_MESSAGE, redirectAttributes);
		}

		return MAPPING_RESULT;
	}
	
	@GetMapping("/files/view")
	public String viewFile(RedirectAttributes redirectAttributes, Authentication authentication, String fileId) {
		return MAPPING_RESULT;
	}
	
	private void setupResult(boolean isSuccess, String message, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("success", isSuccess);
		redirectAttributes.addFlashAttribute("message", message);
		redirectAttributes.addFlashAttribute("activeTab", "files");
	}

}
