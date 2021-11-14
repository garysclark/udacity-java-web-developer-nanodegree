package com.udacity.jwdnd.course1.cloudstorage.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialTests;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.UserTests;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

public class CredentialControllerTests {

	private static final String TEST_KEY = "Test Key";

	private static final String TEST_ENCRYPTED_PASSWORD = "Test Encrypted Password";

	private CredentialController credentialController;

	@Mock
	private CredentialService credentialService;
	@Mock
	private RedirectAttributes redirectAttributes;
	@Mock
	private Authentication authentication;
	@Mock
	private EncryptionService encryptionService;
	@Mock
	private UserService userService;
	@Captor
	private ArgumentCaptor<String> keyCaptor;
	@Captor
	private ArgumentCaptor<Object> valueCaptor;

	private Credential credential;

	private User user;


	@BeforeEach
	public void beforeEach() {
		MockitoAnnotations.initMocks(this);
		credentialController = new CredentialController(credentialService, userService, encryptionService);

		user = UserTests.getTestUser_1();

		Mockito.when(authentication.getName()).thenReturn(user.getUsername());
		Mockito.when(userService.getUser(user.getUsername())).thenReturn(user);
	}

	@Test
	public void canCreateController() {
		assertNotNull(credentialController);
	}

	@Test
	public void canCreateCredential() {
		prepareForNewCredentialTests();
		Mockito.when(credentialService.createCredential(credential)).thenReturn(1);

		String response = credentialController.createCredential(credential, redirectAttributes, authentication);

		assertEquals(TEST_KEY, credential.getKey());
		assertEquals(TEST_ENCRYPTED_PASSWORD, credential.getPassword());
		assertEquals(user.getUserId(), credential.getUserId());
		Mockito.verify(credentialService).createCredential(credential);
		verifyWithResult(true, CredentialController.ADD_CREDENTIAL_SUCCESS_MESSAGE, response);
	}

	@Test
	public void canHandleCreateCredentialError() {
		prepareForNewCredentialTests();
		Mockito.when(credentialService.createCredential(credential)).thenReturn(0);

		String response = credentialController.createCredential(credential, redirectAttributes, authentication);

		verifyWithResult(false, CredentialController.ADD_CREDENTIAL_ERROR_MESSAGE, response);
	}

	@Test
	public void canUpdateCredential() {
		prepareForExistingCredentialTests();
		Mockito.when(credentialService.updateCredential(credential)).thenReturn(1);

		String unencryptedPassword = credential.getPassword();
		assertNotEquals(TEST_ENCRYPTED_PASSWORD, unencryptedPassword);

		String response = credentialController.createCredential(credential, redirectAttributes, authentication);

		assertEquals(TEST_ENCRYPTED_PASSWORD, credential.getPassword());
		Mockito.verify(encryptionService).encryptValue(unencryptedPassword, credential.getKey());
		Mockito.verify(credentialService).updateCredential(credential);
		verifyWithResult(true, CredentialController.UPDATE_CREDENTIAL_SUCCESS_MESSAGE, response);
	}

	@Test
	public void canHandleUpdateCredentialError() {
		prepareForExistingCredentialTests();
		Mockito.when(credentialService.updateCredential(credential)).thenReturn(0);

		String response = credentialController.createCredential(credential, redirectAttributes, authentication);

		verifyWithResult(false, CredentialController.UPDATE_CREDENTIAL_ERROR_MESSAGE, response);
	}
	
	@Test
	public void canDeleteCredential() {
		prepareForExistingCredentialTests();
		Mockito.when(credentialService.deleteCredential(credential)).thenReturn(1);
		String response = credentialController.deleteCredential(credential, redirectAttributes, authentication);
		
		Mockito.verify(credentialService).deleteCredential(credential);
		verifyWithResult(true, CredentialController.DELETE_CREDENTIAL_SUCCESS_MESSAGE, response);
	}
	
	@Test
	public void canHandleDeleteCredentialError() {
		prepareForExistingCredentialTests();
		Mockito.when(credentialService.deleteCredential(credential)).thenReturn(0);

		String response = credentialController.deleteCredential(credential, redirectAttributes, authentication);

		verifyWithResult(false, CredentialController.DELETE_CREDENTIAL_ERROR_MESSAGE, response);
	}

	private void prepareForExistingCredentialTests() {
		credential = CredentialTests.getTestCredential_1();
		Mockito.when(credentialService.getCredentialById(credential.getId())).thenReturn(credential);
		Mockito.when(encryptionService.encryptValue(credential.getPassword(), credential.getKey())).thenReturn(TEST_ENCRYPTED_PASSWORD);
	}

	private void prepareForNewCredentialTests() {
		credential = CredentialTests.getTestCredential_1();
		credential.setId(null);
		credential.setKey(null);
		credential.setUserId(null);
		Mockito.when(credentialService.getCredentialById(credential.getId())).thenReturn(null);
		Mockito.when(encryptionService.generateKey()).thenReturn(TEST_KEY);
		Mockito.when(encryptionService.encryptValue(credential.getPassword(), TEST_KEY)).thenReturn(TEST_ENCRYPTED_PASSWORD);
	}

	private void verifyWithResult(boolean resultValue, String messageValue, String response) {
		Mockito.verify(redirectAttributes, times(3)).addFlashAttribute(keyCaptor.capture(), valueCaptor.capture());
		List<String> keys = keyCaptor.getAllValues();
		List<Object> values = valueCaptor.getAllValues();

		assertEquals(CloudStorageController.SUCCESS_KEY, keys.get(0));
		assertEquals(resultValue, values.get(0));

		assertEquals(CloudStorageController.MESSAGE_KEY, keys.get(1));
		assertEquals(messageValue, values.get(1));

		assertEquals(CloudStorageController.ACTIVE_TAB_KEY, keys.get(2));
		assertEquals(CredentialController.ACTIVE_TAB_CREDENTIALS, values.get(2));

		assertEquals(ResultController.REDIRECT_RESULT_RESPONSE, response);
	}

}
