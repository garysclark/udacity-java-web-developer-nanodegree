package com.udacity.jwdnd.course1.cloudstorage.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.UserTests;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
public class UserServiceTests {

	@Autowired
	private UserService userService;
	@Autowired
	private HashService hashService;

	@Test
	public void canAccessServices() {
		assertNotNull(userService);
		assertNotNull(hashService);
	}

	@Test
	public void canCreateAndRetrieveUser() {
		User user = UserTests.getTestUser();

		assertTrue(userService.isUserNameAvailable(user.getUsername()));
		assertTrue(userService.createUser(user) > 0);
		User newUser = userService.getUser(user.getUsername());
		assertNotNull(newUser);
		assertFalse(userService.isUserNameAvailable(user.getUsername()));

		assertNotEquals(user.getUserId(), newUser.getUserId());
		assertNotEquals(user.getSalt(), newUser.getSalt());
		assertNotEquals(user.getPassword(), newUser.getPassword());
		assertEquals(user.getUsername(), newUser.getUsername());
		assertEquals(user.getFirstName(), newUser.getFirstName());
		assertEquals(user.getLastName(), newUser.getLastName());
		
		// check for no side effect change
		assertEquals(user, UserTests.getTestUser());
	}
}
