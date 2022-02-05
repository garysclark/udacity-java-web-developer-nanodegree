package com.udacity.jdnd.course3.critter.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

public class ScheduleTests {

	private static final Long TEST_ID = 99l;
	private static final List<Employee> TEST_EMPLOYEES = Collections.singletonList(EmployeeTests.getTestEmployee());
	private static final List<Pet> TEST_PETS = Collections.singletonList(PetTests.getTestPet());
	private static final LocalDate TEST_DATE = LocalDate.of(2022, 2, 4);
	private static final Set<EmployeeSkill> TEST_ACTIVITIES = Collections.singleton(EmployeeSkill.FEEDING);

	@Test
	public void canCreateSchedule() {
		Schedule schedule = new Schedule();
		assertNotNull(schedule);
	}
	
	@Test
	public void canSetGetAttributes() {
		Schedule schedule = new Schedule();
		schedule.setId(TEST_ID);
		schedule.setEmployees(TEST_EMPLOYEES);
		schedule.setPets(TEST_PETS);
		schedule.setDate(TEST_DATE);
		schedule.setActivities(TEST_ACTIVITIES);
		validateAttributes(schedule);
	}
	
	@Test
	public void canCreateWithAttributes() {
		Schedule schedule = getTestSchedule();
		validateAttributes(schedule);
	}

	private void validateAttributes(Schedule schedule) {
		assertEquals(TEST_ID, schedule.getId());
		assertEquals(TEST_EMPLOYEES, schedule.getEmployees());
		assertEquals(TEST_PETS, schedule.getPets());
		assertEquals(TEST_DATE, schedule.getDate());
		assertEquals(TEST_ACTIVITIES, schedule.getActivities());
	}
	
	@Test
	public void canValidateEquals() {
		assertEquals(getTestSchedule(), getTestSchedule());
	}

	static public Schedule getTestSchedule() {
		return new Schedule(TEST_ID, TEST_EMPLOYEES, TEST_PETS, TEST_DATE, TEST_ACTIVITIES);
	}
}
