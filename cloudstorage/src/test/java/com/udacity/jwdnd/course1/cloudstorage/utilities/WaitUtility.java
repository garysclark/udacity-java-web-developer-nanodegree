package com.udacity.jwdnd.course1.cloudstorage.utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtility {

	private WebDriverWait wait;

	public WaitUtility(WebDriver webDriver, Integer timeout) {
		this.wait = new WebDriverWait(webDriver, timeout);
	}

	public void until(ExpectedCondition<WebElement> expectedCondition) {
		wait.until(expectedCondition);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void until(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
