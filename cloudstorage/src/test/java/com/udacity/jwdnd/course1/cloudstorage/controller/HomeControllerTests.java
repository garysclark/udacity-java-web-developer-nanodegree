package com.udacity.jwdnd.course1.cloudstorage.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.springframework.ui.Model;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.UserTests;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

public class HomeControllerTests {

	@Mock
	private UserService userService;
	@Mock
	private NoteService noteService;
	@Mock
	private FileService fileService;
	@Mock
	private CredentialService credentialService;
	@Mock
	private EncryptionService encryptionService;

	@Mock
	private Model model;
	@Mock
	private Authentication authentication;
	@Mock
	private List<Note> notes;
	@Mock
	private List<File> files;
	@Mock
	private List<Credential> credentials;

	private HomeController homeController;

	@Captor
	private ArgumentCaptor<String> keyCaptor;
	@Captor
	private ArgumentCaptor<Object> valueCaptor;

	@BeforeEach
	public void beforeEach() {
		MockitoAnnotations.initMocks(this);
		homeController = new HomeController(userService, noteService, fileService, credentialService, encryptionService);
	}
	
	@Test
	public void canCreateController() {
		assertNotNull(homeController);
	}
	
	@Test
	public void canGetContentOnLogin() {
		User user = UserTests.getTestUser_1();
		Mockito.when(userService.getUser(user.getUsername())).thenReturn(user);
		Mockito.when(authentication.getName()).thenReturn(user.getUsername());
		Mockito.when(noteService.getNotes(user.getUserId())).thenReturn(notes);
		Mockito.when(fileService.getFilesByUserId(user.getUserId())).thenReturn(files);		
		Mockito.when(credentialService.getCredentialsByUserId(user.getUserId())).thenReturn(credentials);		

		String response = homeController.getContent(model, authentication, null);
		
		Mockito.verify(model, times(5)).addAttribute(keyCaptor.capture(), valueCaptor.capture());
		List<String> keys = keyCaptor.getAllValues();
		List<Object> values = valueCaptor.getAllValues();
		
		assertEquals(CloudStorageController.ACTIVE_TAB_KEY, keys.get(0));
		assertEquals(FileController.ACTIVE_TAB_FILES, values.get(0));
		
		assertEquals(NoteController.NOTES_DATA_KEY, keys.get(1));
		assertEquals(notes, values.get(1));
		
		assertEquals(FileController.FILES_DATA_KEY, keys.get(2));
		assertEquals(files, values.get(2));
		
		assertEquals(CredentialController.CREDENTIALS_DATA_KEY, keys.get(3));
		assertEquals(credentials, values.get(3));
		
		assertEquals(HomeController.ENCRYPTION_SERVICE_KEY, keys.get(4));
		assertEquals(encryptionService, values.get(4));
		
		assertEquals(HomeController.HOME_RESPONSE, response);
	}
}
