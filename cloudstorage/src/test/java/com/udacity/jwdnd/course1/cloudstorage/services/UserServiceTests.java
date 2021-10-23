package com.udacity.jwdnd.course1.cloudstorage.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.UserTests;

public class UserServiceTests {

	private static final String TEST_ENCRYPTED_PASSWORD = "Encrypted Password";
	@Mock
	private UserMapper userMapper;
	@Mock
	private HashService hashService;
	@Captor
	private ArgumentCaptor<User> userCaptor;
	@Captor
	private ArgumentCaptor<String> passwordCaptor;
	@Captor
	private ArgumentCaptor<String> saltCaptor;

	private UserService userService;
	private User user;

	@BeforeEach
	public void beforeEach() {
		MockitoAnnotations.initMocks(this);
		userService = new UserService(userMapper, hashService);
		user = UserTests.getTestUser_1();
	}
	
	@Test
	public void canCreateUser() {
		Mockito.when(userMapper.create(Mockito.any(User.class))).thenReturn(1);
		Mockito.when(hashService.getHashedValue(Mockito.anyString(), Mockito.anyString())).thenReturn(TEST_ENCRYPTED_PASSWORD);
		
		int rowsAffected = userService.createUser(user);
		
		assertEquals(1, rowsAffected);
		Mockito.verify(userMapper).create(userCaptor.capture());
		Mockito.verify(hashService).getHashedValue(passwordCaptor.capture(), saltCaptor.capture());
		User createdUser = userCaptor.getValue();
		assertNull(createdUser.getUserId());
		assertEquals(user.getFirstName(), createdUser.getFirstName());
		assertEquals(user.getLastName(), createdUser.getLastName());
		assertEquals(user.getUsername(), createdUser.getUsername());
		assertEquals(TEST_ENCRYPTED_PASSWORD, createdUser.getPassword());
		assertEquals(saltCaptor.getValue(), createdUser.getSalt());
		
		assertEquals(user.getPassword(), passwordCaptor.getValue());
	}
	
	@Test
	public void canHandleCreateUserError() {
		Mockito.when(userMapper.create(Mockito.any(User.class))).thenReturn(0);
		
		int rowsAffected = userService.createUser(user);
		
		assertEquals(0, rowsAffected);
	}
	
	@Test
	public void canGetUser() {
		Mockito.when(userMapper.findByUsername(user.getUsername())).thenReturn(user);
		
		User foundUser = userService.getUser(user.getUsername());
		
		assertEquals(user, foundUser);
	}
	
	@Test
	public void canHandleUserNotFound() {
		Mockito.when(userMapper.findByUsername(user.getUsername())).thenReturn(null);
		
		User foundUser = userService.getUser(user.getUsername());
		
		assertNull(foundUser);
	}

	@Test
	public void canVerifyUsernameIsAvailable() {
		Mockito.when(userMapper.findByUsername(user.getUsername())).thenReturn(null);
		assertTrue(userService.isUserNameAvailable(user.getUsername()));
	}
	
	@Test
	public void canVerifyUsernameIsNotAvailable() {
		Mockito.when(userMapper.findByUsername(user.getUsername())).thenReturn(user);
		assertFalse(userService.isUserNameAvailable(user.getUsername()));
	}
}
