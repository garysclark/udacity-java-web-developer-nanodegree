package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
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

}
