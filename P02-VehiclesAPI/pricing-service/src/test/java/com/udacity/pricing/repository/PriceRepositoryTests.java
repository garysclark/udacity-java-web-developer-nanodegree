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
import org.springframework.test.context.ActiveProfiles;

import com.udacity.pricing.entity.Price;
import com.udacity.pricing.entity.PriceTests;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class PriceRepositoryTests {

	public static final int TEST_DB_PRICE_COUNT = 5;
	public static final int TEST_DB_ROW = 0;
	public static final Long TEST_DB_VEHICLE_ID = 21L;
	public static final BigDecimal TEST_DB_PRICE = new BigDecimal("9999.99");
	public static final String TEST_DB_CURRENCY = "US Dollars";
	public static final Long TEST_DB_ID = 1L;

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
		assertEquals(TEST_DB_PRICE_COUNT,prices.size());
		
		// validate SQL initialization values
		Price foundPrice = prices.get(TEST_DB_ROW);
		validatePrice(foundPrice);
	}

	@Test
	public void canFindByVehicleId() {
		Price price = priceRepository.findByVehicleId(TEST_DB_VEHICLE_ID);
		assertNotNull(price);
		validatePrice(price);
	}
	
	private void validatePrice(Price price) {
		assertEquals(TEST_DB_ID, price.getId());
		assertEquals(TEST_DB_CURRENCY, price.getCurrency());
		assertEquals(TEST_DB_PRICE, price.getPrice());
		assertEquals(TEST_DB_VEHICLE_ID, price.getVehicleId());
	}
	
}
