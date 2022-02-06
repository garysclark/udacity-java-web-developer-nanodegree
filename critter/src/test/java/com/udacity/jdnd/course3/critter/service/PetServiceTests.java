package com.udacity.jdnd.course3.critter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.PetTests;
import com.udacity.jdnd.course3.critter.repository.PetRepository;

@SpringBootTest
public class PetServiceTests {

	@MockBean
	private PetRepository mockPetRepository;
	
	@Autowired
	private PetService service;
	
	@Test
	public void canAccessService() {
		assertNotNull(service);
	}
	
	@Test
	public void canSavePet() {
		Pet pet = PetTests.getTestPet();
		when(mockPetRepository.save(pet)).thenReturn(pet);

		Pet savedPet = service.savePet(pet);
		assertEquals(pet, savedPet);
	}
	
	@Test
	public void canFindPetById() {
		Pet pet = PetTests.getTestPet();
		when(mockPetRepository.findById(pet.getId())).thenReturn(Optional.of(pet));
		
		Pet foundPet = service.findPetById(pet.getId());
		assertEquals(pet, foundPet);
	}
	
	@Test
	public void canFindPetsByCustomerId() {
		Pet pet = PetTests.getTestPet();
		List<Pet> pets = Collections.singletonList(pet);
		when(mockPetRepository.findByOwnerId(pet.getOwnerId())).thenReturn(pets);
		
		List<Pet> foundPets = service.findPetsByCustomerId(pet.getOwnerId());
		assertEquals(pet, foundPets.get(0));
	}
	
	@Test
	public void canFindAllPets() {
		Pet pet = PetTests.getTestPet();
		List<Pet> pets = Collections.singletonList(pet);
		when(mockPetRepository.findAll()).thenReturn(pets);
		
		List<Pet> foundPets = service.findAllPets();
		assertEquals(pet, foundPets.get(0));
	}
}
