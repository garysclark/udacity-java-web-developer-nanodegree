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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.FileTests;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.UserTests;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

public class FileControllerTests {

	private static final String TEST_FILE_NAME = "File Name";
	@Mock
	private RedirectAttributes redirectAttributes;
	@Mock
	private Authentication authentication;
	@Mock
	private Model model;
	@Mock
	private MultipartFile multipartFile;
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
		Mockito.when(fileService.getFilesByUserId(user.getUserId())).thenReturn(storedFiles);
	}

	@Test
	public void canCreateFileController() {
		assertNotNull(fileController);
	}

	@Test
	public void canUploadFile() {
		Mockito.when(multipartFile.getOriginalFilename()).thenReturn(TEST_FILE_NAME);
		Mockito.when(fileService.addFile(user.getUserId(), multipartFile)).thenReturn(1);

		String response = fileController.uploadFile(redirectAttributes, authentication, model, multipartFile);

		verifyAddFileWithResult(true, FileController.ADD_FILE_SUCCESS_MESSAGE, response);
	}

	@Test
	public void canHandleAddFileError() {
		Mockito.when(multipartFile.getOriginalFilename()).thenReturn(TEST_FILE_NAME);
		Mockito.when(fileService.addFile(user.getUserId(), multipartFile)).thenReturn(-1);

		String response = fileController.uploadFile(redirectAttributes, authentication, model, multipartFile);

		verifyAddFileWithResult(false, FileController.ADD_FILE_ERROR_MESSAGE, response);
	}

	@Test
	public void canHandleFileTooLargeError() {
		Mockito.when(multipartFile.getOriginalFilename()).thenReturn(TEST_FILE_NAME);
		Mockito.when(multipartFile.getSize()).thenReturn(FileController.MAX_FILE_SIZE + 1);

		String response = fileController.uploadFile(redirectAttributes, authentication, model, multipartFile);

		verifyResultAndAttributes(false, FileController.ADD_FILE_TOO_LARGE_ERROR_MESSAGE, response);
	}

	@Test
	public void canHandleNoFileSelectedError() {
		Mockito.when(multipartFile.getOriginalFilename()).thenReturn("");

		String response = fileController.uploadFile(redirectAttributes, authentication, model, multipartFile);

		verifyResultAndAttributes(false, FileController.ADD_NO_FILE_SELECTED_ERROR_MESSAGE, response);
	}

	@Test
	public void canViewFile() {
		Integer fileId = 1;
		File file = FileTests.getTestFile_1();
		Mockito.when(fileService.getFileByFileId(fileId)).thenReturn(file);

		ResponseEntity<byte[]> response = fileController.viewFile(redirectAttributes, authentication, fileId);

		assertNotNull(response);
		assertEquals(file.getData(), response.getBody());
	}

	@Test
	public void canDeleteFile() {
		File file = FileTests.getTestFile_1();
		Mockito.when(fileService.deleteFile(file)).thenReturn(1);

		String response = fileController.deleteFile(file, redirectAttributes, authentication);

		verifyDeleteFileWithResult(true, FileController.DELETE_FILE_SUCCESS_MESSAGE, response, file);
	}

	@Test
	public void canHandleDeleteFileError() {
		File file = FileTests.getTestFile_1();
		Mockito.when(fileService.deleteFile(file)).thenReturn(0);

		String response = fileController.deleteFile(file, redirectAttributes, authentication);

		verifyDeleteFileWithResult(false, FileController.DELETE_FILE_ERROR_MESSAGE, response, file);
	}

	@Test
	public void canHandleDuplicateFilenameError() {
		File file = FileTests.getTestFile_1();
		Mockito.when(multipartFile.getOriginalFilename()).thenReturn(TEST_FILE_NAME);
		Mockito.when(fileService.getFileByFileName(user.getUserId(), file.getName())).thenReturn(file);

		String response = fileController.uploadFile(redirectAttributes, authentication, model, multipartFile);

		verifyResultAndAttributes(false, FileController.DUPLICATE_FILENAME_ERROR_MESSAGE, response);
	}

	private void verifyAddFileWithResult(boolean resultValue, String messageValue, String response) {
		Mockito.verify(fileService).addFile(user.getUserId(), multipartFile);
		verifyResultAndAttributes(resultValue, messageValue, response);
	}

	private void verifyDeleteFileWithResult(boolean resultValue, String messageValue, String response, File file) {
		Mockito.verify(fileService).deleteFile(file);
		verifyResultAndAttributes(resultValue, messageValue, response);
	}

	private void verifyResultAndAttributes(boolean resultValue, String messageValue, String response) {
		Mockito.verify(redirectAttributes, times(3)).addFlashAttribute(keyCaptor.capture(), valueCaptor.capture());
		List<String> keys = keyCaptor.getAllValues();
		List<Object> values = valueCaptor.getAllValues();

		assertEquals(CloudStorageController.SUCCESS_KEY, keys.get(0));
		assertEquals(resultValue, values.get(0));

		assertEquals(CloudStorageController.MESSAGE_KEY, keys.get(1));
		assertEquals(messageValue, values.get(1));

		assertEquals(CloudStorageController.ACTIVE_TAB_KEY, keys.get(2));
		assertEquals(FileController.ACTIVE_TAB_FILES, values.get(2));

		assertEquals(ResultController.REDIRECT_RESULT_RESPONSE, response);
	}
}
