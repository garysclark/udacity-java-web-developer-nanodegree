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

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
public class FileController {

	public static final String VIEW_FILES_ENDPOINT = "/file/view";
	public static final String DELETE_FILES_ENDPOINT = "/file/delete";
	public static final String UPLOAD_FILE_ENDPOINT = "/file/upload";
	public static final String UPLOAD_FILE_DUPLICATE_FILENAME_ERROR_MESSAGE = "This file has already been uploaded. Please try again.";
	public static final String DELETE_FILE_ERROR_MESSAGE = "There was an error deleting the file.  Please try again.";
	public static final String DELETE_FILE_SUCCESS_MESSAGE = "You successfully deleted a file.";
	public static final String UPLOAD_FILE_NO_FILE_SELECTED_ERROR_MESSAGE = "No file selected. Please try again.";
	public static final String UPLOAD_FILE_ERROR_MESSAGE = "There was an error adding the file.  Please try again.";
	public static final String UPLOAD_FILE_SUCCESS_MESSAGE = "You successfully added a file.";
	public static final String FILES_DATA_KEY = "files";
	public static final String ACTIVE_TAB_FILES = "files";
	public static final String FILE_DTO_ATTRIBUTE = "fileDTO";
	public static final Long MAX_FILE_SIZE = (long) 1E+6;
	public static final String UPLOAD_FILE_FILE_TOO_LARGE_ERROR_MESSAGE = "The file you selected is larger than " + MAX_FILE_SIZE + " bytes. Please select another file.";

	private UserService userService;
	private FileService fileService;

	public FileController(FileService fileService, UserService userService) {
		this.fileService = fileService;
		this.userService = userService;
	}

	@PostMapping(UPLOAD_FILE_ENDPOINT)
	public String uploadFile(RedirectAttributes redirectAttributes, Authentication authentication, Model model, @ModelAttribute(FILE_DTO_ATTRIBUTE) MultipartFile file) {

		if(file.getOriginalFilename().equals("")) {
			setupResult(false, UPLOAD_FILE_NO_FILE_SELECTED_ERROR_MESSAGE, redirectAttributes);
			return ResultController.REDIRECT_RESULT_RESPONSE;
		} 

		if(file.getSize() > MAX_FILE_SIZE) {
			setupResult(false, UPLOAD_FILE_FILE_TOO_LARGE_ERROR_MESSAGE, redirectAttributes);
			return ResultController.REDIRECT_RESULT_RESPONSE;
		} 

		String userName = authentication.getName();
		User user = userService.getUser(userName);

		if(fileService.getFileByFileName(user.getUserId(), file.getOriginalFilename()) != null) {
			setupResult(false, UPLOAD_FILE_DUPLICATE_FILENAME_ERROR_MESSAGE, redirectAttributes);
			return ResultController.REDIRECT_RESULT_RESPONSE;
		}
		
		int rowAdded = fileService.addFile(user.getUserId(), file);

		if(rowAdded > 0) {
			setupResult(true, UPLOAD_FILE_SUCCESS_MESSAGE, redirectAttributes);
		} else {
			setupResult(false, UPLOAD_FILE_ERROR_MESSAGE, redirectAttributes);
		}

		return ResultController.REDIRECT_RESULT_RESPONSE;
	}

	@PostMapping(DELETE_FILES_ENDPOINT)
	public String deleteFile(@ModelAttribute File file, RedirectAttributes redirectAttributes, Authentication authentication) {

		int rowsDeleted = fileService.deleteFile(file);

		if(rowsDeleted > 0) {
			setupResult(true, DELETE_FILE_SUCCESS_MESSAGE, redirectAttributes);
		} else {
			setupResult(false, DELETE_FILE_ERROR_MESSAGE, redirectAttributes);
		}

		return ResultController.REDIRECT_RESULT_RESPONSE;
	}

	@GetMapping(VIEW_FILES_ENDPOINT)
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
		redirectAttributes.addFlashAttribute(CloudStorageController.SUCCESS_KEY, isSuccess);
		redirectAttributes.addFlashAttribute(CloudStorageController.MESSAGE_KEY, message);
		redirectAttributes.addFlashAttribute(CloudStorageController.ACTIVE_TAB_KEY, ACTIVE_TAB_FILES);
	}

}
