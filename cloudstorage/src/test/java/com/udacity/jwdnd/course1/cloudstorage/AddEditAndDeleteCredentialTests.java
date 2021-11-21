package com.udacity.jwdnd.course1.cloudstorage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

import com.udacity.jwdnd.course1.cloudstorage.controller.HomePageCredentialsTab;
import com.udacity.jwdnd.course1.cloudstorage.controller.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.controller.ResultsPage;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialTests;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.UserTests;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
public class AddEditAndDeleteCredentialTests {

	private static final String TEST_URL_1 = CredentialTests.TEST_URL_1;

	private static final String TEST_USERNAME_1 = CredentialTests.TEST_USERNAME_1;

	private static final String TEST_PASSWORD_1 = CredentialTests.TEST_PASSWORD_1;

	private static final String TEST_URL_2 = CredentialTests.TEST_URL_2;

	private static final String TEST_USERNAME_2 = CredentialTests.TEST_USERNAME_2;

	private static final String TEST_PASSWORD_2 = CredentialTests.TEST_PASSWORD_2;

	private static WebDriver driver;

	@LocalServerPort
	private Integer port;

	@Autowired
	private UserService userService;
	
	private LoginPage loginPage;

	private ResultsPage resultsPage;

	private HomePageCredentialsTab credentialsTab;


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
		credentialsTab = new HomePageCredentialsTab(driver);
		resultsPage = new ResultsPage(driver);
		User user = UserTests.getTestUser_1();
		userService.createUser(user);
		loginPage.login(user.getUsername(),user.getPassword());

		credentialsTab.selectTab();
		credentialsTab.createCredential(TEST_URL_1, TEST_USERNAME_1, TEST_PASSWORD_1);
		handleSuccessResult();
	}

	private void handleSuccessResult() {
		resultsPage.waitForSuccessResultPage();
		resultsPage.selectSuccessContinueLink();
		credentialsTab.waitForCredentialsTab();
	}
	
	@Test
	public void canCreateCredential() {
		List<Credential> credentials = credentialsTab.getCredentials();
		assertEquals(1, credentials.size());
		Credential credential = credentials.get(0);
		assertEquals(TEST_URL_1, credential.getUrl());
		assertEquals(TEST_USERNAME_1, credential.getUsername());
		assertNotNull(credential.getPassword());
	}
	
	@Test
	public void canEditCredential() {
		credentialsTab.editCredential(0, TEST_URL_2, TEST_USERNAME_2, TEST_PASSWORD_2);
		handleSuccessResult();
		List<Credential> credentials = credentialsTab.getCredentials();
		Credential credential = credentials.get(0);
		assertEquals(TEST_URL_2, credential.getUrl());
		assertEquals(TEST_USERNAME_2, credential.getUsername());
		assertNotNull(credential.getPassword());
	}

	@Test
	public void canDeleteCredential() {
		credentialsTab.createCredential(TEST_URL_2, TEST_USERNAME_2, TEST_PASSWORD_2);
		handleSuccessResult();
		List<Credential> credentials = credentialsTab.getCredentials();
		assertEquals(2, credentials.size());
		credentialsTab.deleteCredential(1);
		handleSuccessResult();
		credentials = credentialsTab.getCredentials();
		assertEquals(1, credentials.size());
		Credential credential = credentials.get(0);
		assertEquals(TEST_URL_1, credential.getUrl());
		assertEquals(TEST_USERNAME_1, credential.getUsername());
		assertNotNull(credential.getPassword());
	}
}
