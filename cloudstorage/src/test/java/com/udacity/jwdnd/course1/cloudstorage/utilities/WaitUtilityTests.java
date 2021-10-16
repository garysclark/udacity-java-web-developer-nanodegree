package com.udacity.jwdnd.course1.cloudstorage.utilities;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class WaitUtilityTests {
	
	private WebElement webElement;
	
	private static final Integer TEST_TIMEOUT = 1;
	private WebDriver driver;
	
	@BeforeEach
	public void beforeEach() {
		webElement = mock(WebElement.class);
		driver = mock(WebDriver.class);
	}
	
	@Test
	public void canCreateWaitUtility() {
		WaitUtility waitUtility = new WaitUtility(driver, TEST_TIMEOUT);
		assertNotNull(waitUtility);
	}
	
	@Test
	public void canWaitForTimeout() {
		WaitUtility wait = new WaitUtility(driver, TEST_TIMEOUT);
		assertThrows(TimeoutException.class, () -> {wait.until(ExpectedConditions.visibilityOf(webElement));});
	}
}
