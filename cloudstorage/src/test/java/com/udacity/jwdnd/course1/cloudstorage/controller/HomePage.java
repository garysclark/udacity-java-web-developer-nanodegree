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
import org.openqa.selenium.support.ui.WebDriverWait;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;

public class HomePage {

	@FindBy(className = "display-5")
	private WebElement pageTitle;

	@FindBy(id = "add-note-btn")
	private WebElement addNoteButton;

	@FindBy(id = "nav-notes-tab")
	private WebElement notesTab;

	private JavascriptExecutor jse;

	@FindBy(id = "note-title")
	private WebElement noteTitle;

	@FindBy(id = "note-description")
	private WebElement noteDescription;

	@FindBy(id = "save-changes-btn")
	private WebElement saveChangesButton;

	private WebDriverWait wait;

	@FindBy(id = "userTable")
	private WebElement notesTable;

	@FindBy(id = "delete-note-button")
	private WebElement deleteNoteButton;

	@FindBy(id = "delete-note-confirm-button")
	private WebElement deleteNoteConfirmButton;

	public HomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		jse = (JavascriptExecutor)driver;
		wait = new WebDriverWait(driver, 5);
	}

	public boolean isPageReady() {
		return pageTitle.getAttribute("innerHTML").equals("Cloud Drive");
	}

	public void selectNotesTab() {
		jse.executeScript("arguments[0].click()", notesTab);
		wait.until(ExpectedConditions.elementToBeClickable(addNoteButton));
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isAddNoteButtonVisible() {
		return addNoteButton.isDisplayed();
	}

	public void createNote(String title, String description) {
		addNoteButton.click();
		noteTitle.sendKeys(title);
		noteDescription.sendKeys(description);
		saveChangesButton.click();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Note> getNotes() {
		ArrayList<Note> notes = new ArrayList<>();
		List<WebElement> elements = notesTable.findElements(By.tagName("tbody"));
		for(WebElement trElement:elements) {
			String title = trElement.findElement(By.id("table-noteTitle")).getText();
			String description = trElement.findElement(By.id("table-noteDescription")).getText();
			Note note = new Note(null, title, description, null);
			notes.add(note);
		}
		return notes;
	}

	public void deleteNote() {
		deleteNoteButton.click();
		deleteNoteConfirmButton.click();
	}

}
