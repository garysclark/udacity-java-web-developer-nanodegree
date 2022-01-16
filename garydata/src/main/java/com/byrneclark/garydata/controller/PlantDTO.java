package com.byrneclark.garydata.controller;

import java.math.BigDecimal;

public class PlantDTO {

	private String name;
	private BigDecimal price;
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getPrice() {
		return price;
	}

}
