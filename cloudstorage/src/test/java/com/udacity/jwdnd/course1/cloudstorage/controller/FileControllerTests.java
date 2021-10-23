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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.udacity.jwdnd.course1.cloudstorage.dto.FileDTO;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.UserTests;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

public class FileControllerTests {

	@Mock
	private RedirectAttributes redirectAttributes;
	@Mock
	private Authentication authentication;
	@Mock
	private Model model;
	@Mock
	private MultipartFile file;
	@Mock
	private FileService fileService;
	@Mock
	private UserService userService;
	@Captor
	private ArgumentCaptor<String> keyCaptor;
	@Captor
	private ArgumentCaptor<Object> valueCaptor;
	
	private FileController fileController;
	private User user;
	private List<File> storedFiles;
	
	@BeforeEach
	public void beforeEach() {
		MockitoAnnotations.initMocks(this);
		user = UserTests.getTestUser_1();
		fileController = new FileController(fileService, userService);
		Mockito.when(authentication.getName()).thenReturn(user.getUsername());
		Mockito.when(userService.getUser(user.getUsername())).thenReturn(user);
		Mockito.when(fileService.getFiles(user.getUserId())).thenReturn(storedFiles);
	}
	
	@Test
	public void canCreateFileController() {
		assertNotNull(fileController);
	}
	
	@Test
	public void canGetDTO() {
		FileDTO dto = fileController.getFileDTO();
		assertNotNull(dto);
	}
	
	@Test
	public void canUploadFile() {
		Mockito.when(fileService.addFile(user.getUserId(), file)).thenReturn(1);
	
		String response = fileController.uploadFile(redirectAttributes, authentication, model, file);

		verifyWithResult("success", true, "successMessage", "You successfully added a file.", response);
	}

	@Test
	public void canHandleAddFileError() {
		Mockito.when(fileService.addFile(user.getUserId(), file)).thenReturn(-1);
		
		String response = fileController.uploadFile(redirectAttributes, authentication, model, file);

		verifyWithResult("error", true, "errorMessage", "There was an error adding the note.  Please try again.", response);
	}
	
	private void verifyWithResult(String resultKey, boolean resultValue, String messageKey, String messageValue, String response) {
		Mockito.verify(fileService).addFile(user.getUserId(), file);
		Mockito.verify(redirectAttributes, times(3)).addFlashAttribute(keyCaptor.capture(), valueCaptor.capture());
		List<String> keys = keyCaptor.getAllValues();
		List<Object> values = valueCaptor.getAllValues();

		assertEquals(resultKey, keys.get(0));
		assertEquals(resultValue, values.get(0));

		assertEquals(messageKey, keys.get(1));
		assertEquals(messageValue, values.get(1));

		assertEquals("activeTab", keys.get(2));
		assertEquals("files", values.get(2));

		assertEquals("redirect:/result", response);
	}
}
