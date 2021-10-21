package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udacity.jwdnd.course1.cloudstorage.dto.FileDTO;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;


@Controller
@RequestMapping("/home")
public class HomeController {
	
	private UserService userService;
	private NoteService noteService;
	private FileService fileService;

	public HomeController(UserService userService, NoteService noteService, FileService fileService) {
		this.userService = userService;
		this.noteService = noteService;
		this.fileService = fileService;
	}

	@ModelAttribute("fileDTO")
	public FileDTO getFileDTO() {
		return new FileDTO();
	}

	@GetMapping()
	public String getContent(Model model, Authentication authentication, String activeTab) {
		if(activeTab == null) {
			model.addAttribute("activeTab", "files");
		} else {
			model.addAttribute("activeTab", activeTab);
		}

		User user = userService.getUser(authentication.getName());

		if(model.getAttribute("notes") == null) {
			List<Note> notes = noteService.getNotes(user.getUserId());
			model.addAttribute("notes", notes);
		}
		
		if(model.getAttribute("files") == null) {
			List<File> files = fileService.getFiles(user.getUserId());
			model.addAttribute("files", files);
		}

		return "home";
	}

}
