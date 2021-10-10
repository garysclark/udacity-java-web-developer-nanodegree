package com.udacity.jwdnd.course1.cloudstorage.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
public class SignupPageTests {

	@LocalServerPort
	private Integer port;
	
	@Autowired
	private UserService userService;
	
	private static WebDriver driver;
	private SignupPage signupPage;
	private LoginPage loginPage;
	
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
		driver.get("http://localhost:" + port + "/signup");
		signupPage = new SignupPage(driver);
		loginPage = new LoginPage(driver);
	}
	
	@Test
	public void canAccessSignupPage() {
		assertTrue(signupPage.isPageReady());
		assertThrows(org.openqa.selenium.NoSuchElementException.class, () -> {signupPage.isSuccessMessageVisible();});
		assertThrows(org.openqa.selenium.NoSuchElementException.class, () -> {signupPage.isErrorMessageVisible();});
	}
	
	@Test
	public void canSelectLoginPageLink() {
		signupPage.selectLoginPageLink();
		assertTrue(loginPage.isPageReady());
	}
	
	@Test
	public void canSignupUser() {
		User user = UserTests.getTestUser_1();
		signupPage.signupUser(user.getFirstName(),user.getLastName(),user.getUsername(),user.getPassword());
		signupPage.waitForSuccessMessage();
		User newUser = userService.getUser(user.getUsername());
		assertNotNull(newUser);
		assertTrue(signupPage.isSuccessMessageVisible());
		signupPage.selectSuccessLoginLink();
		assertTrue(loginPage.isPageReady());
	}
	
	@Test
	public void canPreventDuplicateUsername() {
		User user = UserTests.getTestUser_1();
		signupPage.signupUser(user.getFirstName(),user.getLastName(),user.getUsername(),user.getPassword());
		signupPage.waitForSuccessMessage();
		signupPage.signupUser(user.getFirstName(),user.getLastName(),user.getUsername(),user.getPassword());
		assertTrue(signupPage.isErrorMessageVisible());
		assertEquals("The username already exists", signupPage.getErrorMessage());
	}
}
