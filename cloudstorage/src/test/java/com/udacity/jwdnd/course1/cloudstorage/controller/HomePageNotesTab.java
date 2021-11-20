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

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.utilities.WaitUtility;

public class HomePageNotesTab {

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

	private WaitUtility wait;

	@FindBy(id = "userTable")
	private WebElement notesTable;

	@FindBy(id = "delete-notes-confirm-button")
	private WebElement deleteConfirmButton;

	@FindBy(id = "logout-btn")
	private WebElement logoutButton;
	
	@FindBy (id = "noteDeleteModalLabel")
	private WebElement deleteModalLabel;

	public HomePageNotesTab(WebDriver driver) {
		PageFactory.initElements(driver, this);
		jse = (JavascriptExecutor)driver;
		wait = new WaitUtility(driver, 5);
	}

	public boolean isPageReady() {
		return pageTitle.getAttribute("innerHTML").equals("Cloud Drive");
	}

	public void selectNotesTab() {
		jse.executeScript("arguments[0].click()", notesTab);
		wait.until(ExpectedConditions.elementToBeClickable(addNoteButton));
	}

	public boolean isAddNoteButtonVisible() {
		return addNoteButton.isDisplayed();
	}

	public void createNote(String title, String description) {
		addNoteButton.click();
		noteTitle.sendKeys(title);
		noteDescription.sendKeys(description);
		saveChangesButton.click();
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

	public void deleteNote(int noteNumber) {
		WebElement deleteButton = getDeleteButtonForNote(noteNumber);
		deleteButton.click();
		deleteConfirmButton.click();
	}

	private WebElement getDeleteButtonForNote(int noteNumber) {
		List<WebElement> elements = notesTable.findElements(By.tagName("tbody"));
		return elements.get(noteNumber).findElement(By.id("delete-note-button"));
	}

	public void logout() {
		logoutButton.click();
	}

	public void editNote(int noteIndex, String title, String description) {
		List<WebElement> elements = notesTable.findElements(By.tagName("tbody"));
		WebElement rowElement = elements.get(noteIndex);
		rowElement.findElement(By.id("edit-note-button")).click();
		noteTitle.clear();
		noteTitle.sendKeys(title);
		noteDescription.clear();
		noteDescription.sendKeys(description);
		saveChangesButton.click();
	}

	public void waitForNotesTab() {
		wait.until(ExpectedConditions.elementToBeClickable(addNoteButton));
	}

	public void waitForHomePage() {
		wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
	}

}
