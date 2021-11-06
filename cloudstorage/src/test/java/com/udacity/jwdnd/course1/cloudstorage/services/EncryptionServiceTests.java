package com.udacity.jwdnd.course1.cloudstorage.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EncryptionServiceTests {

	private static final String TEST_PASSWORD = "password";
	private EncryptionService encryptionService;
	private String key;

	@BeforeEach
	public void beforeEach() {
		encryptionService = new EncryptionService();
		key = encryptionService.generateKey();
	}
	@Test
	public void canCreateService() {
		assertNotNull(encryptionService);
	}
	
	@Test
	public void canGenerateKey() {
		assertNotNull(key);
	}
	
	@Test
	public void canEncryptDecryptPassword() {
		String encryptedPassword = encryptionService.encryptValue(TEST_PASSWORD, key);
		assertNotNull(encryptedPassword);
		String unencryptedPassword = encryptionService.decryptValue(encryptedPassword, key);
		assertEquals(TEST_PASSWORD, unencryptedPassword);
	}
}
