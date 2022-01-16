package com.byrneclark.garydata.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeliveryTests {
	
	private static final String TEST_NAME = "name 1";
	private static final Long TEST_ID = 99l;
	private static final String TEST_ADDRESS = "6154 Monteverde Drive, San Jose, CA 95120";
	private static final LocalDateTime TEST_DELIVERY_DATE_TIME = LocalDateTime.of(2022, 1, 6, 16, 30);
	private static final boolean TEST_DELIVERY_IS_COMPLETE = true;
	private static final List<Plant> TEST_PLANTS = new ArrayList<Plant>();
	private static final boolean TEST_DELIVERY_IS_NOT_COMPLETE = false;

	private Delivery delivery;

	@BeforeEach
	public void beforeEach() {
		delivery = new Delivery();
	}
	
	@Test
	public void canCreateDelivery() {
		assertNotNull(delivery);
	}
	
	@Test
	public void canSetGetId() {
		delivery.setId(TEST_ID);
		assertEquals(TEST_ID, delivery.getId());
	}
	
	@Test
	public void canSetGetName() {
		delivery.setName(TEST_NAME);
		assertEquals(TEST_NAME, delivery.getName());
	}
	
	@Test
	public void canSetGetAddress() {
		delivery.setAddress(TEST_ADDRESS);
		assertEquals(TEST_ADDRESS, delivery.getAddress());
	}
	
	@Test
	public void canSetGetDeliveryDateTime() {
		delivery.setDeliveryTime(TEST_DELIVERY_DATE_TIME);
		assertEquals(TEST_DELIVERY_DATE_TIME, delivery.getDeliveryTime());
	}
	
	@Test
	public void canSetGetIsComplete() {
		delivery.setIsComplete(TEST_DELIVERY_IS_COMPLETE);
		assertEquals(TEST_DELIVERY_IS_COMPLETE, delivery.getIsComplete());
	}
	
	@Test
	public void canSetGetPlants() {
		delivery.setPlants(TEST_PLANTS);
		assertEquals(TEST_PLANTS, delivery.getPlants());
	}

	public static Delivery getTestDelivery() {
		Delivery delivery = new Delivery();
		delivery.setId(TEST_ID);
		delivery.setAddress(TEST_ADDRESS);
		delivery.setName(TEST_NAME);
		delivery.setDeliveryTime(TEST_DELIVERY_DATE_TIME);
		delivery.setIsComplete(TEST_DELIVERY_IS_NOT_COMPLETE);
		delivery.setPlants(TEST_PLANTS);
		return delivery;
	}

	public static void validatePropertiesAreEqual(Delivery delivery1, Delivery delivery2) {
		assertEquals(delivery1.getAddress(),delivery2.getAddress());
		assertEquals(delivery1.getIsComplete(),delivery2.getIsComplete());
		assertEquals(delivery1.getDeliveryTime(),delivery2.getDeliveryTime());
		assertEquals(delivery1.getName(),delivery2.getName());
		assertEquals(delivery1.getPlants(),delivery2.getPlants());
	}
}
