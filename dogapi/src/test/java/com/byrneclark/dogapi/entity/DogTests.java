package com.byrneclark.dogapi.entity;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class DogTests {

	private static final Long TEST_ID_1 = 99l;
	private static final String TEST_NAME_1 = "test name";
	private static final String TEST_BREED_1 = "test breed";
	private static final String TEST_ORIGIN_1 = "test origin";
	private static final Long TEST_ID_2 = 100l;
	private static final String TEST_NAME_2 = "test name 2";
	private static final String TEST_BREED_2 = "test breed 2";
	private static final String TEST_ORIGIN_2 = "test origin 2";

	@Test
	public void canCreateDogWithoutAttributes() {
		Dog dog = new Dog();
		assertNotNull(dog);
	}
	
	@Test
	public void canCreateDogWithAttributes() {
		Dog dog = new Dog(TEST_ID_1, TEST_NAME_1, TEST_BREED_1, TEST_ORIGIN_1);
		assertNotNull(dog);
		assertEquals(TEST_ID_1, dog.getId());
		assertEquals(TEST_NAME_1, dog.getName());
		assertEquals(TEST_BREED_1, dog.getBreed());
		assertEquals(TEST_ORIGIN_1, dog.getOrigin());
	}
	
	@Test
	public void canSetAttributes() {
		Dog dog = getTestDog_1();
		dog.setId(TEST_ID_2);
		assertEquals(TEST_ID_2, dog.getId());
		dog.setBreed(TEST_BREED_2);
		assertEquals(TEST_BREED_2, dog.getBreed());
		dog.setName(TEST_NAME_2);
		assertEquals(TEST_NAME_2, dog.getName());
		dog.setOrigin(TEST_ORIGIN_2);
		assertEquals(TEST_ORIGIN_2, dog.getOrigin());
	}
	
	@Test
	public void canVerifyEquality() {
		Dog dog1 = getTestDog_1();
		Dog dog2 = getTestDog_1();
		assertEquals(dog1, dog2);
	}

	@Test
	public void canVerifyInequality() {
		Dog dog1 = getTestDog_1();
		Dog dog2 = getTestDog_2();
		assertNotEquals(dog1, dog2);
	}
	
	public static Dog getTestDog_2() {
		return new Dog(TEST_ID_2, TEST_NAME_2, TEST_BREED_2, TEST_ORIGIN_2);
	}

	public static Dog getTestDog_1() {
		return new Dog(TEST_ID_1, TEST_NAME_1, TEST_BREED_1, TEST_ORIGIN_1);
	}
}
