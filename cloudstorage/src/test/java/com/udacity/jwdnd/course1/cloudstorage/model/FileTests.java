package com.udacity.jwdnd.course1.cloudstorage.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileTests {

	private static final Integer TEST_FILE_ID = 99;
	private static final String TEST_FILE_NAME = "File Name";
	private static final String TEST_CONTENT_TYPE = "Content Type";
	private static final String TEST_FILE_SIZE = "224455";
	private static final Integer TEST_USER_ID = 999;
	private static final byte[] TEST_FILE_DATA = {11,22,33,44};

	private static final Integer TEST_FILE_ID_2 = 100;
	private static final String TEST_FILE_NAME_2 = "File Name 2";
	private static final String TEST_CONTENT_TYPE_2 = "Content Type 2";
	private static final String TEST_FILE_SIZE_2 = "55443322";
	private static final Integer TEST_USER_ID_2 = 1000;
	private static final byte[] TEST_FILE_DATA_2 = {44,33,22,11};

	private File file;

	@BeforeEach
	public void beforeEach() {
		file = getTestFile();
	}

	@Test
	public void canCreateFile() {
		assertNotNull(file);
		assertEquals(TEST_FILE_ID, file.getId());
		assertEquals(TEST_FILE_NAME, file.getName());
		assertEquals(TEST_CONTENT_TYPE, file.getContentType());
		assertEquals(TEST_FILE_SIZE, file.getSize());
		assertEquals(TEST_USER_ID, file.getUserId());
		assertEquals(TEST_FILE_DATA, file.getData());
	}
	
	@Test
	public void canSetAttributes() {
		file.setId(TEST_FILE_ID_2);
		assertEquals(TEST_FILE_ID_2, file.getId());
		file.setName(TEST_FILE_NAME_2);
		assertEquals(TEST_FILE_NAME_2, file.getName());
		file.setContentType(TEST_CONTENT_TYPE_2);
		assertEquals(TEST_CONTENT_TYPE_2, file.getContentType());
		file.setSize(TEST_FILE_SIZE_2);
		assertEquals(TEST_FILE_SIZE_2, file.getSize());
		file.setUserId(TEST_USER_ID_2);
		assertEquals(TEST_USER_ID_2, file.getUserId());
		file.setData(TEST_FILE_DATA_2);
		assertEquals(TEST_FILE_DATA_2, file.getData());
	}
	
	@Test
	public void canVerifyEquals() {
		File file2 = getTestFile();
		assertEquals(file, file2);
	}

	static public File getTestFile() {
		return new File(TEST_FILE_ID, TEST_FILE_NAME, TEST_CONTENT_TYPE, TEST_FILE_SIZE, TEST_USER_ID, TEST_FILE_DATA);
	}
}
