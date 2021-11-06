package com.udacity.jwdnd.course1.cloudstorage.services;

import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;

@Service
public class UserService {
	
	private UserMapper userMapper;
	private HashService hashService;
	private EncryptionService encryptionService;

	public UserService(UserMapper userMapper, HashService hashService, EncryptionService encryptionService) {
		this.userMapper = userMapper;
		this.hashService = hashService;
		this.encryptionService = encryptionService;
	}

	public boolean isUserNameAvailable(String username) {
		return userMapper.findByUsername(username) == null;
	}

	public Integer createUser(User user) {
		String encodedSalt = encryptionService.generateKey();
		String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
		return userMapper.create(new User(null, user.getUsername(), encodedSalt, 
				hashedPassword, user.getFirstName(), user.getLastName()));
	}

	public User getUser(String username) {
		return userMapper.findByUsername(username);
	}

}
