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

	private static final String TEST_SUFFIX = "- EDIT";

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private FileMapper fileMapper;

	private User testUser1;
	private User createdUser1;

	private File file;

	private Integer rowNum;

	private User testUser2;

	private User createdUser2;

	@BeforeEach
	public void beforeEach() {
		testUser1 = UserTests.getTestUser_1();
		userMapper.create(testUser1);
		createdUser1 = userMapper.findByUsername(testUser1.getUsername());

		testUser2 = UserTests.getTestUser_2();
		userMapper.create(testUser2);
		createdUser2 = userMapper.findByUsername(testUser2.getUsername());

		file = FileTests.getTestFile();
		file.setUserId(createdUser1.getUserId());
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
		List<File> files = fileMapper.findByUserId(createdUser1.getUserId());
		assertEquals(1, files.size());
		assertEquals(file, files.get(0));
	}
	
	@Test
	public void canFindById() {
		File storedFile = fileMapper.findById(file.getId());
		assertEquals(file, storedFile);
	}
	
	@Test
	public void canUpdateFile() {
		file.setContentType(file.getContentType() + TEST_SUFFIX);
		
		byte[] data = file.getData();
		for(int i = 0; i < data.length; i++) {
			data[i]++;
		}
		file.setData(data);
		
		file.setName(file.getName() +  TEST_SUFFIX);
		file.setSize(file.getSize() + TEST_SUFFIX);
		file.setUserId(createdUser2.getUserId());
		
		fileMapper.update(file.getId(), file.getName(), file.getContentType(), file.getSize(), file.getUserId(), file.getData());
		File storedFile = fileMapper.findById(file.getId());
		assertEquals(file, storedFile);
	}
	
	@Test
	public void canDeleteFile() {
		fileMapper.delete(file);
		File storedFile = fileMapper.findById(file.getId());
		assertNull(storedFile);
	}
}
