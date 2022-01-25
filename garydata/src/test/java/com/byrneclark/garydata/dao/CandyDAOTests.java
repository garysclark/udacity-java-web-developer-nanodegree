package com.byrneclark.garydata.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.byrneclark.garydata.entity.Delivery;
import com.byrneclark.garydata.entity.DeliveryTests;

public class CandyDAOTests {

	@Mock
	private CandyDAO candyDAO;
	private AutoCloseable autoCloseable;
	private List<CandyData> mockCandiesList = new ArrayList<>(Arrays.asList(CandyDataTests.getTestCandyData_1()));

	@BeforeEach
	public void beforeEach() {
		autoCloseable = MockitoAnnotations.openMocks(this);
		when(candyDAO.findAllCandy()).thenReturn(mockCandiesList);
	}

	@AfterEach
	public void afterEach() throws Exception {
		autoCloseable.close();
	}
	
	@Test
	public void canCreate() {
		assertNotNull(candyDAO);

	}
	
	@Test
	public void canFindAllCandy() {
		List<CandyData> candies = candyDAO.findAllCandy();
		assertNotNull(candies);
	}
	
	@Test
	public void canAddCandyToDelivery() {
		Delivery delivery = DeliveryTests.getTestDelivery();
		when(candyDAO.findCandyOnDelivery(delivery.getId())).thenReturn(mockCandiesList);
		List<CandyData> candies = candyDAO.findAllCandy();
		candyDAO.addCandyToDelivery(candies.get(0).getId(), delivery.getId());
		List<CandyData> candiesOnDelivery = candyDAO.findCandyOnDelivery(delivery.getId());
		assertNotNull(candiesOnDelivery);
		assertEquals(1, candiesOnDelivery.size());
		assertEquals(candies.get(0), candiesOnDelivery.get(0));
	}
}
