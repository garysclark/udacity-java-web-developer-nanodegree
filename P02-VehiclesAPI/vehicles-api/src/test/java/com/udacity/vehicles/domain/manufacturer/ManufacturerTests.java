package com.udacity.vehicles.domain.manufacturer;

public class ManufacturerTests {

	private static final Integer TEST_CODE_1 = 1;
	private static final String TEST_NAME_1 = "Chevrolet";
	private static final Integer TEST_CODE_2 = 2;
	private static final String TEST_NAME_2 = "Ram";

	public static Manufacturer getTestManufacturer_1() {
		return new Manufacturer(TEST_CODE_1, TEST_NAME_1);
	}

	public static Manufacturer getTestManufacturer_2() {
		return new Manufacturer(TEST_CODE_2, TEST_NAME_2);
	}

}
