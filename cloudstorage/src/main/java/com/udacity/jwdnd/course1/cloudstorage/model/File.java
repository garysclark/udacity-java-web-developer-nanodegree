package com.udacity.jwdnd.course1.cloudstorage.model;

import java.util.Arrays;
import java.util.Objects;

public class File {

	private Integer id;
	private String name;
	private String contentType;
	private String size;
	private Integer userId;
	private byte[] data;

	public File(Integer id, String name, String contentType, String size, Integer userId, byte[] data) {
		this.id = id;
		this.name = name;
		this.contentType = contentType;
		this.size = size;
		this.userId = userId;
		this.data = data;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getContentType() {
		return contentType;
	}

	public String getSize() {
		return size;
	}

	public Integer getUserId() {
		return userId;
	}

	public byte[] getData() {
		return data;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(data);
		result = prime * result + Objects.hash(contentType, id, name, size, userId);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		File other = (File) obj;
		return Objects.equals(contentType, other.contentType) && Arrays.equals(data, other.data)
				&& Objects.equals(id, other.id) && Objects.equals(name, other.name) && Objects.equals(size, other.size)
				&& Objects.equals(userId, other.userId);
	}

}
