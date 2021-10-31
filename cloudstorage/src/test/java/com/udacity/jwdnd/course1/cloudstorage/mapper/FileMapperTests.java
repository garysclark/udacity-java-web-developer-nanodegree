package com.udacity.jwdnd.course1.cloudstorage.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.FileTests;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.UserTests;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
public class FileMapperTests {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private FileMapper fileMapper;

	private User user;

	private File file;

	private Integer rowNum;

	@BeforeEach
	public void beforeEach() {
		user = UserTests.getTestUser_1();
		userMapper.create(user);
		file = FileTests.getTestFile_1();
		file.setUserId(user.getUserId());
		rowNum = fileMapper.create(file);
	}

	@Test
	public void canAccessMapper() {
		assertNotNull(fileMapper);
		assertNotNull(userMapper);
	}

	@Test
	public void canCreateFile() {
		assertTrue(rowNum >0);
	}

	@Test
	public void canFindByUserId() {
		List<File> files = fileMapper.findByUserId(user.getUserId());
		assertEquals(1, files.size());
		assertEquals(file, files.get(0));
	}
	
	@Test
	public void canFindById() {
		File storedFile = fileMapper.findById(file.getId());
		assertEquals(file, storedFile);
	}
	
	@Test
	public void canFindByFilename() {
		File storedFile = fileMapper.findByFileName(file.getUserId(), file.getName());
		assertEquals(file, storedFile);
	}
	
	@Test
	public void canUpdateFile() {
		File file2 = FileTests.getTestFile_2();
		file.setContentType(file2.getContentType());
		file.setData(file2.getData());
		file.setName(file2.getName());
		file.setSize(file2.getSize());
		
		User user2 = UserTests.getTestUser_2();
		userMapper.create(user2);
		file.setUserId(user2.getUserId());
		
		fileMapper.update(file);
		File storedFile = fileMapper.findById(file.getId());
		assertEquals(file, storedFile);
	}
	
	@Test
	public void canDeleteFile() {
		fileMapper.delete(file);
		File storedFile = fileMapper.findById(file.getId());
		assertNull(storedFile);
	}
	
	@Test
	public void canDetectUpdateError() {
		Integer rowsAffected = fileMapper.update(FileTests.getTestFile_2());
		assertEquals(0, rowsAffected);
	}
	
	@Test
	public void canDetectDeleteError() {
		Integer rowsAffected = fileMapper.delete(FileTests.getTestFile_2());
		assertEquals(0, rowsAffected);
	}
}
