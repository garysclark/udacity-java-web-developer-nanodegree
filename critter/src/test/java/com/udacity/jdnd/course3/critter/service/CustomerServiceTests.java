package com.udacity.jdnd.course3.critter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.CustomerTests;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;

@SpringBootTest
public class CustomerServiceTests {
	
	private static final Long TEST_INVALID_ID = -1l;

	@MockBean
	private CustomerRepository mockRepository;
	
	@Autowired
	private CustomerService service;

	@Test
	public void canAccessService() {
		assertNotNull(service);
	}
	
	@Test
	public void canCreateCustomer() {
		Customer customer = CustomerTests.getTestCustomer();
		when(mockRepository.save(customer)).thenReturn(customer);
		Customer savedCustomer = service.saveCustomer(customer);
		assertEquals(customer, savedCustomer);
	}
	
	@Test
	public void canFindAllCustomers() {
		Customer customer = CustomerTests.getTestCustomer();
		List<Customer> customers = Collections.singletonList(customer);
		when(mockRepository.findAll()).thenReturn(customers);
		List<Customer> foundCustomers = service.getAllCustomers();
		assertEquals(customers, foundCustomers);
	}
	
	@Test
	public void canFindCustomerById() {
		Customer customer = CustomerTests.getTestCustomer();
		when(mockRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
		
		Customer foundCustomer = service.getCustomerById(customer.getId());
		assertEquals(customer, foundCustomer);
	}
	
	@Test
	public void canHandleRecordNotFoundById() {
		when(mockRepository.findById(TEST_INVALID_ID)).thenReturn(Optional.ofNullable(null));

		assertThrows(EntityNotFoundException.class, ()->{service.getCustomerById(TEST_INVALID_ID);});
	}
}
