package com.byrneclark.dogapi.resolver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.byrneclark.dogapi.entity.Dog;
import com.byrneclark.dogapi.exception.DogNotFoundException;
import com.byrneclark.dogapi.mutator.Mutation;

import graphql.ErrorType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
public class QueryTests {

	private static final String TEST_UPDATED_NAME = "UPDATED DOG NAME";
	@Autowired
	private Query query;
	private List<Dog> testDogs;
	@Autowired
	private Mutation mutation;

	@BeforeEach
	public void beforeEach() {
		testDogs = query.findAllDogs();
	}

	@Test
	public void canAccessServices() {
		assertNotNull(query);
		assertNotNull(mutation);
	}

	@Test
	public void canFindDogs() {
		assertNotNull(testDogs);
	}

	@Test
	public void canFindDogById() {
		Dog testDog = testDogs.get(0);
		Dog foundDog = query.findDogById(testDog.getId());
		assertEquals(testDog, foundDog);
	}

	@Test
	public void canHandleInvalidIdOnFindById() {
		Long invalidId = testDogs.size() + 1l;
		DogNotFoundException exception = assertThrows(DogNotFoundException.class, () -> {query.findDogById(invalidId);});
		assertEquals(Query.DOG_NOT_FOUND_ERROR + invalidId, exception.getMessage());
		Map<String,Object> extensions = exception.getExtensions();
		assertEquals(invalidId, extensions.get(DogNotFoundException.EXT_INVALID_DOG_ID));
		assertEquals(ErrorType.DataFetchingException, exception.getErrorType());
	}

	@Test
	public void canFindDogNames() {
		List<String> dogNames = query.findAllDogNames();
		assertNotNull(dogNames);
		for(Dog dog:testDogs) {
			assertTrue(dogNames.contains(dog.getName()));
		}
	}

	@Test
	public void canFindDogBreeds() {
		List<String> dogBreeds = query.findAllDogBreeds();
		assertNotNull(dogBreeds);
		for(Dog dog:testDogs) {
			assertTrue(dogBreeds.contains(dog.getBreed()));
		}
	}

	@Test
	public void canDeleteDogBreed() {
		Dog testDog = testDogs.get(0);
		assertTrue(mutation.deleteDogBreed(testDog.getBreed()));
		List<String> dogBreeds = query.findAllDogBreeds();
		assertFalse(dogBreeds.contains(testDog.getBreed()));
	}
	
	@Test
	public void canUpdateDogName() {
		Dog testDog = testDogs.get(0);
		Dog updatedDog = mutation.updateDogName(testDog.getId(), TEST_UPDATED_NAME);
		assertNotNull(updatedDog);
		assertEquals(TEST_UPDATED_NAME, updatedDog.getName());
		Dog storedDog = query.findDogById(testDog.getId());
		assertEquals(storedDog, updatedDog);
	}
	
	@Test
	public void canHandleInvalidIdOnUpdateDogName() {
		Long invalidId = testDogs.size() + 1l;
		assertThrows(DogNotFoundException.class, () -> {mutation.updateDogName(invalidId, TEST_UPDATED_NAME);});
	}
}
