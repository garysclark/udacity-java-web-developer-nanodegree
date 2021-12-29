package com.udacity.vehicles.domain;

public class LocationTests {
	private static final Double TEST_LATTITUDE_1 = 50.0;
	private static final Double TEST_LONGITUDE_1 = 60.0;
	private static final String TEST_ADDRESS_1 = "Test Address 1";
	private static final String TEST_CITY_1 = "Test City 1";
	private static final String TEST_STATE_1 = "Test State 1";
	private static final String TEST_ZIP_1 = "Test zip 1";

	private static final Double TEST_LATTITUDE_2 = 70.0;
	private static final Double TEST_LONGITUDE_2 = 80.0;
	private static final String TEST_ADDRESS_2 = "Test Address 2";
	private static final String TEST_CITY_2 = "Test City 2";
	private static final String TEST_STATE_2 = "Test State 2";
	private static final String TEST_ZIP_2 = "Test zip 2";

	public static Location getTestLocation_1() {
		Location location = new Location(TEST_LATTITUDE_1, TEST_LONGITUDE_1);
		location.setAddress(TEST_ADDRESS_1);
		location.setCity(TEST_CITY_1);
		location.setState(TEST_STATE_1);
		location.setZip(TEST_ZIP_1);
		return location;
	}

	public static Location getTestLocation_2() {
		Location location = new Location(TEST_LATTITUDE_2, TEST_LONGITUDE_2);
		location.setAddress(TEST_ADDRESS_2);
		location.setCity(TEST_CITY_2);
		location.setState(TEST_STATE_2);
		location.setZip(TEST_ZIP_2);
		return location;
	}

}
