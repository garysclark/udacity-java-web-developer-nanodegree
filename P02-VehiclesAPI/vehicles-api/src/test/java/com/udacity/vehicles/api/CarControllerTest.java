package com.udacity.vehicles.api;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.Condition;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.Details;
import com.udacity.vehicles.domain.manufacturer.Manufacturer;
import com.udacity.vehicles.service.CarNotFoundException;
import com.udacity.vehicles.service.CarService;

/**
 * Implements testing of the CarController class.
 */
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class CarControllerTest {

	private static final String TEST_JSON_PREFIX_EMBEDDED_ARRAY_RECORD = "$._embedded.carList[0]";

	private static final String TEST_JSON_PREFIX_SINGLE_RECORD = "$";

	private static final int TEST_UPDATE_MILEAGE = 600;

	private static final Condition TEST_UPDATE_CONDITION = Condition.NEW;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private JacksonTester<Car> json;

	@MockBean
	private CarService carService;

	@MockBean
	private PriceClient priceClient;

	@MockBean
	private MapsClient mapsClient;

	private Car car;

	/**
	 * Creates pre-requisites for testing, such as an example car.
	 */
	@BeforeEach
	public void beforeEach() {
		car = getCar();
	}

	/**
	 * Tests for successful creation of new car in the system
	 * @throws Exception when car creation fails in the system
	 */
	@Test
	public void createCar() throws Exception {
		given(carService.save(car)).willReturn(car);

		ResultActions resultActions = mvc.perform(
				post(new URI("/cars"))
				.content(json.write(car).getJson())
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isCreated());
		
		validateCarResult(resultActions, car, TEST_JSON_PREFIX_SINGLE_RECORD);
	}

	@Test
	public void canUpdateCar() throws Exception {
		Car updatedCar = getCar();
		updatedCar.setCondition(TEST_UPDATE_CONDITION);
		updatedCar.getDetails().setMileage(TEST_UPDATE_MILEAGE);

		given(carService.save(updatedCar)).willReturn(updatedCar);

		ResultActions resultActions = mvc.perform(
				put(new URI("/cars/1"))
				.content(json.write(updatedCar).getJson())
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
		
		validateCarResult(resultActions, updatedCar, TEST_JSON_PREFIX_SINGLE_RECORD);
	}

	/**
	 * Tests if the read operation appropriately returns a list of vehicles.
	 * @throws Exception if the read operation of the vehicle list fails
	 */
	@Test
	public void listCars() throws Exception {
		given(carService.list()).willReturn(Collections.singletonList(car));

		ResultActions resultActions = mvc.perform(
				get(new URI("/cars"))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
		
		validateCarResult(resultActions, car, TEST_JSON_PREFIX_EMBEDDED_ARRAY_RECORD);
	}

	/**
	 * Tests the read operation for a single car by ID.
	 * @throws Exception if the read operation for a single car fails
	 */
	@Test
	public void findCar() throws Exception {
		given(carService.findById(car.getId())).willReturn(car);

		ResultActions resultActions = mvc.perform(
				get(new URI("/cars/1"))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
		
		validateCarResult(resultActions, car, TEST_JSON_PREFIX_SINGLE_RECORD);
	}

	/**
	 * Tests the deletion of a single car by ID.
	 * @throws Exception if the delete operation of a vehicle fails
	 */
	@Test
	public void deleteCar() throws Exception {
		given(carService.findById(car.getId())).willThrow(new CarNotFoundException(CarNotFoundException.ERROR_INVALID_CAR_ID + car.getId()));

		mvc.perform(
				delete(new URI("/cars/1"))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());

		mvc.perform(
				get(new URI("/cars/1"))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isNotFound())
		.andExpect(content().string(CarNotFoundException.ERROR_INVALID_CAR_ID + car.getId()));
	}

	/**
	 * Creates an example Car object for use in testing.
	 * @return an example Car object
	 */
	private Car getCar() {
		Car car = new Car();
		car.setId(1L);
		car.setLocation(new Location(40.730610, -73.935242));
		Details details = new Details();
		Manufacturer manufacturer = new Manufacturer(101, "Chevrolet");
		details.setManufacturer(manufacturer);
		details.setModel("Impala");
		details.setMileage(32280);
		details.setExternalColor("white");
		details.setBody("sedan");
		details.setEngine("3.6L V6");
		details.setFuelType("Gasoline");
		details.setModelYear(2018);
		details.setProductionYear(2018);
		details.setNumberOfDoors(4);
		car.setDetails(details);
		car.setCondition(Condition.USED);
		return car;
	}

	private void validateCarResult(ResultActions resultActions, Car car, String prefix) throws Exception {
		resultActions.andExpect(jsonPath(prefix +  ".id").value(car.getId()));
		resultActions.andExpect(jsonPath(prefix +  ".condition").value(car.getCondition().toString()));
		resultActions.andExpect(jsonPath(prefix +  ".details.body").value(car.getDetails().getBody()));
		resultActions.andExpect(jsonPath(prefix +  ".details.model").value(car.getDetails().getModel()));
		resultActions.andExpect(jsonPath(prefix +  ".details.manufacturer.code").value(car.getDetails().getManufacturer().getCode()));
		resultActions.andExpect(jsonPath(prefix +  ".details.manufacturer.name").value(car.getDetails().getManufacturer().getName()));
		resultActions.andExpect(jsonPath(prefix +  ".details.numberOfDoors").value(car.getDetails().getNumberOfDoors()));
		resultActions.andExpect(jsonPath(prefix +  ".details.fuelType").value(car.getDetails().getFuelType()));
		resultActions.andExpect(jsonPath(prefix +  ".details.engine").value(car.getDetails().getEngine()));
		resultActions.andExpect(jsonPath(prefix +  ".details.modelYear").value(car.getDetails().getModelYear()));
		resultActions.andExpect(jsonPath(prefix +  ".details.productionYear").value(car.getDetails().getProductionYear()));
		resultActions.andExpect(jsonPath(prefix +  ".details.externalColor").value(car.getDetails().getExternalColor()));
		resultActions.andExpect(jsonPath(prefix +  ".location.lat").value(car.getLocation().getLat()));
		resultActions.andExpect(jsonPath(prefix +  ".location.lon").value(car.getLocation().getLon()));
	}
}