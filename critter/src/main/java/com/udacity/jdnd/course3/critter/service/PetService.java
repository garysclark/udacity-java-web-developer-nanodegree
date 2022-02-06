package com.udacity.jdnd.course3.critter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;

@Service
@Transactional
public class PetService {

	@Autowired
	private PetRepository repository;
	@Autowired
	private CustomerRepository customerRepository;

	public Pet savePet(Pet pet) {
		Pet savedPet = repository.save(pet);
		Long customerId = savedPet.getOwner().getId();
		Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
		if(optionalCustomer.isPresent()) {
			Customer customer = optionalCustomer.get();
			customer.getPets().add(savedPet);
			customerRepository.save(customer);
		}
		
		return savedPet;
	}

	public Pet findPetById(Long id) {
		Optional<Pet> optionalPet = repository.findById(id);
		return optionalPet.get();
	}

	public List<Pet> findPetsByCustomerId(Long ownerId) {
		return repository.findByOwnerId(ownerId);
	}

	public List<Pet> findAllPets() {
		return repository.findAll();
	}

}
