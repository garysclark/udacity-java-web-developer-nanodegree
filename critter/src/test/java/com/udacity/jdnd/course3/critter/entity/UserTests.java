package com.udacity.jdnd.course3.critter.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

import org.junit.jupiter.api.Test;

public class UserTests {

	private static final Long TEST_ID = 99l;
	private static final String TEST_NAME = "test name";
	
	@Test
	public void canCreateUser() {
		User user = getDefaultUser();
		assertNotNull(user);
	}
	
	@Test
	public void canCreateUserWithAttributes() {
		User user = mock(
				User.class, 
				withSettings()
				.useConstructor(TEST_ID, TEST_NAME)
				.defaultAnswer(CALLS_REAL_METHODS)
				);
		assertNotNull(user);
		validateAttributes(user);
	}
	
	@Test
	public void canSetGetAttributes() {
		User user = getDefaultUser();
		user.setId(TEST_ID);
		user.setName(TEST_NAME);
		validateAttributes(user);
	}

	private void validateAttributes(User user) {
		assertEquals(TEST_ID, user.getId());
		assertEquals(TEST_NAME, user.getName());
	}

	private User getDefaultUser() {
		return mock(
				User.class, 
				withSettings()
				.useConstructor()
				.defaultAnswer(CALLS_REAL_METHODS)
				);
	}
}
