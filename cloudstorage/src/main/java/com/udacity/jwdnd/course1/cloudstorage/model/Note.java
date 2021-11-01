package com.udacity.jwdnd.course1.cloudstorage.model;

import java.util.Objects;

public class Note {

	private Integer id;
	private String title;
	private String description;
	private Integer userid;

	public Note(Integer id, String title, String description, Integer userid) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.userid = userid;
	}

	public Integer getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setId(Integer noteid) {
		this.id = noteid;
	}

	public void setTitle(String notetitle) {
		this.title = notetitle;
	}

	public void setDescription(String notedescription) {
		this.description = notedescription;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, id, title, userid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Note other = (Note) obj;
		return Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& Objects.equals(title, other.title) && Objects.equals(userid, other.userid);
	}

}
