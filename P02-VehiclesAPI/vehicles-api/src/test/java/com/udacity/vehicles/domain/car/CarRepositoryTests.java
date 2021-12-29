package com.udacity.vehicles.domain.car;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

	private static final Long TEST_SQL_ID = 1l;
	private static final LocalDateTime TEST_SQL_CREATED_AT = LocalDateTime.parse("2007-12-03T10:15:30");
	private static final LocalDateTime TEST_SQL_MODIFIED_AT = LocalDateTime.parse("2008-12-03T10:15:30");
	private static final Condition TEST_SQL_CONDITION = Condition.NEW;
	private static final Double TEST_SQL_LATTITUDE = 90.0;
	private static final Double TEST_SQL_LONGITUDE = 74.0;
	private static final String TEST_SQL_BODY = "coupe";
	private static final String TEST_SQL_MODEL = "corvette";
	private static final Integer TEST_SQL_NUMBER_OF_DOORS = 2;
	private static final String TEST_SQL_FUEL_TYPE = "91 Octane Gasoline";
	private static final String TEST_SQL_ENGINE = "350 HP Fuel Injected";
	private static final Integer TEST_SQL_MILEAGE = 634;
	private static final Integer TEST_SQL_MODEL_YEAR = 2018;
	private static final Object TEST_SQL_PRODUCTION_YEAR = 2020;
	private static final Object TEST_SQL_EXTERNAL_COLOR = "Fire Engine Red";

	@Autowired
	private CarRepository carRepository;

	@Test
	public void canAccessRepository() {
		assertNotNull(carRepository);
	}
	
	@Test
	public void canFindCarById() {
		Optional<Car> optionalCar = carRepository.findById(TEST_SQL_ID);
		assertTrue(optionalCar.isPresent());
		Car car = optionalCar.get();
		assertEquals(TEST_SQL_ID, car.getId());
		assertEquals(TEST_SQL_CREATED_AT, car.getCreatedAt());
		assertEquals(TEST_SQL_MODIFIED_AT, car.getModifiedAt());
		assertEquals(TEST_SQL_CONDITION, car.getCondition());
		assertEquals(TEST_SQL_LATTITUDE, car.getLocation().getLat());
		assertEquals(TEST_SQL_LONGITUDE, car.getLocation().getLon());
		assertEquals(TEST_SQL_BODY, car.getDetails().getBody());
		assertEquals(TEST_SQL_MODEL, car.getDetails().getModel());
		assertEquals(TEST_SQL_NUMBER_OF_DOORS, car.getDetails().getNumberOfDoors());
		assertEquals(TEST_SQL_FUEL_TYPE, car.getDetails().getFuelType());
		assertEquals(TEST_SQL_ENGINE, car.getDetails().getEngine());
		assertEquals(TEST_SQL_MILEAGE, car.getDetails().getMileage());
		assertEquals(TEST_SQL_MODEL_YEAR, car.getDetails().getModelYear());
		assertEquals(TEST_SQL_PRODUCTION_YEAR, car.getDetails().getProductionYear());
		assertEquals(TEST_SQL_EXTERNAL_COLOR, car.getDetails().getExternalColor());
	}
	
	@Test
	public void canCrudCar() {
		Car testCar = CarTests.getTestCar_1();

		testCar.setId(null);
		
		Car savedCar = carRepository.save(testCar);
		assertNotNull(savedCar);
	}
	
	@Test
	public void canFindCars() {
		List<Car> cars = carRepository.findAll();
		assertEquals(2,cars.size());
	}
	
	@Test
	public void canUpdateCar() {
		List<Car> cars = carRepository.findAll();
		assertEquals(2,cars.size());
		Car car = cars.get(0);
		car.setCondition(Condition.USED);
		Car updatedCar = carRepository.save(car);
		assertNotNull(updatedCar);
	}
	
	@Test
	public void canCopyCar() {
		List<Car> cars = carRepository.findAll();
		assertEquals(2,cars.size());
		Car car = cars.get(0);
		car.setId(99l);
		Car copiedCar = carRepository.save(car);
		assertNotNull(copiedCar);
		cars = carRepository.findAll();
		assertEquals(3,cars.size());
	}
}
