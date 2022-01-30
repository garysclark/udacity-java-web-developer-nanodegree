package com.udacity.jdnd.course3.critter.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.CustomerTests;
import com.udacity.jdnd.course3.critter.entity.Pet;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
public class CustomerRepositoryTests {

	@Autowired
	private CustomerRepository repository;

	@Test
	public void canAccessRepository() {
		assertNotNull(repository);
	}
	
	@Test
	public void canSaveCustomer() {
		Customer customer = getCustomer();
		Customer savedCustomer = repository.save(customer);
		assertEquals(savedCustomer, customer);
	}

	private Customer getCustomer() {
		Customer customer = CustomerTests.getTestCustomer();
		customer.setId(null);
		customer.setPets(new ArrayList<Pet>());
		return customer;
	}
}
