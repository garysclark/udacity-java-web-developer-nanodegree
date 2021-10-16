package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.udacity.jwdnd.course1.cloudstorage.dto.FileDTO;

@Controller
public class FileController {

	@ModelAttribute("fileDTO")
	public FileDTO getFileDTO() {
		return new FileDTO();
	}
	
	@PostMapping("/files")
	public String uploadFile(RedirectAttributes redirectAttributes, Authentication authentication, Model model, @ModelAttribute("fileDTO") MultipartFile file) {
		
		return "redirect:/home";
	}

}
