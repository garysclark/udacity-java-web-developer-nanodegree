package com.udacity.jdnd.course3.critter.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.EmployeeSkill;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;

@Service
@Transactional
public class EmployeeService {

	private static final String EMPLOYEE_NOT_FOUND_EXCEPTION_MESSAGE = "Employee not found - id : ";
	@Autowired
	private EmployeeRepository repository;

	public Employee saveEmployee(Employee employee) {
		return repository.save(employee);
	}

	public List<Employee> getAllEmployees() {
		return repository.findAll();
	}

	public Employee getEmployeeById(Long id) {
		Optional<Employee> optionalEmployee = repository.findById(id);
		if(optionalEmployee.isEmpty()) {
			throw new EntityNotFoundException(EMPLOYEE_NOT_FOUND_EXCEPTION_MESSAGE + id);
		}
		return optionalEmployee.get();
	}

	public List<Employee> getEmployeesForServicesOnDate(Set<EmployeeSkill> skills, LocalDate date) {
		List<Employee> employees = repository.findDistinctEmployeesByDaysAvailableAndSkillsIn(date.getDayOfWeek(), skills);
		List<Employee> employeesWithAllServices = new ArrayList<>();
		
		for(Employee employee:employees) {
			if(employee.getSkills().containsAll(skills)) {
				employeesWithAllServices.add(employee);
			}
		}
		
		return employeesWithAllServices;
	}

}
