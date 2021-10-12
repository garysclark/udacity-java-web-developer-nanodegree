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
public class HomePageTests {

	private static final String TEST_NOTE_TITLE = "Test Note Title";

	private static final String TEST_NOTE_DESCRIPTION = "Test note description.";

	@LocalServerPort
	private Integer port;
	
	@Autowired
	private UserService userService;

	private HomePage homePage;

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
		homePage = new HomePage(driver);
		user = UserTests.getTestUser_1();
		userService.createUser(user);
		loginPage.login(user.getUsername(),user.getPassword());
	}
	
	@Test
	public void canAccessHomePage() {
		assertTrue(homePage.isPageReady());

	}
	
	@Test
	public void canSelectNotesTab() {
		homePage.selectNotesTab();
		assertTrue(homePage.isAddNoteButtonVisible());
	}
	
	@Test
	public void canCreateNote() {
		homePage.selectNotesTab();
		homePage.createNote(TEST_NOTE_TITLE, TEST_NOTE_DESCRIPTION);
		List<Note> notes = homePage.getNotes();
		assertEquals(1, notes.size());
		assertEquals(TEST_NOTE_TITLE, notes.get(0).getNotetitle());
		assertEquals(TEST_NOTE_DESCRIPTION, notes.get(0).getNotedescription());
	}

}
