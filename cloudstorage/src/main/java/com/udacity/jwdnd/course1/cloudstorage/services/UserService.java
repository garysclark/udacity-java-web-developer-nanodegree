package com.udacity.jwdnd.course1.cloudstorage.services;

import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.utilities.SaltUtility;

@Service
public class UserService {
	
	private UserMapper userMapper;
	private HashService hashService;

	public UserService(UserMapper userMapper, HashService hashService) {
		this.userMapper = userMapper;
		this.hashService = hashService;
	}

	public boolean isUserNameAvailable(String username) {
		return userMapper.findByUsername(username) == null;
	}

	public Integer createUser(User user) {
		String encodedSalt = SaltUtility.getEncodedSalt();
		String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
		return userMapper.create(new User(null, user.getUsername(), encodedSalt, 
				hashedPassword, user.getFirstName(), user.getLastName()));
	}

	public User getUser(String username) {
		return userMapper.findByUsername(username);
	}

}
