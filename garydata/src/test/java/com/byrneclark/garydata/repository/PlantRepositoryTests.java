package com.byrneclark.garydata.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.byrneclark.garydata.entity.Plant;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
public class PlantRepositoryTests {

	private static final int TEST_NUM_PLANTS = 3;
	private static final BigDecimal TEST_MAX_PRICE = new BigDecimal("9.99");
	@Autowired
	private PlantRepository plantRepository;

	@Test
	public void canAccessRepository() {
		assertNotNull(plantRepository);
	}
	
	@Test
	public void canFindAllPlants() {
		List<Plant> plants = plantRepository.findAll();
		assertEquals(TEST_NUM_PLANTS, plants.size());
	}
	
	@Test
	public void canCheckIfPlantIsDelivered() {
		List<Plant> plants = plantRepository.findAll();
		Boolean isPlantDelivered = plantRepository.deliveryIsComplete(plants.get(0).getId());
		assertTrue(isPlantDelivered);
	}
	
	@Test
	public void canCheckIfPlantIsDelivered2() {
		List<Plant> plants = plantRepository.findAll();
		Boolean isPlantDelivered = plantRepository.existsPlantByIdAndDeliveryIsComplete(plants.get(0).getId(), true);
		assertTrue(isPlantDelivered);
	}
	
	@Test
	public void canCheckIfPlantExistsById() {
		List<Plant> plants = plantRepository.findAll();
		Boolean isPlantExists = plantRepository.existsPlantById(plants.get(0).getId());
		assertTrue(isPlantExists);
	}
	
	@Test
	public void canFindPlantsWherePriceLessThanPrice() {
		List<Plant> plants = plantRepository.priceLessThan(TEST_MAX_PRICE);
		assertNotNull(plants);
		assertEquals(1, plants.size());
		assertEquals("lily", plants.get(0).getName());
	}
}
