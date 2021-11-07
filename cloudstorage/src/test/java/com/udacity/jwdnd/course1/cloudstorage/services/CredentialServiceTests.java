package com.udacity.jwdnd.course1.cloudstorage.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialTests;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.UserTests;

public class CredentialServiceTests {

	@Mock
	private CredentialMapper credentialMapper;
	@Mock
	private List<Credential> credentials;

	private CredentialService credentialService;
	private Credential credential;

	@BeforeEach
	public void beforeEach() {
		MockitoAnnotations.initMocks(this);
		credentialService = new CredentialService(credentialMapper);
		credential = CredentialTests.getTestCredential_1();
	}
	
	@Test
	public void canCreateService() {
		assertNotNull(credentialService);
	}
	
	@Test
	public void canCreateCredential() {
		Mockito.when(credentialMapper.create(credential)).thenReturn(1);
		
		int rowsAdded = credentialService.createCredential(credential);
		
		assertEquals(1, rowsAdded);
	}
	
	@Test
	public void canGetCredentialsByUserId() {
		User user = UserTests.getTestUser_1();
		Mockito.when(credentialMapper.findByUserId(user.getUserId())).thenReturn(credentials);
		
		List<Credential> foundCredentials = credentialService.getCredentialsByUserId(user.getUserId());
		
		assertEquals(credentials, foundCredentials);
	}
	
	@Test
	public void canGetCredentialById() {
		Mockito.when(credentialMapper.findById(credential.getId())).thenReturn(credential);
		
		Credential foundCredential = credentialService.getCredentialById(credential.getId());
		
		assertEquals(credential, foundCredential);
	}
	
	@Test
	public void canUpdateCredential() {
		Mockito.when(credentialMapper.update(credential)).thenReturn(1);
		
		int rowsUpdated = credentialService.updateCredential(credential);
		
		assertEquals(1, rowsUpdated);

	}
}
