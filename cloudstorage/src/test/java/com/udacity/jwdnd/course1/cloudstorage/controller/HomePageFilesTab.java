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

	@FindBy (id = "fileTable")
	private WebElement fileTable;

	@FindBy(id = "delete-file-confirm-button")
	private WebElement deleteConfirmButton;

	public HomePageFilesTab(WebDriver driver) {
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

	public void setFileName(String fileName) {
		fileSelectorControl.sendKeys(fileName);
	}

	public void selectUpload() {
		fileUploadButton.click();
	}

	public List<String> getFileNames() {
		ArrayList<String> fileNames = new ArrayList<>();
		List<WebElement> elements = fileTable.findElements(By.tagName("tbody"));
		for(WebElement trElement:elements) {
			String fileName = trElement.findElement(By.id("table-fileName")).getText();
			fileNames.add(fileName);
		}
		return fileNames;
	}

	public void waitForFilesTab() {
		wait.until(ExpectedConditions.elementToBeClickable(fileUploadButton));
	}

	public void deleteFile(int fileNumber) {
		WebElement deleteButton = getDeleteButtonForFile(fileNumber);
		deleteButton.click();
		deleteConfirmButton.click();
	}

	private WebElement getDeleteButtonForFile(int fileNumber) {
		List<WebElement> elements = fileTable.findElements(By.tagName("button"));
		return elements.get(fileNumber);
	}

	public void viewFile(int i) {
		List<WebElement> elements = fileTable.findElements(By.id("view-file-button"));
		elements.get(i).click();
	}

}
