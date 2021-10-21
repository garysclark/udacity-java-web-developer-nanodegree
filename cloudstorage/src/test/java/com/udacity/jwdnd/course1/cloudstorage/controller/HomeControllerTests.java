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

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.UserTests;
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
	private Model model;
	@Mock
	private Authentication authentication;
	@Mock
	private List<Note> notes;
	@Mock
	private List<File> files;

	private HomeController homeController;

	@Captor
	private ArgumentCaptor<String> keyCaptor;
	@Captor
	private ArgumentCaptor<Object> valueCaptor;

	@BeforeEach
	public void beforeEach() {
		MockitoAnnotations.initMocks(this);
		homeController = new HomeController(userService, noteService, fileService);
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
		Mockito.when(fileService.getFiles(user.getUserId())).thenReturn(files);		
		String response = homeController.getContent(model, authentication);
		
		Mockito.verify(model, times(3)).addAttribute(keyCaptor.capture(), valueCaptor.capture());
		List<String> keys = keyCaptor.getAllValues();
		List<Object> values = valueCaptor.getAllValues();
		
		assertEquals("activeTab", keys.get(0));
		assertEquals("files", values.get(0));
		
		assertEquals("notes", keys.get(1));
		assertEquals(notes, values.get(1));
		
		assertEquals("files", keys.get(2));
		assertEquals(files, values.get(2));
		
		assertEquals("home", response);
	}
}
