package com.byrneclark.dogapi.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.byrneclark.dogapi.entity.Dog;
import com.byrneclark.dogapi.entity.DogTests;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
public class DogRepositoryTests {

	@Autowired
	private DogRepository dogRepository;
	
	@Test
	public void canAccessRepository() {
		assertNotNull(dogRepository);
	}
	
	@Test
	public void canCreateDog() {
		Dog testDog = DogTests.getTestDog_1();
		Dog savedDog = dogRepository.save(testDog);
		assertEquals(testDog.getName(), savedDog.getName());
	}
	
	@Test
	public void canFindDogs() {
		List<Dog> dogs = (List<Dog>) dogRepository.findAll();
		assertNotNull(dogs);
		assertEquals(6, dogs.size());
	}
}
