package com.udacity.jwdnd.course1.cloudstorage.utilities;

import java.security.SecureRandom;
import java.util.Base64;

public class SaltUtility {

	public static String getEncodedSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}

}
