package com.udacity.vehicles.domain.manufacturer;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
public class ManufacturerRepositoryTests {

	@Autowired
	private ManufacturerRepository manufacturerRepository;

	@Test
	public void canAccessRepository() {
		assertNotNull(manufacturerRepository);
	}
	
	@Test
	public void canFindById() {
		Optional<Manufacturer> optionalManufacturer = manufacturerRepository.findById(1);
		assertTrue(optionalManufacturer.isPresent());
	}
}
