package com.udacity.pricing.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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

	private static final int TEST_SQL_ROW = 0;
	private static final int TEST_SQL_VEHICLE_ID = 21;
	private static final BigDecimal TEST_SQL_PRICE = new BigDecimal("9999.99");
	private static final String TEST_SQL_CURRENCY = "US Dollars";
	private static final Long TEST_SQL_ID = 1L;

	@Autowired
	private PriceRepository priceRepository;

	@Test
	public void canAccessRepository() {
		assertNotNull(priceRepository);
	}
	
	@Test
	public void canCrudPrice() {
		Price newPrice = PriceTests.getTestPrice_1();
		// Create
		Price savedPrice = priceRepository.save(newPrice);
		assertEquals(newPrice.getCurrency(), savedPrice.getCurrency());
		assertEquals(newPrice.getPrice(), savedPrice.getPrice());
		assertEquals(newPrice.getVehicleId(), savedPrice.getVehicleId());
		// Read
		Optional<Price> optionalPrice = priceRepository.findById(savedPrice.getId());
		assertTrue(optionalPrice.isPresent());
		Price foundPrice = optionalPrice.get();
		assertEquals(savedPrice, foundPrice);
		// Update
		Price newPrice2 = PriceTests.getTestPrice_2();
		foundPrice.setCurrency(newPrice2.getCurrency());
		foundPrice.setPrice(newPrice2.getPrice());
		foundPrice.setVehicleId(newPrice2.getVehicleId());
		Price updatedPrice = priceRepository.save(foundPrice);
		assertEquals(foundPrice, updatedPrice);
		//Delete
		priceRepository.delete(updatedPrice);
		optionalPrice = priceRepository.findById(updatedPrice.getId());
		assertTrue(optionalPrice.isEmpty());
	}
	
	@Test
	public void canFindPrices() {
		List<Price> prices = (List<Price>) priceRepository.findAll();
		assertEquals(5,prices.size());
		
		// validate SQL initialization values
		Price foundPrice = prices.get(TEST_SQL_ROW);
		assertEquals(TEST_SQL_ID, foundPrice.getId());
		assertEquals(TEST_SQL_CURRENCY, foundPrice.getCurrency());
		assertEquals(TEST_SQL_PRICE, foundPrice.getPrice());
		assertEquals(TEST_SQL_VEHICLE_ID, foundPrice.getVehicleId());
	}
}
