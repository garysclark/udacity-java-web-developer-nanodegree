package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.udacity.jwdnd.course1.cloudstorage.utilities.WaitUtility;

public class LoginPage {

	@FindBy(className = "display-5")
	private WebElement pageTitle;

	@FindBy(id = "error-msg")
	private WebElement errorMessage;

	@FindBy(id = "logout-msg")
	private WebElement logoutMessage;

	@FindBy(id = "inputUsername")
	private WebElement inputUsername;

	@FindBy(id = "inputPassword")
	private WebElement inputPassword;

	@FindBy(id = "submit-button")
	private WebElement submitButton;

	@FindBy(id = "signup-page-link")
	private WebElement signupPageLink;

	private WaitUtility wait;

	public LoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		wait = new WaitUtility(driver, 5);
	}

	public boolean isPageReady() {
		return pageTitle.getAttribute("innerHTML").equals("Login");
	}

	public boolean isErrorMessageVisible() {
		return errorMessage.getAttribute("innerHTML") != null;
	}

	public boolean isLogoutMessageVisible() {
		return logoutMessage.getAttribute("innerHTML") != null;
	}

	public void login(String username, String password) {
		inputUsername.sendKeys(username);
		inputPassword.sendKeys(password);
		submitButton.click();
	}

	public void selectSetupLink() {
		signupPageLink.click();
	}

	public void waitForLoginPage() {
		wait.until(ExpectedConditions.visibilityOf(pageTitle));
	}

}
