package com.udacity.jdnd.course3.critter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.PetTests;
import com.udacity.jdnd.course3.critter.repository.PetRepository;

@SpringBootTest
public class PetServiceTests {

	private static final Long TEST_INVALID_ID = 0l;

	@MockBean
	private PetRepository mockRepository;
	
	@Autowired
	private PetService service;
	
	@Test
	public void canAccessService() {
		assertNotNull(service);
	}
	
	@Test
	public void canSavePet() {
		Pet pet = PetTests.getTestPet();
		when(mockRepository.save(pet)).thenReturn(pet);

		Pet savedPet = service.savePet(pet);
		assertEquals(pet, savedPet);
	}
	
	@Test
	public void canFindPetById() {
		Pet pet = PetTests.getTestPet();
		when(mockRepository.findById(pet.getId())).thenReturn(Optional.of(pet));
		
		Pet foundPet = service.getPetById(pet.getId());
		assertEquals(pet, foundPet);
	}
	
	@Test
	public void canHandleEntityNotFoundException() {
		when(mockRepository.findById(TEST_INVALID_ID)).thenReturn(Optional.ofNullable(null));

		assertThrows(EntityNotFoundException.class, ()->{service.getPetById(TEST_INVALID_ID);});
	}

	@Test
	public void canFindPetsByCustomerId() {
		Pet pet = PetTests.getTestPet();
		List<Pet> pets = Collections.singletonList(pet);
		when(mockRepository.findByOwnerId(pet.getOwner().getId())).thenReturn(pets);
		
		List<Pet> foundPets = service.getPetsByCustomerId(pet.getOwner().getId());
		assertEquals(pet, foundPets.get(0));
	}
	
	@Test
	public void canFindAllPets() {
		Pet pet = PetTests.getTestPet();
		List<Pet> pets = Collections.singletonList(pet);
		when(mockRepository.findAll()).thenReturn(pets);
		
		List<Pet> foundPets = service.getAllPets();
		assertEquals(pet, foundPets.get(0));
	}
}
