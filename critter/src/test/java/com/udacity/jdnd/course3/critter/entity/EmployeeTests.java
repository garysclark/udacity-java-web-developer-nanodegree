package com.udacity.jdnd.course3.critter.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Sets;

public class EmployeeTests {

	private static final Long TEST_ID = 99l;
	private static final String TEST_NAME = "test employee name";
	private static final Set<EmployeeSkill> TEST_SKILLS = new HashSet<>(Arrays.asList(EmployeeSkill.FEEDING, EmployeeSkill.PETTING));
	private static final Set<DayOfWeek> TEST_DAYS_AVAILABLE = Sets.newHashSet(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY);

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
		employee.setSkills(TEST_SKILLS);
		employee.setDaysAvailable(TEST_DAYS_AVAILABLE);
		validateAttributes(employee);
	}
	
	@Test
	public void canCreateEmployeeWithAttributes() {
		Employee employee = getTestEmployee();
		validateAttributes(employee);
	}
	
	@Test
	public void canValidateEquals() {
		Employee employee1 = getTestEmployee();
		Employee employee2 = getTestEmployee();
		assertEquals(employee1, employee2);
	}

	private void validateAttributes(Employee employee) {
		assertEquals(TEST_ID, employee.getId());
		assertEquals(TEST_NAME, employee.getName());
		assertEquals(TEST_SKILLS, employee.getSkills());
		assertEquals(TEST_DAYS_AVAILABLE, employee.getDaysAvailable());
	}

	public static Employee getTestEmployee() {
		return new Employee(TEST_ID, TEST_NAME, TEST_SKILLS, TEST_DAYS_AVAILABLE);
	}
}
