package com.byrneclark.garydata.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class CandyDataTests {

	private static final Long TEST_ID_1 = 99l;
	private static final String TEST_NAME_1 = "candy 1";
	private static final BigDecimal TEST_PRICE_1 = new BigDecimal("9.99");
	private static final Long TEST_ID_2 = 100L;
	private static final String TEST_NAME_2 = "candy 2";
	private static final BigDecimal TEST_PRICE_2 = new BigDecimal("5.99");

	@Test
	public void canCreate() {
		CandyData candyData = new CandyData();
		assertNotNull(candyData);
	}

	@Test
	public void canCreateWithAttributes() {
		CandyData candyData = getTestCandyData_1();
		assertNotNull(candyData);
		assertEquals(TEST_ID_1, candyData.getId());
		assertEquals(TEST_NAME_1, candyData.getName());
		assertEquals(TEST_PRICE_1, candyData.getPrice());
	}
	
	@Test
	public void canSetAttributes() {
		CandyData candyData = getTestCandyData_1();
		candyData.setId(TEST_ID_2);
		assertEquals(TEST_ID_2, candyData.getId());
		candyData.setName(TEST_NAME_2);
		assertEquals(TEST_NAME_2, candyData.getName());
		candyData.setPrice(TEST_PRICE_2);
		assertEquals(TEST_PRICE_2, candyData.getPrice());
	}
	
	@Test
	public void canVerifyEquals() {
		CandyData candyData1 = getTestCandyData_1();
		CandyData candyData2 = getTestCandyData_1();
		assertEquals(candyData1, candyData2);
	}

	public static CandyData getTestCandyData_1() {
		return new CandyData(TEST_ID_1, TEST_NAME_1, TEST_PRICE_1);
	}
}
