package com.byrneclark.garydata.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.byrneclark.garydata.entity.Plant;

@SpringBootTest
public class PlantServiceTests {

	private static final String TEST_NAME = "test name";
	private static final BigDecimal TEST_PRICE = new BigDecimal("9.99");
	private static final Long TEST_DELIVERED_PLANT_ID = 1l;
	private static final BigDecimal TEST_MAX_PRICE = new BigDecimal("9.99");
	private static final int TEST_NUM_PLANTS_LESS_THAN_PRICE = 1;
	private static final String TEST_SAVED_NAME = "lily";
	@Autowired
	private PlantService plantService;

	@Test
	public void canAccessService() {
		assertNotNull(plantService);
	}
	
	@Test
	public void canSaveNewPlant() {
		Plant plant = new Plant();
		plant.setName(TEST_NAME);
		plant.setPrice(TEST_PRICE);
		Long savedPlantId = plantService.save(plant);
		assertNotNull(savedPlantId);
	}
	
	@Test
	public void canCheckIfPlantHasBeenDelivered() {
		Boolean isDelivered = plantService.isPlantDelivered(TEST_DELIVERED_PLANT_ID);
		assertTrue(isDelivered);
	}
	
	@Test
	public void canFindPlantsLessThanPrice() {
		List<Plant> plants = plantService.getPlantsLessThanPrice(TEST_MAX_PRICE);
		assertNotNull(plants);
		assertEquals(TEST_NUM_PLANTS_LESS_THAN_PRICE, plants.size());
	}
	
	@Test
	public void canGetPlantByName() {
		Plant plant = plantService.getPlantByName(TEST_SAVED_NAME);
		assertNotNull(plant);
		assertEquals(TEST_SAVED_NAME, plant.getName());
	}
}
