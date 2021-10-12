package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

	@FindBy(className = "display-5")
	private WebElement pageTitle;

	@FindBy(id = "add-note-btn")
	private WebElement addNoteButton;

	@FindBy(id = "nav-notes-tab")
	private WebElement notesTab;

	private JavascriptExecutor jse;

	public HomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		jse = (JavascriptExecutor)driver;
	}

	public boolean isPageReady() {
		return pageTitle.getAttribute("innerHTML").equals("Cloud Drive");
	}

	public void selectNotesTab() {
		jse.executeScript("arguments[0].click()", notesTab);
	}

	public boolean isNotesTabVisible() {
		return addNoteButton.isDisplayed();
	}

}
