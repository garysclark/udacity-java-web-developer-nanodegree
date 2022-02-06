package com.udacity.jdnd.course3.critter.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.udacity.jdnd.course3.critter.pet.PetType;

public class PetTests {

	private static final Long TEST_ID = 99l;
	private static final PetType TEST_TYPE = PetType.DOG;
	private static final String TEST_NAME = "Poppy";
	private static final LocalDate TEST_BIRTHDATE = LocalDate.of(2016, 10, 21);
	private static final String TEST_NOTES = "test notes";
	private static final Customer TEST_OWNER = CustomerTests.getTestCustomer();

	@Test
	public void canCreate() {
		Pet pet = new Pet();
		assertNotNull(pet);
	}

	@Test
	public void canSetGetAttributes() {
		Pet pet = new Pet();
		pet.setId(TEST_ID);
		pet.setType(TEST_TYPE);
		pet.setName(TEST_NAME);
		pet.setOwner(TEST_OWNER);
		pet.setBirthDate(TEST_BIRTHDATE);
		pet.setNotes(TEST_NOTES);
		validateAttributes(pet);
	}

	@Test
	public void canCreateWithAttributes() {
		Pet pet = getTestPet();
		validateAttributes(pet);
	}

	@Test
	public void canValidateEquals() {
		Pet pet1 = getTestPet();
		Pet pet2 = getTestPet();
		assertEquals(pet1, pet2);
	}

	public static Pet getTestPet() {
		return new Pet(TEST_ID, TEST_TYPE, TEST_NAME, TEST_OWNER, TEST_BIRTHDATE, TEST_NOTES);
	}

	private void validateAttributes(Pet pet) {
		assertEquals(TEST_ID, pet.getId());
		assertEquals(TEST_TYPE, pet.getType());
		assertEquals(TEST_NAME, pet.getName());
		assertEquals(TEST_OWNER, pet.getOwner());
		assertEquals(TEST_BIRTHDATE, pet.getBirthDate());
		assertEquals(TEST_NOTES, pet.getNotes());
	}

}
