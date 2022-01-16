package com.byrneclark.garydata.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class PlantTests {
	
	static final Long TEST_ID = 99l;
	static final String TEST_NAME = "name 1";
	static final BigDecimal TEST_PRICE = new BigDecimal("9999.99");
	private static final Delivery TEST_DELIVERY = new Delivery();

	@Test
	public void canSetGetAttributes() {
		Plant plant = Mockito.mock(Plant.class, Mockito.CALLS_REAL_METHODS);
		
		plant.setId(TEST_ID);
		assertEquals(TEST_ID, plant.getId());
		
		plant.setName(TEST_NAME);
		assertEquals(TEST_NAME, plant.getName());

		plant.setPrice(TEST_PRICE);
		assertEquals(TEST_PRICE, plant.getPrice());
		
		plant.setDelivey(TEST_DELIVERY);
		assertEquals(TEST_DELIVERY, plant.getDelivery());
	}

}
