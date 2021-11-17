package com.udacity.jwdnd.course1.cloudstorage.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
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

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.UserTests;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
public class HomePageFilesTabTests {

	private static final String TEST_RELATIVE_PATH = "src/main/resources/";

	private static final String TEST_FILENAME_1 = "schema.sql";

	private static final String TEST_FILENAME_2 = "application.properties";

	private static ChromeDriver driver;

	@LocalServerPort
	private Integer port;

	@Autowired
	private UserService userService;
	
	private HomePageFilesTab filesTab;

	private LoginPage loginPage;

	private User user;

	private HomePageNotesTab notesTab;

	private ResultsPage resultsPage;

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
		filesTab = new HomePageFilesTab(driver);
		loginPage = new LoginPage(driver);
		notesTab = new HomePageNotesTab(driver);
		resultsPage = new ResultsPage(driver);
		
		user = UserTests.getTestUser_1();
		userService.createUser(user);
		loginPage.login(user.getUsername(),user.getPassword());

		filesTab.selectFilesTab();
	}
	
	private void uploadFile(String filePath) throws IOException {
		String absolute = new File(filePath).getCanonicalPath();
		filesTab.setFileName(absolute);
		filesTab.selectUpload();
	}
	
	private void uploadValidFile(String filePath) throws IOException {
		uploadFile(filePath);
		handleSuccessResult(FileController.ADD_FILE_SUCCESS_MESSAGE);
	}

	private void handleErrorResult(String message) {
		resultsPage.waitForErrorResultPage();
		assertEquals(message, resultsPage.getErrorMessage());
		resultsPage.selectErrorContinueLink();
		filesTab.waitForFilesTab();
	}

	private void handleSuccessResult(String message) {
		resultsPage.waitForSuccessResultPage();
		assertEquals(message, resultsPage.getSuccessMessage());
		resultsPage.selectSuccessContinueLink();
		filesTab.waitForFilesTab();
	}

	@Test
	public void canAccessFilesTab() {
		assertTrue(filesTab.isReady());
	}
	
	@Test
	public void canUploadFile() throws IOException {
		uploadValidFile(TEST_RELATIVE_PATH + TEST_FILENAME_1);
		List<String> fileNames = filesTab.getFileNames();
		assertEquals(1, fileNames.size());
	}
	
	@Test
	public void canHandleDuplicatFilenameError() throws IOException {
		uploadValidFile(TEST_RELATIVE_PATH + TEST_FILENAME_1);
		uploadFile(TEST_RELATIVE_PATH + TEST_FILENAME_1);
		handleErrorResult(FileController.DUPLICATE_FILENAME_ERROR_MESSAGE);
	}

	@Test
	public void canVerifyFileListPersists() throws IOException {
		uploadValidFile(TEST_RELATIVE_PATH + TEST_FILENAME_1);
		notesTab.logout();
		loginPage.waitForLoginPage();
		loginPage.login(user.getUsername(),user.getPassword());
		filesTab.selectFilesTab();
		List<String> fileNames = filesTab.getFileNames();
		assertEquals(1, fileNames.size());
	}
	
	@Test
	public void canHandleNoFileSelectedError() throws IOException {
		filesTab.selectUpload();
		handleErrorResult(FileController.ADD_NO_FILE_SELECTED_ERROR_MESSAGE);
	}
	
	@Test
	public void canDeleteFile() throws IOException {
		uploadValidFile(TEST_RELATIVE_PATH + TEST_FILENAME_1);
		uploadValidFile(TEST_RELATIVE_PATH + TEST_FILENAME_2);
		assertEquals(2, filesTab.getFileNames().size());
		filesTab.deleteFile(0);
		handleSuccessResult(FileController.DELETE_FILE_SUCCESS_MESSAGE);
		assertEquals(1, filesTab.getFileNames().size());
	}
	
	@Test
	public void canViewFile() throws IOException {
		uploadValidFile(TEST_RELATIVE_PATH + TEST_FILENAME_1);
		filesTab.viewFile(0);
		String downloadedFileName = filesTab.getFileNames().get(0);
		assertTrue(isFilePresentInDownloadDirectory(downloadedFileName));
	}

	private boolean isFilePresentInDownloadDirectory(String downloadedFileName) throws IOException {
		int extensionStart = downloadedFileName.lastIndexOf(".");
		String baseFileName = downloadedFileName.substring(0, extensionStart);
		String myHomePath = System.getProperty("user.home");
		File folder = new File(myHomePath + "/Downloads");
		for (final File fileEntry : folder.listFiles()) {
			if (!fileEntry.isDirectory()) {
				Path path = Paths.get(fileEntry.getCanonicalPath());
				BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
				if(fileEntry.getName().contains(baseFileName)) {
					if(attributes.creationTime().compareTo(FileTime.from(Instant.now().minusSeconds(1))) > 0) {
						return true;
					}
				}
			}
		}	
		
		return false;
	}
	
}
