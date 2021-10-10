package com.udacity.jwdnd.course1.cloudstorage.utilities;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class SaltUtilityTests {

	@Test
	public void canGetSalt() {
		assertNotNull(SaltUtility.getEncodedSalt());
	}
}
