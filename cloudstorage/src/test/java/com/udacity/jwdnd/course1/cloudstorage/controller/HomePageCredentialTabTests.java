package com.udacity.jwdnd.course1.cloudstorage.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

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

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.UserTests;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
public class HomePageCredentialTabTests {
	private static final String TEST_URL = "http://testurl.com";

	private static final String TEST_USERNAME = "testusername";

	private static final String TEST_PASSWORD = "testpassword";

	private static final String TEST_URL_2 = "http://testurl2.com";

	private static final String TEST_USERNAME_2 = "testusername2";

	private static final String TEST_PASSWORD_2 = "testpassword2";

	private static ChromeDriver driver;

	@LocalServerPort
	private Integer port;

	private HomePageCredentialsTab credentialsTab;

	private LoginPage loginPage;

	private User user;

	private ResultsPage resultsPage;

	@Autowired
	private UserService userService;

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
	public void beforeEach(){
		driver.get("http://localhost:" + port + "/home");
		credentialsTab = new HomePageCredentialsTab(driver);
		loginPage = new LoginPage(driver);
		resultsPage = new ResultsPage(driver);
		
		user = UserTests.getTestUser_1();
		userService.createUser(user);
		loginPage.login(user.getUsername(),user.getPassword());
		credentialsTab.selectTab();
		credentialsTab.addCredential(TEST_URL, TEST_USERNAME, TEST_PASSWORD);
		handleSuccessResult();
	}
	
	@Test
	public void canAccessCredentialsTab() {
		assertTrue(credentialsTab.isReady());
	}
	
	@Test
	public void canAddCredential() {
		List<Credential> credentials = credentialsTab.getCredentials();
		assertEquals(1, credentials.size());
		Credential credential = credentials.get(0);
		assertEquals(TEST_URL, credential.getUrl());
		assertEquals(TEST_USERNAME, credential.getUsername());
		assertNotNull(credential.getPassword());
	}
	
	@Test
	public void canEditCredential() {
		credentialsTab.editCredential(0, TEST_URL_2, TEST_USERNAME_2, TEST_PASSWORD_2);
		handleSuccessResult();
		List<Credential> credentials = credentialsTab.getCredentials();
		assertEquals(1, credentials.size());
		Credential credential = credentials.get(0);
		assertEquals(TEST_URL_2, credential.getUrl());
		assertEquals(TEST_USERNAME_2, credential.getUsername());
		assertNotNull(credential.getPassword());
	}
	
	@Test
	public void canDeleteCredential() {
		credentialsTab.deleteCredential(0);
		handleSuccessResult();
		List<Credential> credentials = credentialsTab.getCredentials();
		assertEquals(0, credentials.size());
	}

	private void handleSuccessResult() {
		resultsPage.waitForSuccessResultPage();
		resultsPage.selectSuccessContinueLink();
		credentialsTab.waitForCredentialsTab();
	}
}
