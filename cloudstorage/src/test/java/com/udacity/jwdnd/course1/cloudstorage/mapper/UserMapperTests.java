package com.udacity.jwdnd.course1.cloudstorage.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
//		user.setLastName(user2.getLastName());
//		user.setPassword(user2.getPassword());
//		user.setSalt(user2.getSalt());
		userMapper.updateFirstName(user.getUserId(), user2.getFirstName());
		User newUser = userMapper.findById(user.getUserId());
		assertEquals(user2.getFirstName(), newUser.getFirstName());
	}
	
}
