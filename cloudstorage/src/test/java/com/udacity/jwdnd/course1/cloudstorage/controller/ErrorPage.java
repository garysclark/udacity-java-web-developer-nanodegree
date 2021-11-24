package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.udacity.jwdnd.course1.cloudstorage.utilities.WaitUtility;

public class ErrorPage {

	private WaitUtility wait;
	
	@FindBy(id = "error-page-message")
	private WebElement errorMessage;

	@FindBy(id = "error-back-to-home-link")
	private WebElement backToHomeLink;
	
	public ErrorPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		wait = new WaitUtility(driver, 5);
	}

	public void waitForErrorMessage() {
		wait.until(ExpectedConditions.visibilityOf(errorMessage));
	}
	
	public boolean isErrorMessageVisible() {
		return errorMessage.getAttribute("innerHTML") != null;
	}
	
	public void selectBackToHomeLink() {
		backToHomeLink.click();
	}
}
