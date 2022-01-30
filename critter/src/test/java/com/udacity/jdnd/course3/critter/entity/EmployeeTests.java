package com.udacity.jdnd.course3.critter.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class EmployeeTests {

	private static final Long TEST_ID = 99l;
	private static final String TEST_NAME = "test employee name";

	@Test
	public void canCreateEmployee() {
		Employee employee = new Employee();
		assertNotNull(employee);
	}
	
	@Test
	public void canSetGetAttributes() {
		Employee employee = new Employee();
		employee.setId(TEST_ID);
		employee.setName(TEST_NAME);
		validateAttributes(employee);
	}
	
	@Test
	public void canCreateEmployeeWithAttributes() {
		Employee employee = getTestEmployee();
		validateAttributes(employee);
	}

	private void validateAttributes(Employee employee) {
		assertEquals(TEST_ID, employee.getId());
		assertEquals(TEST_NAME, employee.getName());
	}

	public static Employee getTestEmployee() {
		return new Employee(TEST_ID, TEST_NAME);
	}
}
