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

}
