package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.utilities.WaitUtility;

public class HomePageCredentialsTab {

	private JavascriptExecutor jse;
	private WaitUtility wait;

	@FindBy (id = "nav-credentials-tab")
	private WebElement credentialsTab;

	@FindBy (id = "add-credential-btn")
	private WebElement addCredentialButton;

	@FindBy (id = "credential-url")
	private WebElement credentialUrl;

	@FindBy (id = "credential-username")
	private WebElement credentialUsername;

	@FindBy (id = "credential-password")
	private WebElement credentialPassword;

	@FindBy (id = "save-credential-btn")
	private WebElement saveChangesButton;

	@FindBy (id = "credentialTable")
	private WebElement credentialTable;

	@FindBy (id = "delete-credentials-confirm-button")
	private WebElement deleteConfirmButton;

	public HomePageCredentialsTab(WebDriver driver) {
		PageFactory.initElements(driver, this);
		jse = (JavascriptExecutor)driver;
		wait = new WaitUtility(driver, 5);
	}

	public void selectTab() {
		jse.executeScript("arguments[0].click()", credentialsTab);
		wait.until(ExpectedConditions.elementToBeClickable(addCredentialButton));
	}

	public boolean isReady() {
		return addCredentialButton.isDisplayed();
	}

	public void createCredential(String url, String username, String password) {
		addCredentialButton.click();
		credentialUrl.sendKeys(url);
		credentialUsername.sendKeys(username);
		credentialPassword.sendKeys(password);
		saveChangesButton.click();
	}

	public List<Credential> getCredentials() {
		ArrayList<Credential> credentials = new ArrayList<>();
		List<WebElement> elements = credentialTable.findElements(By.tagName("tbody"));
		for(WebElement trElement:elements) {
			String url = trElement.findElement(By.id("table-credential-url")).getText();
			String username = trElement.findElement(By.id("table-credential-username")).getText();
			String password = trElement.findElement(By.id("table-credential-password")).getText();
			Credential credential = new Credential(null, url, username, null, password, null);
			credentials.add(credential);
		}
		return credentials;
	}

	public void waitForCredentialsTab() {
		wait.until(ExpectedConditions.elementToBeClickable(addCredentialButton));
	}

	public void editCredential(int i, String url, String username, String password) {
		List<WebElement> elements = credentialTable.findElements(By.id("edit-credential-button"));
		elements.get(i).click();
		credentialUrl.clear();
		credentialUrl.sendKeys(url);
		credentialUsername.clear();
		credentialUsername.sendKeys(username);
		credentialPassword.clear();
		credentialPassword.sendKeys(password);
		saveChangesButton.click();
	}

	public void deleteCredential(int i) {
		List<WebElement> elements = credentialTable.findElements(By.id("delete-credential-button"));
		elements.get(i).click();
		deleteConfirmButton.click();
	}

}
