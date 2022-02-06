package com.udacity.jdnd.course3.critter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.EmployeeTests;

@SpringBootTest
@Transactional
public class UserControllerTests {

	@Autowired
	private UserController userController;

	@Test
	public void canAccessController() {
		assertNotNull(userController);
	}
	
	@Test
	public void canVerifyEmployeeAvailability() {
		EmployeeDTO employeeDTO = getEmployeeDto();
		EmployeeDTO savedEmployeeDTO = userController.saveEmployee(employeeDTO);
		assertNotNull(savedEmployeeDTO);
		EmployeeRequestDTO requestDTO = new EmployeeRequestDTO();
		LocalDate date = LocalDate.now().with(TemporalAdjusters.previous(employeeDTO.getDaysAvailable().iterator().next()));
		requestDTO.setDate(date);
		requestDTO.setSkills(employeeDTO.getSkills());
		List<EmployeeDTO> availableEmployees = userController.findEmployeesForService(requestDTO);
		assertNotNull(availableEmployees);
		assertEquals(savedEmployeeDTO, availableEmployees.get(0));
		assertEquals(1, availableEmployees.size());
	}

	private EmployeeDTO getEmployeeDto() {
		Employee employee = EmployeeTests.getTestEmployee();
		EmployeeDTO dto = new EmployeeDTO();
		BeanUtils.copyProperties(employee, dto);
		return dto;
	}
}
