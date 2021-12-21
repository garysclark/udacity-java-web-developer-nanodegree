package com.udacity.pricing.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class PriceTests {

	private static final String TEST_CURRENCY = "test currency";
	private static final BigDecimal TEST_PRICE = new BigDecimal("9999.99");
	private static final Long TEST_VEHICLEID = 99l;
	private static final String TEST_CURRENCY_2 = "test currency 2";
	private static final BigDecimal TEST_PRICE_2 = new BigDecimal("8888.88");
	private static final Long TEST_VEHICLEID_2 = 100l;
	private static final Long TEST_ID = 89l;
	private static final Long TEST_ID_2 = 90l;

	@Test
	public void canCreatePriceWithoutAttributes() {
		Price price = new Price();
		assertNotNull(price);
	}
	
	@Test
	public void canCreatePriceWithAttributes() {
		Price price = getTestPrice_1();
		assertEquals(TEST_ID, price.getId());
		assertEquals(TEST_CURRENCY, price.getCurrency());
		assertEquals(TEST_PRICE, price.getPrice());
		assertEquals(TEST_VEHICLEID, price.getVehicleId());
	}
	
	@Test
	public void canSetAttributes() {
		Price price = getTestPrice_1();
		price.setId(TEST_ID_2);
		assertEquals(TEST_ID_2, price.getId());
		price.setCurrency(TEST_CURRENCY_2);
		assertEquals(TEST_CURRENCY_2, price.getCurrency());
		price.setPrice(TEST_PRICE_2);
		assertEquals(TEST_PRICE_2, price.getPrice());
		price.setVehicleId(TEST_VEHICLEID_2);
		assertEquals(TEST_VEHICLEID_2, price.getVehicleId());
	}
	
	@Test
	public void canVerifyEquality() {
		Price price1 = getTestPrice_1();
		Price price2 = getTestPrice_1();
		assertEquals(price1, price2);
	}
	
	@Test
	public void canVerifyInequality() {
		Price price1 = getTestPrice_1();
		Price price2 = getTestPrice_2();
		assertNotEquals(price1, price2);
	}

	public static Price getTestPrice_2() {
		return new Price(TEST_ID_2, TEST_CURRENCY_2, TEST_PRICE_2, TEST_VEHICLEID_2);
	}

	public static Price getTestPrice_1() {
		return new Price(TEST_ID, TEST_CURRENCY, TEST_PRICE, TEST_VEHICLEID);
	}
}
