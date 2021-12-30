package com.udacity.vehicles.domain.car;

import java.time.LocalDateTime;

import com.udacity.vehicles.domain.Condition;
import com.udacity.vehicles.domain.LocationTests;

public class CarTests {

	private static final Long TEST_ID_1 = 99l;
	private static final LocalDateTime TEST_CREATED_AT_1 = LocalDateTime.parse("2007-12-03T10:15:30");
	private static final LocalDateTime TEST_MODIFIED_AT_1 = LocalDateTime.parse("2008-12-03T10:15:30");
	private static final Condition TEST_CONDITION_1 = Condition.NEW;

	private static final Long TEST_ID_2 = 100l;
	private static final LocalDateTime TEST_CREATED_AT_2 = LocalDateTime.parse("1990-12-03T10:15:30");
	private static final LocalDateTime TEST_MODIFIED_AT_2 = LocalDateTime.parse("1991-12-03T10:15:30");
	private static final Condition TEST_CONDITION_2 = Condition.USED;

	public static Car getTestCar_1() {
		Car car = new Car();
		car.setId(TEST_ID_1);
		car.setCreatedAt(TEST_CREATED_AT_1);
		car.setModifiedAt(TEST_MODIFIED_AT_1);
		car.setCondition(TEST_CONDITION_1);
		car.setLocation(LocationTests.getTestLocation_1());
		car.setDetails(DetailsTests.getTestDetails_1());
		return car;
	}

	public static Car getTestCar_2() {
		Car car = new Car();
		car.setId(TEST_ID_2);
		car.setCreatedAt(TEST_CREATED_AT_2);
		car.setModifiedAt(TEST_MODIFIED_AT_2);
		car.setCondition(TEST_CONDITION_2);
		car.setLocation(LocationTests.getTestLocation_2());
		car.setDetails(DetailsTests.getTestDetails_2());
		return car;
	}

	public static boolean isPersistedAttributesEqual(Car car1, Car car2) {
		return (car1.getCondition() == car2.getCondition() &&
				car1.getDetails().equals(car2.getDetails()) &&
				LocationTests.isPersistedAttributesEqual(car1.getLocation(), car2.getLocation())
				);
	}

	public static void copyPersistedAttributes(Car carSrc, Car carDest) {
		carDest.setCondition(carSrc.getCondition());
		carDest.setDetails(carSrc.getDetails());
		LocationTests.copyPersistedAttributes(carSrc.getLocation(),carDest.getLocation());
	}

}
