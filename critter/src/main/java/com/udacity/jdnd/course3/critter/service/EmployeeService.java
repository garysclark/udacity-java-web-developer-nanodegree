package com.udacity.jdnd.course3.critter.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

@Service
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeRepository repository;

	public Employee save(Employee employee) {
		return repository.save(employee);
	}

	public List<Employee> findAll() {
		return repository.findAll();
	}

	public Employee findById(Long id) {
		Optional<Employee> optionalEmployee = repository.findById(id);
		return optionalEmployee.get();
	}

	public List<Employee> findEmployeesForServicesOnDate(Set<EmployeeSkill> skills, LocalDate date) {
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
