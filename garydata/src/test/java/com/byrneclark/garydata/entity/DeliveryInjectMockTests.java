package com.byrneclark.garydata.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DeliveryInjectMockTests {

	private static final int TEST_NUM_PLANTS = 1;

	@Mock
	List<Plant> mockPlants;
	
	@InjectMocks
	private Delivery delivery = new Delivery();
	
	@Test
	public void canGetPlants() {
		Mockito.when(mockPlants.size()).thenReturn(TEST_NUM_PLANTS);
		List<Plant> plants = delivery.getPlants();
		assertNotNull(plants);
		assertEquals(TEST_NUM_PLANTS, plants.size());
	}
}
