package com.byrneclark.garydata.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShrubTests {

	private static final Integer TEST_HEIGHT = 10;
	private static final Integer TEST_WIDTH = 7;
	private Shrub shrub;
	@BeforeEach
	public void beforeEach() {
		shrub = new Shrub();
	}
	
	@Test
	public void canCreateShrub() {
		assertNotNull(shrub);
	}
//	
//	@Test
//	public void canValidatePlant() {
//		PlantTests tests = new PlantTests(shrub);
//		tests.validatePlant();
//	}
	
	@Test
	public void canSetGetHeight() {
		shrub.setHeight(TEST_HEIGHT);
		assertEquals(TEST_HEIGHT, shrub.getHeight());
	}
	
	@Test
	public void canSetGetWidth() {
		shrub.setWidth(TEST_WIDTH);
		assertEquals(TEST_WIDTH, shrub.getWidth());
	}
}
