package com.udacity.pricing.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.udacity.pricing.entity.Price;
import com.udacity.pricing.entity.PriceTests;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
public class PriceRepositoryTests {

	@Autowired
	private PriceRepository priceRepository;

	@Test
	public void canAccessRepository() {
		assertNotNull(priceRepository);
	}
	
	@Test
	public void canCreatePrice() {
		Price newPrice = PriceTests.getTestPrice();
		
		Price savedPrice = priceRepository.save(newPrice);
		assertEquals(newPrice.getCurrency(), savedPrice.getCurrency());
		assertEquals(newPrice.getPrice(), savedPrice.getPrice());
		assertEquals(newPrice.getVehicleId(), savedPrice.getVehicleId());
	}
	
	@Test
	public void canFindPrices() {
		List<Price> prices = (List<Price>) priceRepository.findAll();
		assertEquals(1,prices.size());
	}
}
