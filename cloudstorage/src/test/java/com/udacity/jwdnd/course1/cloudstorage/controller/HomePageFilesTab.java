package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.udacity.jwdnd.course1.cloudstorage.utilities.WaitUtility;

public class HomePageFilesTab {

	private WaitUtility wait;
	private JavascriptExecutor jse;
	
	@FindBy (id = "nav-files-tab")
	private WebElement filesTab;

	@FindBy (id = "file-upload-btn")
	private WebElement fileUploadButton;

	@FindBy (id = "fileUpload")
	private WebElement fileSelectorControl;

	public HomePageFilesTab(ChromeDriver driver) {
		PageFactory.initElements(driver, this);
		jse = (JavascriptExecutor)driver;
		wait = new WaitUtility(driver, 5);
	}

	public boolean isReady() {
		return fileUploadButton.isDisplayed();
	}

	public void selectFilesTab() {
		jse.executeScript("arguments[0].click()", filesTab);
		wait.until(ExpectedConditions.elementToBeClickable(fileUploadButton));
	}

	public String selectFile() {
		fileSelectorControl.click();
		return null;
	}

}
