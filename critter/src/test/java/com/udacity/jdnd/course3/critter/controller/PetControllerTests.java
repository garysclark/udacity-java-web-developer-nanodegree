package com.udacity.jdnd.course3.critter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.PetTests;
import com.udacity.jdnd.course3.critter.pet.PetController;
import com.udacity.jdnd.course3.critter.pet.PetDTO;

@SpringBootTest
@Transactional
public class PetControllerTests {

	@Autowired
	private PetController petController;

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

	private PetDTO getPet() {
		Pet pet = PetTests.getTestPet();
		pet.setId(0l);
		PetDTO dto = new PetDTO();
		BeanUtils.copyProperties(pet, dto);
		return dto;
	}
}
