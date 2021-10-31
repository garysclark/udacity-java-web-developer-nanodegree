package com.udacity.jwdnd.course1.cloudstorage.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialTests;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.UserTests;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
public class CredentialMapperTests {

	private static final String TEST_SUFFIX = " - TEST";
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private CredentialMapper credentialMapper;
	private Credential credential;
	private Integer rowsAdded;

	@BeforeEach
	public void beforeEach() {
		User testUser1 = UserTests.getTestUser_1();
		userMapper.create(testUser1);

		credential = CredentialTests.getTestCredential_1();
		credential.setUserId(testUser1.getUserId());
		credential.setUsername(testUser1.getUsername());

		rowsAdded = credentialMapper.create(credential);
	}

	@Test
	public void canAccessMappers() {
		assertNotNull(userMapper);
		assertNotNull(credentialMapper);
	}

	@Test
	public void canCreate() {
		assertEquals(1, rowsAdded);
	}

	@Test
	public void canFindById() {
		Credential storedCredential = credentialMapper.findById(credential.getId());
		assertEquals(credential, storedCredential);
	}
	
	@Test
	public void canFindByUserId() {
		Credential secondCredential = CredentialTests.getTestCredential_2();
		secondCredential.setUserId(credential.getUserId());
		secondCredential.setUsername(credential.getUsername());
		credentialMapper.create(secondCredential);
		
		List<Credential> storedCredentials = credentialMapper.findByUserId(credential.getUserId());
		assertEquals(2, storedCredentials.size());
		assertEquals(credential, storedCredentials.get(0));
		assertEquals(secondCredential, storedCredentials.get(1));
	}

	@Test
	public void canUpdate() {
		Credential beforeCredential = credentialMapper.findById(credential.getId());

		credential.setKey(credential.getKey() + TEST_SUFFIX);
		credential.setPassword(credential.getPassword() + TEST_SUFFIX);
		credential.setUrl(credential.getUrl() + TEST_SUFFIX);

		User testUser2 = UserTests.getTestUser_2();
		userMapper.create(testUser2);

		credential.setUserId(testUser2.getUserId());
		credential.setUsername(testUser2.getUsername());
		
		int rowsUpdated = credentialMapper.update(credential);
		assertEquals(1, rowsUpdated);
		Credential afterCredential = credentialMapper.findById(credential.getId());
		assertEquals(credential, afterCredential);
		assertEquals(beforeCredential.getId(), afterCredential.getId());
		assertNotEquals(beforeCredential, afterCredential);
	}
	
	@Test
	public void canDelete() {
		int rowsDeleted = credentialMapper.delete(credential);
		assertEquals(1, rowsDeleted);
		assertNull(credentialMapper.findById(credential.getId()));
	}
	
	@Test
	public void canDetectUpdateError() {
		Integer rowsAffected = credentialMapper.update(CredentialTests.getTestCredential_2());
		assertEquals(0, rowsAffected);
	}
	
	@Test
	public void canDetectDeleteError() {
		Integer rowsAffected = credentialMapper.delete(CredentialTests.getTestCredential_2());
		assertEquals(0, rowsAffected);
	}
}
