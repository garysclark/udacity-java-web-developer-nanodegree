package com.udacity.vehicles.domain.car;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.udacity.vehicles.domain.Condition;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
public class CarRepositoryTests {

	public static final Long TEST_CAR_ID = 1l;
	public static final int TEST_CAR_NUM_RECORDS = 2;
	public static final Condition TEST_UPDATED_CAR_CONDITION = Condition.USED;
	public static final Integer TEST_UPDATED_CAR_MILEAGE = 63400;
	public static final Integer TEST_UPDATED_CAR_MODEL_YEAR = 1990;
	public static final Integer TEST_UPDATED_CAR_PRODUCTION_YEAR = 1991;

	private static final LocalDateTime TEST_CAR_CREATED_AT = LocalDateTime.parse("2007-12-03T10:15:30");
	private static final LocalDateTime TEST_CAR_MODIFIED_AT = LocalDateTime.parse("2008-12-03T10:15:30");
	private static final Condition TEST_CAR_CONDITION = Condition.NEW;
	private static final Double TEST_CAR_LATTITUDE = 90.0;
	private static final Double TEST_CAR_LONGITUDE = 74.0;
	private static final String TEST_CAR_BODY = "coupe";
	private static final String TEST_CAR_MODEL = "corvette";
	private static final Integer TEST_CAR_NUMBER_OF_DOORS = 2;
	private static final String TEST_CAR_FUEL_TYPE = "91 Octane Gasoline";
	private static final String TEST_CAR_ENGINE = "350 HP Fuel Injected";
	private static final Integer TEST_CAR_MILEAGE = 634;
	private static final Integer TEST_CAR_MODEL_YEAR = 2018;
	private static final Integer TEST_CAR_PRODUCTION_YEAR = 2020;
	private static final String TEST_CAR_EXTERNAL_COLOR = "Fire Engine Red";

	@Autowired
	private CarRepository carRepository;

	@Test
	public void canAccessRepository() {
		assertNotNull(carRepository);
	}
	
	@Test
	public void canFindCarById() {
		Optional<Car> optionalCar = carRepository.findById(TEST_CAR_ID);
		assertTrue(optionalCar.isPresent());
		Car car = optionalCar.get();
		validateRepoContent(car);
	}
	
	@Test
	public void canFindCars() {
		List<Car> cars = carRepository.findAll();
		assertEquals(TEST_CAR_NUM_RECORDS,cars.size());
	}
	
	@Test
	public void canCrudCar() {
		Car testCar = CarTests.getTestCar_1();
		// Create
		Car savedCar = carRepository.save(testCar);
		assertNotNull(savedCar);
		assertTrue(CarTests.isPersistedAttributesEqual(testCar, savedCar));
		// Read
		Optional<Car> optionalCar = carRepository.findById(savedCar.getId());
		assertTrue(optionalCar.isPresent());
		Car foundCar = optionalCar.get();
		assertEquals(savedCar, foundCar);
		// Update
		Car testCar2 = CarTests.getTestCar_2();
		CarTests.copyPersistedAttributes(testCar2, savedCar);
		Car updatedCar = carRepository.save(savedCar);
		assertTrue(CarTests.isPersistedAttributesEqual(savedCar, updatedCar));
		assertEquals(savedCar.getId(), updatedCar.getId());
		assertEquals(savedCar.getCreatedAt(), updatedCar.getCreatedAt());
		// Delete
		carRepository.delete(updatedCar);
		optionalCar = carRepository.findById(updatedCar.getId());
		assertTrue(optionalCar.isEmpty());
	}

	public static void validateRepoContent(Car car) {
		assertEquals(TEST_CAR_ID, car.getId());
		assertEquals(TEST_CAR_CREATED_AT, car.getCreatedAt());
		assertEquals(TEST_CAR_MODIFIED_AT, car.getModifiedAt());
		assertEquals(TEST_CAR_CONDITION, car.getCondition());
		assertEquals(TEST_CAR_LATTITUDE, car.getLocation().getLat());
		assertEquals(TEST_CAR_LONGITUDE, car.getLocation().getLon());
		assertEquals(TEST_CAR_BODY, car.getDetails().getBody());
		assertEquals(TEST_CAR_MODEL, car.getDetails().getModel());
		assertEquals(TEST_CAR_NUMBER_OF_DOORS, car.getDetails().getNumberOfDoors());
		assertEquals(TEST_CAR_FUEL_TYPE, car.getDetails().getFuelType());
		assertEquals(TEST_CAR_ENGINE, car.getDetails().getEngine());
		assertEquals(TEST_CAR_MILEAGE, car.getDetails().getMileage());
		assertEquals(TEST_CAR_MODEL_YEAR, car.getDetails().getModelYear());
		assertEquals(TEST_CAR_PRODUCTION_YEAR, car.getDetails().getProductionYear());
		assertEquals(TEST_CAR_EXTERNAL_COLOR, car.getDetails().getExternalColor());
	}
}
