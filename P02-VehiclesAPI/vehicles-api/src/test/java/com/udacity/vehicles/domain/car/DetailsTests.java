package com.udacity.vehicles.domain.car;

import com.udacity.vehicles.domain.manufacturer.ManufacturerTests;

public class DetailsTests {
	private static final String TEST_BODY_1 = "test body 1";
	private static final String TEST_MODEL_1 = "tet model 1";
	private static final Integer TEST_NUMBER_OF_DOORS_1 = 2;
	private static final String TEST_FUEL_TYPE_1 = "test fuel type 1";
	private static final String TEST_ENGINE_1 = "test engine 1";
	private static final Integer TEST_MILEAGE_1 = 555;
	private static final Integer TEST_MODEL_YEAR_1 = 2018;
	private static final Integer TEST_PRODUCTION_YEAR_1 = 2020;
	private static final String TEST_EXTERNAL_COLOR_1 = "test color 1";

	private static final String TEST_BODY_2 = "test body 2";
	private static final String TEST_MODEL_2 = "tet model 2";
	private static final Integer TEST_NUMBER_OF_DOORS_2 = 4;
	private static final String TEST_FUEL_TYPE_2 = "test fuel type 2";
	private static final String TEST_ENGINE_2 = "test engine 2";
	private static final Integer TEST_MILEAGE_2 = 100500;
	private static final Integer TEST_MODEL_YEAR_2 = 1990;
	private static final Integer TEST_PRODUCTION_YEAR_2 = 1991;
	private static final String TEST_EXTERNAL_COLOR_2 = "test color 2";

	public static Details getTestDetails_1() {
		Details details = new Details();
		details.setBody(TEST_BODY_1);
		details.setEngine(TEST_ENGINE_1);
		details.setExternalColor(TEST_EXTERNAL_COLOR_1);
		details.setFuelType(TEST_FUEL_TYPE_1);
		details.setManufacturer(ManufacturerTests.getTestManufacturer_1());
		details.setMileage(TEST_MILEAGE_1);
		details.setModel(TEST_MODEL_1);
		details.setModelYear(TEST_MODEL_YEAR_1);
		details.setNumberOfDoors(TEST_NUMBER_OF_DOORS_1);
		details.setProductionYear(TEST_PRODUCTION_YEAR_1);
		return details;
	}

	public static Details getTestDetails_2() {
		Details details = new Details();
		details.setBody(TEST_BODY_2);
		details.setEngine(TEST_ENGINE_2);
		details.setExternalColor(TEST_EXTERNAL_COLOR_2);
		details.setFuelType(TEST_FUEL_TYPE_2);
		details.setManufacturer(ManufacturerTests.getTestManufacturer_2());
		details.setMileage(TEST_MILEAGE_2);
		details.setModel(TEST_MODEL_2);
		details.setModelYear(TEST_MODEL_YEAR_2);
		details.setNumberOfDoors(TEST_NUMBER_OF_DOORS_2);
		details.setProductionYear(TEST_PRODUCTION_YEAR_2);
		return details;
	}

}
