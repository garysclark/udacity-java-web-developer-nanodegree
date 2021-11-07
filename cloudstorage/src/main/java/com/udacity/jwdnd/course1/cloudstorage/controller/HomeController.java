package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udacity.jwdnd.course1.cloudstorage.dto.FileDTO;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;


@Controller
@RequestMapping(HomeController.HOME_ENDPOINT)
public class HomeController {
	
	public static final String HOME_ENDPOINT = "/home";
	public static final String HOME_RESPONSE = "home";
	public static final String REDIRECT_HOME_RESPONSE = "redirect:/home";
	public static final String ENCRYPTION_SERVICE_KEY = "encryptionService";

	private UserService userService;
	private NoteService noteService;
	private FileService fileService;
	private CredentialService credentialService;
	private EncryptionService encryptionService;

	public HomeController(UserService userService, NoteService noteService, 
			FileService fileService, CredentialService credentialService, EncryptionService encryptionService) {
		this.userService = userService;
		this.noteService = noteService;
		this.fileService = fileService;
		this.credentialService = credentialService;
		this.encryptionService = encryptionService;
	}

	@ModelAttribute(FileController.FILE_DTO_ATTRIBUTE)
	public FileDTO getFileDTO() {
		return new FileDTO();
	}

	@GetMapping()
	public String getContent(Model model, Authentication authentication, String activeTab) {
		if(activeTab == null) {
			model.addAttribute(CloudStorageController.ACTIVE_TAB_KEY, FileController.ACTIVE_TAB_FILES);
		} else {
			model.addAttribute(CloudStorageController.ACTIVE_TAB_KEY, activeTab);
		}

		User user = userService.getUser(authentication.getName());

		if(model.getAttribute(NoteController.NOTES_DATA_KEY) == null) {
			List<Note> notes = noteService.getNotes(user.getUserId());
			model.addAttribute(NoteController.NOTES_DATA_KEY, notes);
		}
		
		if(model.getAttribute(FileController.FILES_DATA_KEY) == null) {
			List<File> files = fileService.getFilesByUserId(user.getUserId());
			model.addAttribute(FileController.FILES_DATA_KEY, files);
		}
		
		if(model.getAttribute(CredentialController.CREDENTIALS_DATA_KEY) == null) {
			List<Credential> credentials = credentialService.getCredentialsByUserId(user.getUserId());
			model.addAttribute(CredentialController.CREDENTIALS_DATA_KEY, credentials);
		}

		model.addAttribute(ENCRYPTION_SERVICE_KEY, encryptionService);
		
		return HOME_RESPONSE;
	}

}
