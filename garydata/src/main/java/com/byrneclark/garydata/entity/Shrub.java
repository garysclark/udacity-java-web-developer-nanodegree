package com.byrneclark.garydata.entity;

import javax.persistence.Entity;

@Entity
public class Shrub extends Plant{

	private Integer height;
	private Integer width;

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getHeight() {
		return height;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getWidth() {
		return width;
	}

}
