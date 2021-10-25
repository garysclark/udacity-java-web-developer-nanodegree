package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.udacity.jwdnd.course1.cloudstorage.dto.FileDTO;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
public class FileController {

	static final String MAPPING_RESULT = "redirect:/result";
	static final String ADD_FILE_SUCCESS_MESSAGE = "You successfully added a file.";
	static final String ADD_FILE_ERROR_MESSAGE = "There was an error adding the note.  Please try again.";
	static final String ADD_NO_FILE_SELECTED_ERROR_MESSAGE = "No file selected. Please try again.";

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

		if(file.getOriginalFilename().equals("")) {
			setupResult(false, ADD_NO_FILE_SELECTED_ERROR_MESSAGE, redirectAttributes);
		} else {
			String userName = authentication.getName();
			User user = userService.getUser(userName);
			int rowAdded = fileService.addFile(user.getUserId(), file);

			if(rowAdded > 0) {
				setupResult(true, ADD_FILE_SUCCESS_MESSAGE, redirectAttributes);
			} else {
				setupResult(false, ADD_FILE_ERROR_MESSAGE, redirectAttributes);
			}
		}

		return MAPPING_RESULT;
	}
	
	@PostMapping("/files/delete")
	public String deleteFile(@ModelAttribute File file, RedirectAttributes redirectAttributes, Authentication authentication) {
		return MAPPING_RESULT;
	}

	@GetMapping("/files/view")
	public ResponseEntity<byte[]> viewFile(RedirectAttributes redirectAttributes, Authentication authentication, Integer fileId) {
		File file = fileService.getFileByFileId(fileId);
		String filename = file.getName();
		ResponseEntity<byte[]> responseEntity = ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(file.getContentType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\""+filename+"\"")
				.body(file.getData());
		return responseEntity;
	}

	private void setupResult(boolean isSuccess, String message, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("success", isSuccess);
		redirectAttributes.addFlashAttribute("message", message);
		redirectAttributes.addFlashAttribute("activeTab", "files");
	}

}
