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

	public static final String CREDENTIALS_ENDPOINT = "/credentials";

	public static final String CREDENTIALS_DELETE_ENDPOINT = "/credentials/delete";

	private CredentialService credentialService;

	private UserService userService;

	private EncryptionService encryptionService;

	public static final String ADD_CREDENTIAL_ERROR_MESSAGE = "There was an error adding the credential.  Please try again.";

	public static final String ADD_CREDENTIAL_SUCCESS_MESSAGE = "You successfully added a credential.";

	public static final String CREDENTIALS_DATA_KEY = "credentials";

	public static final String ACTIVE_TAB_CREDENTIALS = "credentials";

	public static final String UPDATE_CREDENTIAL_SUCCESS_MESSAGE = "You successfully updated a credential.";

	public static final String UPDATE_CREDENTIAL_ERROR_MESSAGE = "There was an error updating the credential.  Please try again.";

	public static final String DELETE_CREDENTIAL_SUCCESS_MESSAGE = "You successfully deleted a credential.";

	public static final String DELETE_CREDENTIAL_ERROR_MESSAGE = "There was an error deleting the credential.  Please try again.";


	public CredentialController(CredentialService credentialService, UserService userService, EncryptionService encryptionService) {
		this.credentialService = credentialService;
		this.userService = userService;
		this.encryptionService = encryptionService;
	}

	@PostMapping(CREDENTIALS_ENDPOINT)
	public String createCredential(@ModelAttribute Credential credential, RedirectAttributes redirectAttributes,
			Authentication authentication) {

		if(credentialService.getCredentialById(credential.getId()) == null) {

			User user = userService.getUser(authentication.getName());

			String encryptionKey = encryptionService.generateKey();
			String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encryptionKey);
			credential.setKey(encryptionKey);
			credential.setPassword(encryptedPassword);
			credential.setUserId(user.getUserId());

			int rowsAdded = credentialService.createCredential(credential);
			if(rowsAdded > 0) {
				setupResult(true, CredentialController.ADD_CREDENTIAL_SUCCESS_MESSAGE, redirectAttributes);
			} else {
				setupResult(false, CredentialController.ADD_CREDENTIAL_ERROR_MESSAGE, redirectAttributes);
			}
		} else {
			String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), credential.getKey());
			credential.setPassword(encryptedPassword);

			int rowsUpdated = credentialService.updateCredential(credential);
			if(rowsUpdated > 0) {
				setupResult(true, CredentialController.UPDATE_CREDENTIAL_SUCCESS_MESSAGE, redirectAttributes);
			} else {
				setupResult(false, CredentialController.UPDATE_CREDENTIAL_ERROR_MESSAGE, redirectAttributes);
			}
		}

		return ResultController.REDIRECT_RESULT_RESPONSE;
	}
	
	@PostMapping(CREDENTIALS_DELETE_ENDPOINT)
	public String deleteCredential(@ModelAttribute Credential credential, RedirectAttributes redirectAttributes,
			Authentication authentication) {
		
		int rowsDeleted = credentialService.deleteCredential(credential);
		if(rowsDeleted > 0) {
			setupResult(true, DELETE_CREDENTIAL_SUCCESS_MESSAGE, redirectAttributes);
		} else {
			setupResult(false, DELETE_CREDENTIAL_ERROR_MESSAGE, redirectAttributes);
		}
		
		return ResultController.REDIRECT_RESULT_RESPONSE;
	}

	private void setupResult(boolean isSuccess, String message, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute(CloudStorageController.SUCCESS_KEY, isSuccess);
		redirectAttributes.addFlashAttribute(CloudStorageController.MESSAGE_KEY, message);
		redirectAttributes.addFlashAttribute(CloudStorageController.ACTIVE_TAB_KEY, CredentialController.ACTIVE_TAB_CREDENTIALS);
	}

}
