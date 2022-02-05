package com.udacity.jdnd.course3.critter.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.EmployeeTests;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
@Transactional
public class EmployeeRepositoryTests {

	@Autowired
	private EmployeeRepository repository;
	private Employee savedEmployee;

	@BeforeEach
	public void beforeEach() {
		Employee employee = getEmployee();
		savedEmployee = repository.save(employee);
		assertEquals(employee, savedEmployee);
	}
	
	@Test
	public void canAccessRepository() {
		assertNotNull(repository);
	}
	
	@Test
	public void canSaveAndFindEmployee() {
		Optional<Employee> optionalEmployee = repository.findById(savedEmployee.getId());
		assertTrue(optionalEmployee.isPresent());
		assertEquals(savedEmployee, optionalEmployee.get());
	}
	
	@Test
	public void canFindEmployeesByServiceAndTime() {
		DayOfWeek day = savedEmployee.getDaysAvailable().iterator().next();
		List<Employee> foundEmployees = repository.findBySkillsInAndDaysAvailable(savedEmployee.getSkills(), day);
		assertEquals(savedEmployee, foundEmployees.get(0));
	}
	
	private Employee getEmployee() {
		Employee employee = EmployeeTests.getTestEmployee();
		employee.setId(null);
		return employee;
	}
}
