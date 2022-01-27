package com.byrneclark.garydata.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.byrneclark.garydata.entity.Delivery;
import com.byrneclark.garydata.repository.DeliveryRepository;

@SpringBootTest
public class CandyDAOImplTests {

	private static final int NUMBER_OF_CANDIES = 1;
	@Autowired
	private CandyDAOImpl candyDAO;
	@Autowired
	private DeliveryRepository deliveryRepository;

	@Test
	public void canAccessDAO() {
		assertNotNull(candyDAO);
	}
	
	@Test
	public void canFindAllCandy() {
		List<CandyData> candies = candyDAO.findAllCandy();
		assertNotNull(candies);
		assertEquals(NUMBER_OF_CANDIES, candies.size());
	}
	
	@Test
	public void canVerifyCandyOnDelivery() {
		List<Delivery> deliveries = deliveryRepository.findAllDeliveries();
		assertNotNull(deliveries);
		Delivery delivery = deliveries.get(0);
		List<CandyData> candiesOnDelivery = candyDAO.findCandyOnDelivery(delivery.getId());
		assertEquals(0, candiesOnDelivery.size());
		List<CandyData> candies = candyDAO.findAllCandy();
		CandyData candy = candies.get(0);
		candyDAO.addCandyToDelivery(candy.getId(), delivery.getId());
		candiesOnDelivery = candyDAO.findCandyOnDelivery(delivery.getId());
		assertNotNull(candiesOnDelivery);
	}
}
