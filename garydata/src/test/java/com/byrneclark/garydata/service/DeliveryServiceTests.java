package com.byrneclark.garydata.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.byrneclark.garydata.entity.Delivery;
import com.byrneclark.garydata.entity.DeliveryTests;
import com.byrneclark.garydata.entity.Flower;
import com.byrneclark.garydata.entity.FlowerTests;

@SpringBootTest
@Transactional
public class DeliveryServiceTests {

	@Autowired
	private DeliveryService deliveryService;

	@Test
	public void canAccessService() {
		assertNotNull(deliveryService);
	}
	
	@Test
	public void canSaveDelivery() {
		Delivery delivery = DeliveryTests.getTestDelivery();
		delivery.setId(null);
		Flower flower = FlowerTests.getTestFlower();
		flower.setId(null);
		delivery.getPlants().add(flower);
		Long deliveryId = deliveryService.save(delivery);
		assertNotNull(deliveryId);
		Delivery savedDelivery = deliveryService.get(deliveryId);
		assertNotNull(savedDelivery);
	}
}
