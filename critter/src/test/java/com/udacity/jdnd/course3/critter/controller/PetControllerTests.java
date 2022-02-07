package com.udacity.jdnd.course3.critter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.CustomerTests;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.PetTests;
import com.udacity.jdnd.course3.critter.service.CustomerService;

@SpringBootTest
@Transactional
public class PetControllerTests {

	private static final long TEST_INVALID_ENTITY_ID = 0l;
	@Autowired
	private PetController petController;
	@Autowired
	private CustomerService customerService;

	@Test
	public void canAccessController() {
		assertNotNull(petController);
	}
	
	@Test
	public void canFindAllPets() {
		PetDTO petDto1 = petController.savePet(getPet());
		PetDTO petDto2 = petController.savePet(getPet());

		List<PetDTO> pets = petController.getPets();
		assertEquals(2, pets.size());
		assertTrue(pets.containsAll(Arrays.asList(petDto1, petDto2)));
	}
	
	@Test
	public void canHandlePetNotFoundException() {
		assertThrows(EntityNotFoundException.class, ()->{petController.getPet(TEST_INVALID_ENTITY_ID);});
	}

	private PetDTO getPet() {
		Pet pet = PetTests.getTestPet();
		pet.setId(0l);
		pet.setOwner(getSavedCustomer());
		PetDTO dto = new PetDTO();
		BeanUtils.copyProperties(pet, dto);
		dto.setOwnerId(pet.getOwner().getId());
		return dto;
	}
	
	private Customer getSavedCustomer() {
		Customer customer = CustomerTests.getTestCustomer();
		customer.setId(null);
		customer.setPets(new ArrayList<>());
		return customerService.saveCustomer(customer);
	}

}
