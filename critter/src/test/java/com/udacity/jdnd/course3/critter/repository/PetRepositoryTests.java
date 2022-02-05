package com.udacity.jdnd.course3.critter.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.CustomerTests;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.PetTests;

@DataJpaTest
public class PetRepositoryTests {

	@Autowired
	private PetRepository petRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Test
	public void canAccessRepository() {
		assertNotNull(petRepository);
	}
	
	@Test
	public void canSaveAndFindPet() {
		Pet pet = getNewPet();
		Pet savedPet = petRepository.save(pet);
		assertEquals(pet, savedPet);
		Optional<Pet> optionalPet = petRepository.findById(savedPet.getId());
		assertEquals(savedPet, optionalPet.get());
	}

	@Test
	public void canFindPetByOwnerId() {
		Customer customer = getSavedCustomer();
		Pet pet = getNewPet();
		pet.setOwnerId(customer.getId());
		Pet savedPet = petRepository.save(pet);
		List<Pet> pets = petRepository.findByOwnerId(customer.getId());
		assertNotNull(pets);
		assertEquals(savedPet, pets.get(0));
	}
	
	private Customer getSavedCustomer() {
		Customer customer = CustomerTests.getTestCustomer();
		customer.setId(null);
		return customerRepository.save(customer);
	}

	private Pet getNewPet() {
		Pet pet = PetTests.getTestPet();
		pet.setId(null);
		pet.setOwnerId(null);
		return pet;
	}
}