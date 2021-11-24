package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.udacity.jwdnd.course1.cloudstorage.utilities.WaitUtility;

public class SignupPage {

	@FindBy(className = "display-5")
	private WebElement pageTitle;

	@FindBy(id = "error-msg")
	private WebElement errorMessage;

	@FindBy(id = "inputFirstName")
	private WebElement firstNameField;

	@FindBy(id = "inputLastName")
	private WebElement lastNameField;

	@FindBy(id = "inputUsername")
	private WebElement usernameField;

	@FindBy(id = "inputPassword")
	private WebElement passwordField;

	@FindBy(id = "submit-button")
	private WebElement submitButton;

	@FindBy(id = "back-to-login-link")
	private WebElement backToLoginLink;

	private WaitUtility wait;

	@FindBy(id = "signup-login-link")
	private WebElement signupLoginLink;

	public SignupPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		wait = new WaitUtility(driver, 5);
	}

	public boolean isPageReady() {
		return pageTitle.getAttribute("innerHTML").equals("Sign Up");
	}

	public boolean isErrorMessageVisible() {
		return errorMessage.getAttribute("innerHTML") != null;
	}

	public void signupUser(String firstName, String lastName, String username, String password) {
		firstNameField.sendKeys(firstName);
		lastNameField.sendKeys(lastName);
		usernameField.sendKeys(username);
		passwordField.sendKeys(password);
		submitButton.click();
	}

	public void selectLoginPageLink() {
		backToLoginLink.click();
	}

	public void selectSuccessLoginLink() {
		signupLoginLink.click();
	}

	public String getErrorMessage() {
		return errorMessage.getAttribute("innerHTML");
	}

	public void waitForSignupPage() {
		wait.until(ExpectedConditions.visibilityOf(pageTitle));
	}

}
