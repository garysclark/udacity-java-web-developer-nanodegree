package com.byrneclark.garydata.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class RecipientAndPriceTests {

	private static final String TEST_NAME = "name";
	private static final BigDecimal TEST_PRICE = new BigDecimal("9.99");

	@Test
	public void canCreateWithoutAttributes() {
		RecipientAndPrice recipientAndPrice = new RecipientAndPrice();
		assertNotNull(recipientAndPrice);
	}

	@Test
	public void canCreateWithAttributes() {
		RecipientAndPrice recipientAndPrice = new RecipientAndPrice(TEST_NAME, TEST_PRICE);
		assertEquals(TEST_NAME, recipientAndPrice.getName());
		assertEquals(TEST_PRICE, recipientAndPrice.getPrice());
	}

	@Test
	public void canSetAttributes() {
		RecipientAndPrice recipientAndPrice = new RecipientAndPrice();
		recipientAndPrice.setName(TEST_NAME);
		assertEquals(TEST_NAME, recipientAndPrice.getName());
		recipientAndPrice.setPrice(TEST_PRICE);
		assertEquals(TEST_PRICE, recipientAndPrice.getPrice());
	}
}
