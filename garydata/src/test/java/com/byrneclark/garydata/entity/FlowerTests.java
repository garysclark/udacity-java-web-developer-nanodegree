package com.byrneclark.garydata.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FlowerTests {

	private static final String TEST_COLOR = "color 1";
	private Flower flower;

	@BeforeEach
	public void beforeEach() {
		flower = new Flower();
	}

	@Test
	public void canCreateFlower() {
		assertNotNull(flower);
	}

	@Test
	public void canSetGetColor() {
		flower.setColor(TEST_COLOR);
		assertEquals(TEST_COLOR, flower.getColor());
	}
	
	@Test
	public void canCreateFlowerWithAttributes() {
		Flower flower = getTestFlower();
		assertEquals(PlantTests.TEST_ID, flower.getId());
		assertEquals(PlantTests.TEST_NAME, flower.getName());
		assertEquals(PlantTests.TEST_PRICE, flower.getPrice());
		assertEquals(TEST_COLOR, flower.getColor());
	}

	public static Flower getTestFlower() {
		return new Flower(PlantTests.TEST_ID, PlantTests.TEST_NAME, PlantTests.TEST_PRICE, TEST_COLOR);
	}
}
