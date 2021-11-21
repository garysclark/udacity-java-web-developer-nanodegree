package com.udacity.jwdnd.course1.cloudstorage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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

import com.udacity.jwdnd.course1.cloudstorage.controller.HomePageNotesTab;
import com.udacity.jwdnd.course1.cloudstorage.controller.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.controller.ResultsPage;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteTests;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.UserTests;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
public class NoteAcceptanceTests {

	private static final String TEST_NOTE_TITLE = NoteTests.TEST_NOTE_TITLE;

	private static final String TEST_NOTE_DESCRIPTION = NoteTests.TEST_NOTE_DESCRIPTION;

	private static final String TEST_EDITED_NOTE_TITLE = NoteTests.TEST_EDITED_NOTE_TITLE;

	private static final String TEST_EDITED_NOTE_DESCRIPTION = NoteTests.TEST_EDITED_NOTE_DESCRIPTION;

	private static WebDriver driver;

	@LocalServerPort
	private Integer port;

	@Autowired
	private UserService userService;

	private LoginPage loginPage;

	private HomePageNotesTab notesTab;

	private ResultsPage resultsPage;

	private User user;

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
		resultsPage = new ResultsPage(driver);
		user = UserTests.getTestUser_1();
		userService.createUser(user);
		loginPage.login(user.getUsername(), user.getPassword());

		notesTab.selectNotesTab();
		notesTab.createNote(TEST_NOTE_TITLE, TEST_NOTE_DESCRIPTION);
		handleSuccessResult();
	}

	@Test
	public void canCreateNote() {
		List<Note> notes = notesTab.getNotes();
		assertEquals(1, notes.size());
		Note note = notes.get(0);
		assertEquals(TEST_NOTE_TITLE, note.getTitle());
		assertEquals(TEST_NOTE_DESCRIPTION, note.getDescription());
	}

	@Test
	public void canEditNote() {
		notesTab.editNote(0, TEST_EDITED_NOTE_TITLE, TEST_EDITED_NOTE_DESCRIPTION);
		handleSuccessResult();
		List<Note> notes = notesTab.getNotes();
		Note note = notes.get(0);
		assertEquals(TEST_EDITED_NOTE_TITLE, note.getTitle());
		assertEquals(TEST_EDITED_NOTE_DESCRIPTION, note.getDescription());
	}

	@Test
	public void canDeleteNote() {
		notesTab.createNote(TEST_EDITED_NOTE_TITLE, TEST_EDITED_NOTE_DESCRIPTION);
		handleSuccessResult();
		List<Note> notes = notesTab.getNotes();
		assertEquals(2, notes.size());
		notesTab.deleteNote(1);
		handleSuccessResult();
		notes = notesTab.getNotes();
		assertEquals(1, notes.size());
		Note note = notes.get(0);
		assertNotEquals(TEST_EDITED_NOTE_TITLE, note.getTitle());
		assertNotEquals(TEST_EDITED_NOTE_DESCRIPTION, note.getDescription());
	}

	@Test
	public void canSeeNotesAfterLogoutLogin() {
		notesTab.logout();
		loginPage.waitForLoginPage();
		loginPage.login(user.getUsername(),user.getPassword());
		notesTab.selectNotesTab();
		assertEquals(1, notesTab.getNotes().size());
	}

	private void handleSuccessResult() {
		resultsPage.waitForSuccessResultPage();
		resultsPage.selectSuccessContinueLink();
		notesTab.waitForNotesTab();
	}
}
