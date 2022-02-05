package com.udacity.jdnd.course3.critter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.EmployeeTests;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;

@SpringBootTest
public class EmployeeServiceTests {

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
		Employee createdEmployee = service.save(employee);
		assertEquals(employee,createdEmployee);
	}
	
	@Test
	public void canFindAllEmployees() {
		List<Employee> employees = Collections.singletonList(EmployeeTests.getTestEmployee());
		when(mockRepository.findAll()).thenReturn(employees);
		List<Employee> foundEmployees = service.findAll();
		assertEquals(employees, foundEmployees);
	}
	
	@Test
	public void canFindEmployeeById() {
		Optional<Employee> optionalEmployee = Optional.of(EmployeeTests.getTestEmployee());
		when(mockRepository.findById(optionalEmployee.get().getId())).thenReturn(optionalEmployee);
		Employee foundEmployee = service.findById(optionalEmployee.get().getId());
		assertEquals(optionalEmployee.get(),foundEmployee);
	}
	
	@Test
	public void canFindEmployeeForServiceOnDate() {
		Employee employee = EmployeeTests.getTestEmployee();
		List<Employee> employees = Collections.singletonList(employee);
		DayOfWeek day = employee.getDaysAvailable().iterator().next();
		when(mockRepository.findDistinctEmployeesByDaysAvailableAndSkillsIn(day, employee.getSkills())).thenReturn(employees);

		LocalDate date = LocalDate.now().with(TemporalAdjusters.previous(day));
		List<Employee> foundEmployees = service.findEmployeesForServicesOnDate(employee.getSkills(), date);
		assertEquals(employees.get(0), foundEmployees.get(0));
	}
}
