package com.udacity.jdnd.course3.critter.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class CustomerTests {

	private static final Long TEST_ID = 99l;
	private static final String TEST_NAME = "test name";
	private static final String TEST_PHONE_NUMBER = "408-432-8855";
	private static final String TEST_NOTES = "test notes";
	private static final List<Pet> TEST_PETS = new ArrayList<Pet>();

	@Test
	public void canCreateCustomer() {
		Customer customer = new Customer();
		assertNotNull(customer);
	}
	
	@Test
	public void canSetGetAttributes() {
		Customer customer = new Customer();
		customer.setId(TEST_ID);
		customer.setName(TEST_NAME);
		customer.setPhoneNumber(TEST_PHONE_NUMBER);
		customer.setNotes(TEST_NOTES);
		customer.setNotes(TEST_NOTES);
		customer.setPets(TEST_PETS);
		validateTestContent(customer);
	}
	
	@Test
	public void canCreateWithAttributes() {
		Customer customer = new Customer(TEST_ID,TEST_NAME,TEST_PHONE_NUMBER,TEST_NOTES,TEST_PETS);
		validateTestContent(customer);
	}

	@Test
	public void canValidateEquals() {
		Customer customer1 = getTestCustomer();
		Customer customer2 = getTestCustomer();
		assertEquals(customer1, customer2);
	}
	
	public static Customer getTestCustomer() {
		return new Customer(TEST_ID,TEST_NAME,TEST_PHONE_NUMBER,TEST_NOTES,TEST_PETS);
	}

	private void validateTestContent(Customer customer) {
		assertEquals(TEST_ID, customer.getId());
		assertEquals(TEST_NAME, customer.getName());
		assertEquals(TEST_PHONE_NUMBER, customer.getPhoneNumber());
		assertEquals(TEST_NOTES, customer.getNotes());
		assertEquals(TEST_PETS, customer.getPets());
	}
}
