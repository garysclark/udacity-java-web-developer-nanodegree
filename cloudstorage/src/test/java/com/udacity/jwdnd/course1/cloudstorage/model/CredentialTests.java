package com.udacity.jwdnd.course1.cloudstorage.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CredentialTests {

	private static final Integer TEST_ID_1 = 99;
	private static final String TEST_URL_1 = "http://testurl1.com";
	private static final String TEST_USERNAME_1 = "testuser1";
	private static final String TEST_KEY_1 = "key 1";
	private static final String TEST_PASSWORD_1 = "password 1";
	private static final Integer TEST_USERID_1 = 199;
	private static final Integer TEST_ID_2 = 100;
	private static final String TEST_URL_2 = "http://testurl2.com";
	private static final String TEST_USERNAME_2 = "testuser2";
	private static final String TEST_KEY_2 = "key 2";
	private static final String TEST_PASSWORD_2 = "password 2";
	private static final Integer TEST_USERID_2 = 200;
	private Credential credential;

	@BeforeEach
	public void beforeEach() {
		credential = getTestCredential_1();
	}
	
	@Test
	public void canCreateCredential() {
		assertNotNull(credential);
		assertEquals(TEST_ID_1, credential.getId());
		assertEquals(TEST_URL_1, credential.getUrl());
		assertEquals(TEST_USERNAME_1, credential.getUsername());
		assertEquals(TEST_KEY_1, credential.getKey());
		assertEquals(TEST_PASSWORD_1, credential.getPassword());
		assertEquals(TEST_USERID_1, credential.getUserId());
	}
	
	@Test
	public void canSetAttributes() {
		credential.setId(TEST_ID_2);
		assertEquals(TEST_ID_2, credential.getId());
		credential.setUrl(TEST_URL_2);
		assertEquals(TEST_URL_2, credential.getUrl());
		credential.setUsername(TEST_USERNAME_2);
		assertEquals(TEST_USERNAME_2, credential.getUsername());
		credential.setKey(TEST_KEY_2);
		assertEquals(TEST_KEY_2, credential.getKey());
		credential.setPassword(TEST_PASSWORD_2);
		assertEquals(TEST_PASSWORD_2, credential.getPassword());
		credential.setUserId(TEST_USERID_2);
		assertEquals(TEST_USERID_2, credential.getUserId());
	}
	
	@Test
	public void canVerifyEquals() {
		Credential duplicateCredential = getTestCredential_1();
		assertEquals(credential, duplicateCredential);
		
	}

	private Credential getTestCredential_1() {
		return new Credential(TEST_ID_1, TEST_URL_1, TEST_USERNAME_1, TEST_KEY_1, TEST_PASSWORD_1, TEST_USERID_1);
	}
}
