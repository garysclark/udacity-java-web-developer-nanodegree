package com.udacity.jwdnd.course1.cloudstorage.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.UserTests;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
public class HomePageNotesTabTests {

	private static final String TEST_NOTE_TITLE = "Test Note Title";

	private static final String TEST_NOTE_DESCRIPTION = "Test note description.";

	private static final String TEST_EDITED_NOTE_TITLE = "Edited Note Title";

	private static final String TEST_EDITED_NOTE_DESCRIPTION = "Edited Note Description";

	@LocalServerPort
	private Integer port;

	@Autowired
	private UserService userService;

	private HomePageNotesTab notesTab;

	private LoginPage loginPage;

	private User user;

	private static WebDriver driver;

	@BeforeAll
	static public void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@AfterAll
	static public void afterAll() {
		driver.quit();
	}

	@BeforeEach
	public void beforeEach() {
		driver.get("http://localhost:" + port + "/home");
		loginPage = new LoginPage(driver);
		notesTab = new HomePageNotesTab(driver);
		user = UserTests.getTestUser_1();
		userService.createUser(user);
		loginPage.login(user.getUsername(),user.getPassword());

		notesTab.selectNotesTab();
		notesTab.createNote(TEST_NOTE_TITLE, TEST_NOTE_DESCRIPTION);
		notesTab.waitForNotesTab();
	}

	@Test
	public void canAccessHomePage() {
		assertTrue(notesTab.isPageReady());

	}

	@Test
	public void canSelectNotesTab() {
		assertTrue(notesTab.isAddNoteButtonVisible());
	}

	@Test
	public void canCreateNote() {
		List<Note> notes = notesTab.getNotes();
		assertEquals(1, notes.size());
		assertEquals(TEST_NOTE_TITLE, notes.get(0).getTitle());
		assertEquals(TEST_NOTE_DESCRIPTION, notes.get(0).getDescription());
	}

	@Test
	public void canDeleteNote() {
		notesTab.createNote(TEST_NOTE_TITLE, TEST_NOTE_DESCRIPTION);
		notesTab.waitForNotesTab();
		assertEquals(2, notesTab.getNotes().size());
		notesTab.deleteNote(1);
		notesTab.waitForNotesTab();
		assertEquals(1, notesTab.getNotes().size());
	}

	@Test
	public void canSeeNotesAfterLogoutLogin() {
		notesTab.logout();
		loginPage.waitForLoginPage();
		loginPage.login(user.getUsername(),user.getPassword());
		notesTab.selectNotesTab();
		assertEquals(1, notesTab.getNotes().size());
	}

	@Test
	public void canEditNote() {
		notesTab.editNote(0, TEST_EDITED_NOTE_TITLE, TEST_EDITED_NOTE_DESCRIPTION);
		List<Note> notes = notesTab.getNotes();
		assertEquals(TEST_EDITED_NOTE_TITLE, notes.get(0).getTitle());
		assertEquals(TEST_EDITED_NOTE_DESCRIPTION, notes.get(0).getDescription());
	}
}
