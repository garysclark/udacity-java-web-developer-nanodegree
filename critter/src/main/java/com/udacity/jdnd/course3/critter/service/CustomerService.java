package com.udacity.jdnd.course3.critter.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;

@Service
@Transactional
public class CustomerService {
	
	public static final String CUSTOMER_NOT_FOUND_EXCEPTION_MESSAGE = "Customer not found - id : ";

	@Autowired
	private CustomerRepository repository;

	public Customer save(Customer customer) {
		return repository.save(customer);
	}

	public List<Customer> findAll() {
		return repository.findAll();
	}

	public Customer findById(Long id) {
		Optional<Customer> optionalCustomer = repository.findById(id);
		if(optionalCustomer.isEmpty()) {
			throw new EntityNotFoundException(CUSTOMER_NOT_FOUND_EXCEPTION_MESSAGE + id);
		}
		return optionalCustomer.get();
	}

}
