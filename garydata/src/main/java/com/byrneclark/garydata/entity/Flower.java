package com.byrneclark.garydata.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;

@Entity
public class Flower extends Plant{

	private String color;

	public Flower(Long id, String name, BigDecimal price, String color) {
		super(id, name, price);
		this.color = color;
	}

	public Flower() {
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getColor() {
		return color;
	}

}
