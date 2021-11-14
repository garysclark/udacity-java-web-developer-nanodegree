package com.udacity.jwdnd.course1.cloudstorage.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;

@Service
public class CredentialService {

	private CredentialMapper credentialMapper;
	
	public CredentialService(CredentialMapper credentialMapper) {
		this.credentialMapper = credentialMapper;
	}

	public Integer createCredential(Credential credential) {
		return credentialMapper.create(credential);
	}

	public List<Credential> getCredentialsByUserId(Integer userId) {
		return credentialMapper.findByUserId(userId);
	}

	public Credential getCredentialById(Integer id) {
		return credentialMapper.findById(id);
	}

	public Integer updateCredential(Credential credential) {
		return credentialMapper.update(credential);
	}

	public Integer deleteCredential(Credential credential) {
		return credentialMapper.delete(credential);
	}

}
