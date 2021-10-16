package com.udacity.jwdnd.course1.cloudstorage.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.UserTests;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
public class HomePageFilesTabTests {

	private static ChromeDriver driver;

	@LocalServerPort
	private Integer port;

	@Autowired
	private UserService userService;

	private HomePageFilesTab filesTab;

	private LoginPage loginPage;

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
		filesTab = new HomePageFilesTab(driver);
		loginPage = new LoginPage(driver);
		user = UserTests.getTestUser_1();
		userService.createUser(user);
		loginPage.login(user.getUsername(),user.getPassword());
		filesTab.selectFilesTab();
	}
	
	@Test
	public void canAccessFilesTab() {
		assertTrue(filesTab.isReady());
	}
}
