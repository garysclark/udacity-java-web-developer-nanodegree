package com.udacity.jwdnd.course1.cloudstorage.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
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
public class UserMapperTests {

	
	@Autowired
	private UserMapper userMapper;

	private User user;
	private Integer rowNum;
	
	@BeforeEach
	public void beforeEach() {
		user = UserTests.getTestUser_1();
		rowNum = userMapper.create(user);
	}
	
	@Test
	public void canAccessMapper() {
		assertNotNull(userMapper);
	}
	
	@Test
	public void canCreateUser() {
		assertTrue(rowNum > 0);
	}
	
	@Test
	public void canFindByUserId() {
		User newUser = userMapper.findById(user.getUserId());
		assertNotNull(newUser);
		assertEquals(user, newUser);
	}
	
	@Test
	public void canFindUserByUsername() {
		User newUser = userMapper.findByUsername(user.getUsername());
		assertNotNull(newUser);
		assertEquals(user, newUser);
	}
	
	@Test
	public void canUpdateUser() {
		User user2 = UserTests.getTestUser_2();
		userMapper.update(user.getUserId(), user2.getFirstName(), user2.getLastName(), user2.getPassword());
		User updatedUser = userMapper.findById(user.getUserId());
		assertEquals(user2.getFirstName(), updatedUser.getFirstName());
		assertEquals(user2.getLastName(), updatedUser.getLastName());
		assertEquals(user2.getPassword(), updatedUser.getPassword());
	}
	
	@Test
	public void canDeleteUser() {
		User userToDelete = userMapper.findByUsername(user.getUsername());
		userMapper.delete(userToDelete);
		assertNull(userMapper.findById(userToDelete.getUserId()));
	}
	
	@Test
	public void canDetectUpdateError() {
		User user2 = UserTests.getTestUser_2();
		Integer rowsAffected = userMapper.update(user2.getUserId(), user2.getFirstName(), user2.getLastName(), user2.getPassword());
		assertEquals(0, rowsAffected);
	}
	
	@Test
	public void canDetectDeleteError() {
		User user2 = UserTests.getTestUser_2();
		Integer rowsAffected = userMapper.delete(user2);
		assertEquals(0, rowsAffected);
	}
	
}
