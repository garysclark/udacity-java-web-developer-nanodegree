package com.udacity.jdnd.course3.critter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.EmployeeTests;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;

@SpringBootTest
public class EmployeeServiceTests {

	private static final Long TEST_INVALID_ID = 0l;

	@MockBean
	private EmployeeRepository mockRepository;
	
	@Autowired
	private EmployeeService service;

	@Test
	public void canAccessService() {
		assertNotNull(service);
	}
	
	@Test
	public void canCreateEmployee() {
		Employee employee = EmployeeTests.getTestEmployee();
		when(mockRepository.save(employee)).thenReturn(employee);
		Employee createdEmployee = service.saveEmployee(employee);
		assertEquals(employee,createdEmployee);
	}
	
	@Test
	public void canFindAllEmployees() {
		List<Employee> employees = Collections.singletonList(EmployeeTests.getTestEmployee());
		when(mockRepository.findAll()).thenReturn(employees);
		List<Employee> foundEmployees = service.getAllEmployees();
		assertEquals(employees, foundEmployees);
	}
	
	@Test
	public void canFindEmployeeById() {
		Optional<Employee> optionalEmployee = Optional.of(EmployeeTests.getTestEmployee());
		when(mockRepository.findById(optionalEmployee.get().getId())).thenReturn(optionalEmployee);
		Employee foundEmployee = service.getEmployeeById(optionalEmployee.get().getId());
		assertEquals(optionalEmployee.get(),foundEmployee);
	}
	
	@Test
	public void canHandleEntityNotFoundException() {
		when(mockRepository.findById(TEST_INVALID_ID)).thenReturn(Optional.ofNullable(null));

		assertThrows(EntityNotFoundException.class, ()->{service.getEmployeeById(TEST_INVALID_ID);});
	}
	
	@Test
	public void canFindEmployeeForServiceOnDate() {
		Employee employee = EmployeeTests.getTestEmployee();
		List<Employee> employees = Collections.singletonList(employee);
		DayOfWeek day = employee.getDaysAvailable().iterator().next();
		when(mockRepository.findDistinctEmployeesByDaysAvailableAndSkillsIn(day, employee.getSkills())).thenReturn(employees);

		LocalDate date = LocalDate.now().with(TemporalAdjusters.previous(day));
		List<Employee> foundEmployees = service.getEmployeesForServicesOnDate(employee.getSkills(), date);
		assertEquals(employees.get(0), foundEmployees.get(0));
	}
}
