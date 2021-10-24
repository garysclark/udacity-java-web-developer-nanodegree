package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.udacity.jwdnd.course1.cloudstorage.utilities.WaitUtility;

public class ResultsPage {

	private WaitUtility wait;

	@FindBy (id = "success-continue-link")
	private WebElement successContinueLink;

	@FindBy (id = "success-message")
	private WebElement successMessage;

	@FindBy (id = "error-continue-link")
	private WebElement errorContinueLink;

	@FindBy (id = "error-message")
	private WebElement errorMessage;

	public ResultsPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		wait = new WaitUtility(driver, 5);
	}

	public void waitForSuccessResultPage() {
		wait.until(ExpectedConditions.visibilityOf(successContinueLink));
	}

	public void selectSuccessContinueLink() {
		successContinueLink.click();
	}

	public String getSuccessMessage() {
		return successMessage.getText();
	}

	public void waitForErrorResultPage() {
		wait.until(ExpectedConditions.visibilityOf(errorContinueLink));
	}

	public String getErrorMessage() {
		return errorMessage.getText();
	}

	public void selectErrorContinueLink() {
		errorContinueLink.click();
	}

}
