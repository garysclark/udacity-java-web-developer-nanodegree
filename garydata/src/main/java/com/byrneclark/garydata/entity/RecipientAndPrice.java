package com.byrneclark.garydata.entity;

import java.math.BigDecimal;

public class RecipientAndPrice {

	private String name;
	private BigDecimal price;

	public RecipientAndPrice(String name, BigDecimal price) {
		this.name = name;
		this.price = price;
	}

	public RecipientAndPrice() {
	}

	public String getName() {
		return name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
