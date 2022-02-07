package com.udacity.jdnd.course3.critter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.CustomerTests;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.EmployeeTests;

@SpringBootTest
@Transactional
public class UserControllerTests {

	private static final long TEST_INVALID_ENTITY_ID = 0;
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
	
	@Test
	public void canSaveGetCustomer() {
		CustomerDTO customerDto = getCustomerDto();
		CustomerDTO savedCustomerDto = userController.saveCustomer(customerDto);
		assertNotNull(savedCustomerDto);
		CustomerDTO foundCustomerDto = userController.getCustomer(savedCustomerDto.getId());
		assertEquals(savedCustomerDto,foundCustomerDto);
	}
	
	@Test
	public void canCustomerNotFoundException() {
		assertThrows(EntityNotFoundException.class, ()->{userController.getCustomer(TEST_INVALID_ENTITY_ID);});
	}
	
	@Test
	public void canEmployeeNotFoundException() {
		assertThrows(EntityNotFoundException.class, ()->{userController.getEmployee(TEST_INVALID_ENTITY_ID);});
	}
	
	private CustomerDTO getCustomerDto() {
		Customer customer = CustomerTests.getTestCustomer();
		customer.setPets(new ArrayList<>());
		CustomerDTO dto = new CustomerDTO();
		BeanUtils.copyProperties(customer, dto);
		return dto;
	}

	private EmployeeDTO getEmployeeDto() {
		Employee employee = EmployeeTests.getTestEmployee();
		EmployeeDTO dto = new EmployeeDTO();
		BeanUtils.copyProperties(employee, dto);
		return dto;
	}
}
