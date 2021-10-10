package com.udacity.jwdnd.course1.cloudstorage.model;

import java.util.Objects;

public class User {

	private Integer userId;
	private String username;
	private String salt;
	private String password;
	private String firstName;
	private String lastName;

	public User(Integer userId, String username, String encodedSalt, String hashedPassword, String firstName,
			String lastName) {
		this.userId = userId;
		this.username = username;
		this.salt = encodedSalt;
		this.password = hashedPassword;
		this.firstName = firstName;
		this.lastName = lastName;
	}


	public Integer getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public String getSalt() {
		return salt;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	@Override
	public int hashCode() {
		return Objects.hash(firstName, lastName, password, salt, userId, username);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(password, other.password) && Objects.equals(salt, other.salt)
				&& Objects.equals(userId, other.userId) && Objects.equals(username, other.username);
	}

}
