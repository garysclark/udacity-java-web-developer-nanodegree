package com.udacity.jwdnd.course1.cloudstorage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import com.udacity.jwdnd.course1.cloudstorage.controller.HomePageNotesTab;
import com.udacity.jwdnd.course1.cloudstorage.controller.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.controller.SignupController;
import com.udacity.jwdnd.course1.cloudstorage.controller.SignupPage;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.UserTests;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
public class SignupAndLoginAcceptanceTests {

	private static WebDriver driver;

	@LocalServerPort
	private Integer port;
	
	private LoginPage loginPage;

	private HomePageNotesTab homePage;

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
		homePage = new HomePageNotesTab(driver);
		loginPage = new LoginPage(driver);
		signupPage = new SignupPage(driver);
	}
	
	@Test
	public void verifyHomePageNotAccessibleWOLogin() {
		driver.get("http://localhost:" + port + "/home");
		assertFalse(homePage.isPageReady());
		assertTrue(loginPage.isPageReady());
	}
	
	@Test
	public void canSignupLoginLogoutUser() {
		driver.get("http://localhost:" + port + "/signup");
		assertTrue(signupPage.isPageReady());

		User user = UserTests.getTestUser_1();
		signupPage.signupUser(user.getFirstName(),user.getLastName(),user.getUsername(),user.getPassword());
		loginPage.waitForLoginPage();
		assertTrue(loginPage.isSuccessMessageVisible());
		
		loginPage.login(user.getUsername(), user.getPassword());
		homePage.waitForHomePage();
		assertTrue(homePage.isPageReady());
		
		homePage.logout();
		assertFalse(homePage.isPageReady());
		assertTrue(loginPage.isPageReady());
		driver.get("http://localhost:" + port + "/home");
		assertTrue(loginPage.isPageReady());
	}
	
	@Test
	public void canSelectSignupLink() {
		driver.get("http://localhost:" + port + "/login");
		loginPage.selectSignupLink();
		assertTrue(signupPage.isPageReady());
	}
	
	@Test
	public void canSelectLoginLink() {
		driver.get("http://localhost:" + port + "/signup");
		signupPage.selectLoginPageLink();
		assertTrue(loginPage.isPageReady());
	}

	// Error Handling
	@Test
	public void canDetectInvalidUser() {
		driver.get("http://localhost:" + port + "/login");
		User user = UserTests.getTestUser_1();
		loginPage.login(user.getUsername(),user.getPassword());
		loginPage.waitForLoginPage();
		assertTrue(loginPage.isErrorMessageVisible());
	}
	
	@Test
	public void canPreventDuplicateUsername() {
		driver.get("http://localhost:" + port + "/signup");
		User user = UserTests.getTestUser_1();
		signupPage.signupUser(user.getFirstName(),user.getLastName(),user.getUsername(),user.getPassword());
		loginPage.waitForLoginPage();
		loginPage.selectSignupLink();
		signupPage.waitForSignupPage();
		signupPage.signupUser(user.getFirstName(),user.getLastName(),user.getUsername(),user.getPassword());
		assertTrue(signupPage.isErrorMessageVisible());
		assertEquals(SignupController.SIGNUP_ERROR_MESSAGE_USERNAME_ALREADY_EXISTS, signupPage.getErrorMessage());
	}
}
