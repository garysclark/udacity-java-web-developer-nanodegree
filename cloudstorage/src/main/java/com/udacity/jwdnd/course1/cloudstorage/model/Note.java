package com.udacity.jwdnd.course1.cloudstorage.model;

import java.util.Objects;

public class Note {

	private Integer noteid;
	private String notetitle;
	private String notedescription;
	private Integer userid;

	public Note(Integer noteid, String notetitle, String notedescription, Integer userid) {
		this.noteid = noteid;
		this.notetitle = notetitle;
		this.notedescription = notedescription;
		this.userid = userid;
	}

	public Integer getNoteid() {
		return noteid;
	}

	public String getNotetitle() {
		return notetitle;
	}

	public String getNotedescription() {
		return notedescription;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setNoteid(Integer noteid) {
		this.noteid = noteid;
	}

	public void setNotetitle(String notetitle) {
		this.notetitle = notetitle;
	}

	public void setNotedescription(String notedescription) {
		this.notedescription = notedescription;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Override
	public int hashCode() {
		return Objects.hash(notedescription, noteid, notetitle, userid);
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
		return Objects.equals(notedescription, other.notedescription) && Objects.equals(noteid, other.noteid)
				&& Objects.equals(notetitle, other.notetitle) && Objects.equals(userid, other.userid);
	}

}
