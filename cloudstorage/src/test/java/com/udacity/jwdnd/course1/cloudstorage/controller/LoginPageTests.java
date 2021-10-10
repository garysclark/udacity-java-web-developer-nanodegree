package com.udacity.jwdnd.course1.cloudstorage.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.UserTests;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
public class LoginPageTests {

	private static WebDriver driver;

	@LocalServerPort
	private Integer port;

	@Autowired
	private UserService userService;
	
	private LoginPage loginPage;

	private HomePage homePage;

	private SignupPage signupPage;
	
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
		driver.get("http://localhost:" + port + "/login");
		loginPage = new LoginPage(driver);
		homePage = new HomePage(driver);
		signupPage = new SignupPage(driver);
	}
	
	@Test
	public void canAccessPage() {
		assertTrue(loginPage.isPageReady());
		assertThrows(org.openqa.selenium.NoSuchElementException.class, () -> {loginPage.isErrorMessageVisible();});
		assertThrows(org.openqa.selenium.NoSuchElementException.class, () -> {loginPage.isLogoutMessageVisible();});
	}
	
	@Test
	public void canLogin() {
		// create the user to login
		User user = UserTests.getTestUser_1();
		userService.createUser(user);
		
		loginPage.login(user.getUsername(),user.getPassword());
		assertTrue(homePage.isPageReady());
	}
	
	@Test
	public void canSelectSetupLink() {
		loginPage.selectSetupLink();
		assertTrue(signupPage.isPageReady());
	}
	
	@Test
	public void canDetectInvalidUser() {
		User user = UserTests.getTestUser_1();
		loginPage.login(user.getUsername(),user.getPassword());
		assertTrue(loginPage.isErrorMessageVisible());
	}
}
