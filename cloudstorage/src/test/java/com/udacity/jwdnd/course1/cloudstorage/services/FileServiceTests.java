package com.udacity.jwdnd.course1.cloudstorage.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
	
	@BeforeEach
	public void beforeEach() {
		MockitoAnnotations.initMocks(this);
		fileService = new FileService(fileMapper);
	}
	
	@Test
	public void canCreateFileService() {
		assertNotNull(fileService);
	}
	
	@Test
	public void canAddFile() throws IOException {
		Mockito.when(fileMapper.create(Mockito.any(File.class))).thenReturn(1);
		
		File testFile = FileTests.getTestFile_1();
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
	public void canHandleMapperError() {
		Mockito.when(fileMapper.create(Mockito.any(File.class))).thenReturn(-1);

		Integer rowAdded = fileService.addFile(0, multipartFile);

		assertEquals(-1, rowAdded);
	}
	
	@Test
	public void canGetListOfFilesForUser() {
		Integer userId = 99;
		
		Mockito.when(fileMapper.findByUserId(userId)).thenReturn(userFiles);

		List<File> files = fileService.getFiles(userId);
		assertNotNull(files);
	}
}
