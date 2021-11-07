package com.udacity.jwdnd.course1.cloudstorage.model;

import java.util.Objects;

public class Credential {

	private Integer id;
	private String url;
	private String username;
	private String key;
	private String password;
	private Integer userid;

	public Credential(Integer id, String url, String username, String key, String password, Integer userid) {
		this.id = id;
		this.url = url;
		this.username = username;
		this.key = key;
		this.password = password;
		this.userid = userid;
	}

	public Integer getId() {
		return id;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getKey() {
		return key;
	}

	public String getPassword() {
		return password;
	}

	public Integer getUserId() {
		return userid;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserId(Integer userid) {
		this.userid = userid;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, key, password, url, userid, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Credential other = (Credential) obj;
		return Objects.equals(id, other.id) && Objects.equals(key, other.key)
				&& Objects.equals(password, other.password) && Objects.equals(url, other.url)
				&& Objects.equals(userid, other.userid) && Objects.equals(username, other.username);
	}

}
