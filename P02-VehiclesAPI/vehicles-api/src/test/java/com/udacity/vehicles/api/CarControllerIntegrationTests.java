package com.udacity.vehicles.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.udacity.vehicles.domain.Condition;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepositoryTests;
import com.udacity.vehicles.domain.car.CarTests;
import com.udacity.vehicles.service.CarServiceIntegrationTests;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
public class CarControllerIntegrationTests {

	private static final Integer TEST_CAR_NUM_RECORDS = CarRepositoryTests.TEST_CAR_NUM_RECORDS;
	private static final Long TEST_CAR_ID = CarRepositoryTests.TEST_CAR_ID;
	private static final String TEST_CAR_PRICE = CarServiceIntegrationTests.TEST_CAR_PRICE;
	private static final Condition TEST_UPDATED_CAR_CONDITION = CarRepositoryTests.TEST_UPDATED_CAR_CONDITION;
	private static final Integer TEST_UPDATED_CAR_MILEAGE = CarRepositoryTests.TEST_UPDATED_CAR_MILEAGE;
	private static final Integer TEST_UPDATED_CAR_MODEL_YEAR = CarRepositoryTests.TEST_UPDATED_CAR_MODEL_YEAR;
	private static final Integer TEST_UPDATED_CAR_PRODUCTION_YEAR = CarRepositoryTests.TEST_UPDATED_CAR_PRODUCTION_YEAR;

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void canGetAllCars() throws URISyntaxException {
		ParameterizedTypeReference<PagedModel<Car>> responseType = new ParameterizedTypeReference<PagedModel<Car>>() {};
		RequestEntity<PagedModel<Car>> request = new RequestEntity<PagedModel<Car>>(HttpMethod.GET, new URI("http://localhost:" + port + "/cars"));
		ResponseEntity<PagedModel<Car>> response = restTemplate.exchange(request, responseType);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		Collection<Car> cars = response.getBody().getContent();
		assertEquals(TEST_CAR_NUM_RECORDS, cars.size());
		Car car = cars.iterator().next();
		validateCar(car);
	}
	
	@Test
	public void canGetCarById() {
		Car car = getCar(TEST_CAR_ID);
		validateCar(car);
	}

	private void validateCar(Car car) {
		assertEquals(TEST_CAR_ID, car.getId());
		assertEquals(TEST_CAR_PRICE, car.getPrice());
		CarRepositoryTests.validateRepoContent(car);
		CarServiceIntegrationTests.validateServiceContent(car);
	}
	
	@Test
	public void canCreateCar() {
		Car car = CarTests.getTestCar_1();
		car.setId(null);
		ResponseEntity<Car> response = restTemplate.postForEntity("http://localhost:" + port + "/cars", car, Car.class);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		Car savedCar = response.getBody();

		assertNotEquals(car.getId(), savedCar.getId());
		assertNotEquals(car.getCreatedAt(), savedCar.getCreatedAt());
		assertNotEquals(car.getModifiedAt(), savedCar.getModifiedAt());

		assertEquals(savedCar.getCreatedAt(), savedCar.getModifiedAt());
		assertEquals(car.getCondition(), savedCar.getCondition());
		assertEquals(car.getDetails(), savedCar.getDetails());
		assertEquals(car.getLocation().getLat(), savedCar.getLocation().getLat());
		assertEquals(car.getLocation().getLon(), savedCar.getLocation().getLon());
	}
	
	@Test
	public void canUpdateCar() throws URISyntaxException {
		Car car = getCar(TEST_CAR_ID);
		car.setCondition(TEST_UPDATED_CAR_CONDITION);
		car.getDetails().setMileage(TEST_UPDATED_CAR_MILEAGE);
		car.getDetails().setModelYear(TEST_UPDATED_CAR_MODEL_YEAR);
		car.getDetails().setProductionYear(TEST_UPDATED_CAR_PRODUCTION_YEAR);
		
		RequestEntity<Car> request = new RequestEntity<Car>(car, HttpMethod.PUT, new URI("http://localhost:" + port + "/cars/" + car.getId()));
		ResponseEntity<Car> response = restTemplate.exchange(request, Car.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response);
		
		Car savedCar = getCar(TEST_CAR_ID);
		
		assertEquals(car.getId(), savedCar.getId());
		assertEquals(car.getCondition(), savedCar.getCondition());
		assertEquals(car.getCreatedAt(), savedCar.getCreatedAt());
		assertEquals(car.getDetails(), savedCar.getDetails());
	}
	
	@Test
	public void canDeleteCar() throws URISyntaxException {
		RequestEntity<Car> request = new RequestEntity<Car>(HttpMethod.DELETE, new URI("http://localhost:" + port + "/cars/" + TEST_CAR_ID));
		ResponseEntity<Object> response = restTemplate.exchange(request, Object.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	private Car getCar(Long testCarId) {
		ResponseEntity<Car> response = restTemplate.getForEntity("http://localhost:" + port + "/cars/" + TEST_CAR_ID, Car.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		return response.getBody();
	}
}
