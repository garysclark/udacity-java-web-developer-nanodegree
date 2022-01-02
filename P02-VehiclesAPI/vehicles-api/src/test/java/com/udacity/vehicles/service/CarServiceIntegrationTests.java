package com.udacity.vehicles.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepositoryTests;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
public class CarServiceIntegrationTests {

	private static final Long TEST_CAR_ID = 1l;
private static final Long TEST_INVALID_CAR_ID = 99l;
	@Autowired
	private CarService carService;

	@Test
	public void canAccessService() {
		assertNotNull(carService);
	}
	
	@Test
	public void canGetAllCars() {
		List<Car> cars = carService.list();
		assertNotNull(cars);
		assertEquals(CarRepositoryTests.TEST_DB_NUM_RECORDS, cars.size());
		Car car = cars.get(0);
		CarRepositoryTests.validateRepoContent(car);
		validateServiceContent(car);
	}
	
	@Test
	public void canFindCarById() {
		Car car = carService.findById(TEST_CAR_ID);
		assertEquals(TEST_CAR_ID, car.getId());
		CarRepositoryTests.validateRepoContent(car);
		validateServiceContent(car);
	}
	
	@Test
	public void canHandleCarNotFoundError() {
		assertThrows(CarNotFoundException.class, 
				()->{carService.findById(TEST_INVALID_CAR_ID);});
	}
	
	@Test
	public void canDeleteCar() {
		carService.delete(TEST_CAR_ID);
		assertThrows(CarNotFoundException.class, 
				()->{carService.findById(TEST_CAR_ID);});
	}

	@Test
	public void canHandleDeleteInvalidCarError() {
		assertThrows(CarNotFoundException.class, 
				()->{carService.delete(TEST_INVALID_CAR_ID);});
	}
	
	private void validateServiceContent(Car car) {
		assertNotNull(car.getLocation().getAddress());
		assertNotNull(car.getLocation().getCity());
		assertNotNull(car.getLocation().getState());
		assertNotNull(car.getLocation().getZip());
		assertNotEquals("(consult price)",car.getPrice());
	}
}
