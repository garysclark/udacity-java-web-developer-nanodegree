package com.udacity.jdnd.course3.critter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;

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

}
