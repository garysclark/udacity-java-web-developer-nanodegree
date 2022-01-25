package com.byrneclark.garydata.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.byrneclark.garydata.entity.Plant;
import com.byrneclark.garydata.entity.PlantTests;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PlantControllerIntegrationTests {

	private static final Long TEST_DELIVERED_PLANT_ID = 1l;

	private static final BigDecimal TEST_MAX_PLANT_PRICE = new BigDecimal("9.99");
	
	@Autowired
	private PlantController plantController;
	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int port;

	@Test
	public void canAccessController() {
		assertNotNull(plantController);
	}
	
	@Test
	public void canSavePlant() {
		Plant newPlant = PlantTests.getTestPlant_1();
		newPlant.setId(null);
		
		ResponseEntity<Long> response = restTemplate.postForEntity("http://localhost:" + port + "/plant/save", newPlant, Long.class);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(4l, response.getBody());
	}
	
	@Test
	public void canVerifyPlantIsDelivered() {
		ResponseEntity<Boolean> response = restTemplate.getForEntity("http://localhost:" + port + "/plant/delivered/" + TEST_DELIVERED_PLANT_ID, Boolean.class);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(Boolean.valueOf("true"), response.getBody());
	}
	
	@Test
	public void canGetPlantsLessThanPrice() {
		ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:" + port + "/plant/pricelimit/" + TEST_MAX_PLANT_PRICE, List.class);
		assertNotNull(response);
		assertEquals(1, response.getBody().size());
	}
}
