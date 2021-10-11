package com.udacity.jwdnd.course1.cloudstorage.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTests {

	private static final Integer TEST_ID = 99;
	private static final String TEST_USERNAME = "username";
	private static final String TEST_SALT = "salt";
	private static final String TEST_PASSWORD = "password";
	private static final String TEST_FIRST_NAME = "FirstName";
	private static final String TEST_LAST_NAME = "LastName";

	private static final Integer TEST_ID_2 = 100;
	private static final String TEST_USERNAME_2 = "username2";
	private static final String TEST_SALT_2 = "salt2";
	private static final String TEST_PASSWORD_2 = "password2";
	private static final String TEST_FIRST_NAME_2 = "FirstName2";
	private static final String TEST_LAST_NAME_2 = "LastName2";

	private Integer userId;
	private String username;
	private String encodedSalt;
	private String hashedPassword;
	private String firstName;
	private String lastName;
	private User user;

	@BeforeEach
	public void beforeEach() {
		userId = TEST_ID;
		username = TEST_USERNAME;
		encodedSalt = TEST_SALT;
		hashedPassword= TEST_PASSWORD;
		firstName = TEST_FIRST_NAME;
		lastName= TEST_LAST_NAME;
		user = new User(userId, username, encodedSalt, hashedPassword, firstName, lastName);
	}
	
	@Test
	public void canCreateUser() {
		assertNotNull(user);
		assertEquals(user.getUserId(), TEST_ID);
		assertEquals(user.getUsername(), TEST_USERNAME);
		assertEquals(user.getSalt(), TEST_SALT);
		assertEquals(user.getPassword(), TEST_PASSWORD);
		assertEquals(user.getFirstName(), TEST_FIRST_NAME);
		assertEquals(user.getLastName(), TEST_LAST_NAME);
	}
	
	@Test
	public void canSetAttributes() {
		user.setUserId(TEST_ID_2);
		assertEquals(user.getUserId(), TEST_ID_2);
		user.setUsername(TEST_USERNAME_2);
		assertEquals(user.getUsername(), TEST_USERNAME_2);
		user.setSalt(TEST_SALT_2);
		assertEquals(user.getSalt(), TEST_SALT_2);
		user.setPassword(TEST_PASSWORD_2);
		assertEquals(user.getPassword(), TEST_PASSWORD_2);
		user.setFirstName(TEST_FIRST_NAME_2);
		assertEquals(user.getFirstName(), TEST_FIRST_NAME_2);
		user.setLastName(TEST_LAST_NAME_2);
		assertEquals(user.getLastName(), TEST_LAST_NAME_2);
	}
	
	@Test
	public void canVerifyEquality() {
		User user1 =  getTestUser_1();
		User user2 =  getTestUser_1();
		assertEquals(user1, user2);
	}

	public static User getTestUser_1() {
		return new User(TEST_ID, TEST_USERNAME, TEST_SALT, TEST_PASSWORD, TEST_FIRST_NAME, TEST_LAST_NAME);
	}

	public static User getTestUser_2() {
		return new User(TEST_ID_2, TEST_USERNAME_2, TEST_SALT_2, TEST_PASSWORD_2, TEST_FIRST_NAME_2, TEST_LAST_NAME_2);
	}
}
