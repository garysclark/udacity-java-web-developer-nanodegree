package com.byrneclark.garydata.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.byrneclark.garydata.entity.Delivery;
import com.byrneclark.garydata.entity.Plant;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
@ActiveProfiles("noinit")
public class PlantRepositoryExercise4Tests {

	private static final String TEST_NAME_1 = "plant 1";

	private static final BigDecimal TEST_PRICE_1 = new BigDecimal("9.99");

	private static final String TEST_NAME_2 = "plant 2";

	private static final BigDecimal TEST_PRICE_2 = new BigDecimal("5.99");

	private static final int TEST_CHEAPER_PLANT_COUNT = 1;

	private static final String TEST_ADDRESS = "6154 Monteverde Drive, San Jose CA 95120";

	private static final String TEST_DELIVERY_NAME = "Ma Barker";

	@Autowired
	private TestEntityManager testEntityManager;
	
	@Autowired
	private PlantRepository plantRepository;
	
	@Test
	public void canVerifyPriceLessThan() {
		Plant plant1 = getPlant(TEST_NAME_1, TEST_PRICE_1);
		Plant plant2 = getPlant(TEST_NAME_2, TEST_PRICE_2);
		
		assertNotNull(plantRepository.save(plant1));
		assertNotNull(plantRepository.save(plant2));
		List<Plant> cheaperPlants = plantRepository.priceLessThan(TEST_PRICE_1);
		assertEquals(TEST_CHEAPER_PLANT_COUNT, cheaperPlants.size());
		assertEquals(plant2, cheaperPlants.get(0));
	}
	
	@Test
	public void canVerifyDeliveryCompleted() {
		Plant plant = testEntityManager.persist(getPlant(TEST_NAME_1, TEST_PRICE_1));
		Delivery delivery = testEntityManager.persist(getDelivery(TEST_DELIVERY_NAME, TEST_ADDRESS, LocalDateTime.now()));
		delivery.setPlants(Arrays.asList(plant));
		plant.setDelivey(delivery);
		assertFalse(plantRepository.existsPlantByIdAndDeliveryIsComplete(plant.getId(), true));
		delivery.setIsComplete(true);
		assertTrue(plantRepository.existsPlantByIdAndDeliveryIsComplete(plant.getId(), true));
	}

	private Delivery getDelivery(String name, String address, LocalDateTime dateTime) {
		Delivery delivery = new Delivery();
		delivery.setName(name);
		delivery.setAddress(address);
		delivery.setDeliveryTime(dateTime);
		return delivery;
	}

	private Plant getPlant(String name, BigDecimal price) {
		Plant plant = new Plant();
		plant.setName(name);
		plant.setPrice(price);
		return plant;
	}
}
