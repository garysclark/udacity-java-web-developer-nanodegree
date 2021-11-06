package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
public class CredentialController {

	static final String MAPPING_RESULT = "redirect:/result";

	static final String ADD_CREDENTIAL_SUCCESS_MESSAGE = "You successfully added a credential.";

	static final String ADD_CREDENTIAL_ERROR_MESSAGE = "There was an error adding the credential.  Please try again.";

	private CredentialService credentialService;

	private UserService userService;

	private EncryptionService encryptionService;
	
	public CredentialController(CredentialService credentialService, UserService userService, EncryptionService encryptionService) {
		this.credentialService = credentialService;
		this.userService = userService;
		this.encryptionService = encryptionService;
	}

	@PostMapping("/credentials")
	public String createCredential(@ModelAttribute Credential credential, RedirectAttributes redirectAttributes,
			Authentication authentication) {

		User user = userService.getUser(authentication.getName());
		
		String encryptionKey = encryptionService.generateKey();
		String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encryptionKey);
		credential.setKey(encryptionKey);
		credential.setPassword(encryptedPassword);
		credential.setUserId(user.getUserId());
		
		int rowsAdded = credentialService.createCredential(credential);
		if(rowsAdded > 0) {
			setupResult(true, ADD_CREDENTIAL_SUCCESS_MESSAGE, redirectAttributes);
		} else {
			setupResult(false, ADD_CREDENTIAL_ERROR_MESSAGE, redirectAttributes);
		}
		
		return MAPPING_RESULT;
	}
	
	private void setupResult(boolean isSuccess, String message, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("success", isSuccess);
		redirectAttributes.addFlashAttribute("message", message);
		redirectAttributes.addFlashAttribute("activeTab", "credentials");
	}

}
