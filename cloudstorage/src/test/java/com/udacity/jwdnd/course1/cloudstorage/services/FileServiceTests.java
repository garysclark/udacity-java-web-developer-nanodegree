package com.udacity.jwdnd.course1.cloudstorage.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.FileTests;

public class FileServiceTests {

	@Mock
	private MultipartFile multipartFile;
	@Mock
	private FileMapper fileMapper;
	@Mock
	private List<File> userFiles;
	
	@Captor
	private ArgumentCaptor<File> fileArgument;
	
	private FileService fileService;
	private File testFile;
	
	@BeforeEach
	public void beforeEach() {
		MockitoAnnotations.initMocks(this);
		fileService = new FileService(fileMapper);
		testFile = FileTests.getTestFile_1();
	}
	
	@Test
	public void canCreateFileService() {
		assertNotNull(fileService);
	}
	
	@Test
	public void canAddFile() throws IOException {
		Mockito.when(fileMapper.create(Mockito.any(File.class))).thenReturn(1);
		
		testFile.setId(null);
		
		Mockito.when(multipartFile.getContentType()).thenReturn(testFile.getContentType());
		Mockito.when(multipartFile.getBytes()).thenReturn(testFile.getData());
		Mockito.when(multipartFile.getOriginalFilename()).thenReturn(testFile.getName());
		Mockito.when(multipartFile.getSize()).thenReturn(Long.parseLong(testFile.getSize()));
	
		Integer rowAdded = fileService.addFile(testFile.getUserId(), multipartFile);
		
		assertEquals(1, rowAdded);
		Mockito.verify(fileMapper).create(fileArgument.capture());
		File file = fileArgument.getValue();
		assertEquals(testFile,file);
	}
	
	@Test
	public void canHandleAddFileError() {
		Mockito.when(fileMapper.create(Mockito.any(File.class))).thenReturn(-1);

		Integer rowAdded = fileService.addFile(0, multipartFile);

		assertEquals(-1, rowAdded);
	}
	
	@Test
	public void canGetListOfFilesForUser() {
		Mockito.when(fileMapper.findByUserId(testFile.getUserId())).thenReturn(userFiles);

		List<File> files = fileService.getFilesByUserId(testFile.getUserId());
		assertNotNull(files);
	}
	
	@Test
	public void canHandleGetFilesError() {
		Mockito.when(fileMapper.findByUserId(testFile.getUserId())).thenReturn(null);

		List<File> files = fileService.getFilesByUserId(testFile.getUserId());
		assertNull(files);
	}
	
	@Test
	public void canGetFileByFileId() {
		Mockito.when(fileMapper.findById(testFile.getId())).thenReturn(testFile);
		
		File foundFile = fileService.getFileByFileId(testFile.getId());
		
		assertEquals(testFile, foundFile);
	}
	
	@Test
	public void canDeleteFile() {
		Mockito.when(fileMapper.delete(testFile)).thenReturn(1);
		
		int rowsDeleted = fileService.deleteFile(testFile);
		
		assertEquals(1, rowsDeleted);
	}
	
	@Test
	public void canHandleDeleteFileError() {
		Mockito.when(fileMapper.delete(testFile)).thenReturn(0);
		
		int rowsDeleted = fileService.deleteFile(testFile);
		
		assertEquals(0, rowsDeleted);
	}
}
