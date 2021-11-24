package com.udacity.jwdnd.course1.cloudstorage;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import com.udacity.jwdnd.course1.cloudstorage.controller.ErrorPage;
import com.udacity.jwdnd.course1.cloudstorage.controller.HomePageNotesTab;
import com.udacity.jwdnd.course1.cloudstorage.controller.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.UserTests;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
public class MiscAcceptanceTests {

	private static WebDriver driver;

	@LocalServerPort
	private Integer port;

	@Autowired
	private UserService userService;
	
	private LoginPage loginPage;

	private ErrorPage errorPage;

	private HomePageNotesTab homePage;


	@BeforeAll
	static public void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}
	
	@AfterAll
	static public void afterAll() {
		driver.quit();
	}
	
	@Test
	public void canHandleInvalidUrl() {
		driver.get("http://localhost:" + port + "/login");
		loginPage = new LoginPage(driver);
		errorPage = new ErrorPage(driver);
		homePage = new HomePageNotesTab(driver);
		
		User user = UserTests.getTestUser_1();
		userService.createUser(user);
		loginPage.login(user.getUsername(),user.getPassword());
		
		driver.get("http://localhost:" + port + "/file/upload");
		errorPage.waitForErrorMessage();
		assertTrue(errorPage.isErrorMessageVisible());
		errorPage.selectBackToHomeLink();
		homePage.waitForHomePage();
		assertTrue(homePage.isPageReady());
	}

}
