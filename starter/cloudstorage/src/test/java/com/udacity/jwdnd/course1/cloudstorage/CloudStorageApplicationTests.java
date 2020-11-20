package com.udacity.jwdnd.course1.cloudstorage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	
	// User fields
	private String userFirstName = "test";
	private String userLastName = "user";
	private String userUserName = "un";
	private String userPassword = "pw";
	// Note fields
	private String noteTitle = "title";
	private String noteDescription = "description";
	// Credential fields
	private String credentialUrl = "http://localhost:8080/login";
	private String credentialUserName = "un";
	private String credentialPassword = "pw";

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}
	
	@Test
	public void ckUnauthorizedUserAccess() {		
		driver.get("http://localhost:" + this.port + "/login");
		assertEquals("Login", driver.getTitle());
		
		driver.get("http://localhost:" + this.port + "/signup");
		assertEquals("Sign Up", driver.getTitle());
		
		// Unauthorized user cannot access home page
		driver.get("http://localhost:" + this.port + "/home");
		assertEquals("Login", driver.getTitle());
	}
	
	@Test
	public void ckAuthorizedUserAccess() {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) this.driver;
		
		// Signup user
		signupUser(userFirstName, userLastName, userUserName, userPassword);
		
		// Login user
		loginUser(userUserName, userPassword);
		
		// Ck successful login	
		assertTrue(isLoggedIn());
		
		if(isLoggedIn()) {
			// Logout user
			jsExecutor.executeScript("arguments[0].click();", driver.findElement(By.id("logoutButton")));
			
			// Ck logged out user access
			driver.get("http://localhost:" + this.port + "/home");
			assertEquals("Login", driver.getTitle());	
		}	
	}
	
	@Test
	public void ckNoteCreated() {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) this.driver;
		
		// Signup user
		signupUser(userFirstName, userLastName, userUserName, userPassword);
		
		// Login user
		loginUser(userUserName, userPassword);
		
		// Ck successful login		
		if(isLoggedIn()) {
			int initNoteCount = driver.findElements(By.xpath("//table[@id='userTable']/tbody/tr")).size();
			int updatedNoteCount = 0;
			
			createNote(noteTitle, noteDescription);
			
			// Get number of table rows
			updatedNoteCount = driver.findElements(By.xpath("//table[@id='userTable']/tbody/tr")).size();
			
			assertEquals(initNoteCount + 1, updatedNoteCount);
		}	
	}
	
	@Test
	public void ckNoteDeleted() {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) this.driver;
		
		// Signup user
		signupUser(userFirstName, userLastName, userUserName, userPassword);
		
		// Login user
		loginUser(userUserName, userPassword);
		
		// Ck successful login		
		if(isLoggedIn()) {
			List<WebElement> tableRows;
			int initNoteCount = 0;
			int updatedNoteCount = 0;
			
			// Initially no notes exist, need to create one to delete
			createNote(noteTitle, noteDescription);
			
			// Need to find the "Delete" link for a the note and click it to delete note
			tableRows = driver.findElements(By.xpath("//table[@id='userTable']/tbody/tr"));
			initNoteCount = tableRows.size();
			jsExecutor.executeScript("arguments[0].click();", tableRows.get(0).findElement(By.id("link-delete-note")));
			
			// Click link to get back to home page from the results page
			jsExecutor.executeScript("arguments[0].click();", driver.findElement(By.tagName("a")));
			
			// Get updated number of table rows
			updatedNoteCount = driver.findElements(By.xpath("//table[@id='userTable']/tbody/tr")).size();
			
			assertEquals(initNoteCount - 1, updatedNoteCount);
		}	
	}
	
	@Test
	public void ckNoteEdited() {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) this.driver;
		
		// Signup user
		signupUser(userFirstName, userLastName, userUserName, userPassword);
		
		// Login user
		loginUser(userUserName, userPassword);
		
		// Ck successful login		
		if(isLoggedIn()) {
			List<WebElement> initTableRows;
			List<WebElement> updatedTableRows;
			
			// Initially no notes exist, need to create one to edit
			createNote(noteTitle, noteDescription);
			
			// Need to find the "Edit" button and click it to raise edit modal
			initTableRows = driver.findElements(By.xpath("//table[@id='userTable']/tbody/tr"));
			jsExecutor.executeScript("arguments[0].click();", initTableRows.get(0).findElement(By.id("button-edit-note")));
			
			// Edit note
			jsExecutor.executeScript("arguments[0].value='updated " + noteTitle + "';", driver.findElement(By.id("note-title")));
			jsExecutor.executeScript("arguments[0].click();", driver.findElement(By.id("button-note-save")));
			
			// Click link to get back to home page from the results page
			jsExecutor.executeScript("arguments[0].click();", driver.findElement(By.tagName("a")));
			
			updatedTableRows = driver.findElements(By.xpath("//table[@id='userTable']/tbody/tr"));
		
			assertEquals("updated title", updatedTableRows.get(0).findElement(By.id("display-note-title")).getText());	
		}
	}
	
	@Test
	public void ckCredentialCreated() {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) this.driver;
		
		// Signup user
		signupUser(userFirstName, userLastName, userUserName, userPassword);
		
		// Login user
		loginUser(userUserName, userPassword);
		
		// Ck successful login		
		if(isLoggedIn()) {
			int initCredCount = driver.findElements(By.xpath("//table[@id='credentialTable']/tbody/tr")).size();
			int updatedCredCount = 0;
			List<WebElement> updatedTableRows;
			
			createCredential(credentialUrl, credentialUserName, credentialPassword);
			updatedTableRows = driver.findElements(By.xpath("//table[@id='credentialTable']/tbody/tr"));
			
			assertEquals(initCredCount + 1, updatedTableRows.size());
			assertNotEquals(credentialPassword, updatedTableRows.get(0).findElement(By.id("display-cred-password")).getText());
		}	
	}
	
	@Test
	public void ckCredentialDeleted() {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) this.driver;
		
		// Signup user
		signupUser(userFirstName, userLastName, userUserName, userPassword);
		
		// Login user
		loginUser(userUserName, userPassword);
		
		// Ck successful login		
		if(isLoggedIn()) {
			int initCredCount = 0;
			int updatedCredCount = 0;
			List<WebElement> tableRows;
			
			// Initially no credentials exist, need to create one to edit
			createCredential(credentialUrl, credentialUserName, credentialPassword);
			
			// Need to find the "Delete" link for the credential and click it to delete note
			tableRows = driver.findElements(By.xpath("//table[@id='credentialTable']/tbody/tr"));
			initCredCount = tableRows.size();
			jsExecutor.executeScript("arguments[0].click();", tableRows.get(0).findElement(By.id("link-delete-cred")));
			
			// Click link to get back to home page from the results page
			jsExecutor.executeScript("arguments[0].click();", driver.findElement(By.tagName("a")));
			
			// Get updated number of table rows
			updatedCredCount = driver.findElements(By.xpath("//table[@id='credentialTable']/tbody/tr")).size();
			
			assertEquals(initCredCount - 1, updatedCredCount);
		}	
	}
	
	@Test
	public void ckCredentialEdited() {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) this.driver;
		
		// Signup user
		signupUser(userFirstName, userLastName, userUserName, userPassword);
		
		// Login user
		loginUser(userUserName, userPassword);
		
		// Ck successful login		
		if(isLoggedIn()) {
			List<WebElement> tableRows;
			List<WebElement> updatedTableRows;
			
			// Initially no credentials exist, need to create one to edit
			createCredential(credentialUrl, credentialUserName, credentialPassword);
			
			// Need to find the "Edit" button for the credential and click it to edit note
			tableRows = driver.findElements(By.xpath("//table[@id='credentialTable']/tbody/tr"));
			jsExecutor.executeScript("arguments[0].click();", tableRows.get(0).findElement(By.id("button-edit-cred")));
		
			assertEquals(credentialPassword, driver.findElement(By.id("credential-password")).getAttribute("value"));
			
			// Edit credential
			jsExecutor.executeScript("arguments[0].value='updated" + credentialUserName + "';", driver.findElement(By.id("credential-username")));
			jsExecutor.executeScript("arguments[0].click();", driver.findElement(By.id("button-cred-save")));

			// Click link to get back to home page from the results page
			jsExecutor.executeScript("arguments[0].click();", driver.findElement(By.tagName("a")));
			
			
			updatedTableRows = driver.findElements(By.xpath("//table[@id='credentialTable']/tbody/tr"));
			
			assertEquals("updated" + credentialUserName, updatedTableRows.get(0).findElement(By.id("display-cred-username")).getText());
		}	
	}
	
	private void signupUser(String firstName, String lastName, String userName, String password) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) this.driver;
		
		driver.get("http://localhost:" + this.port + "/signup");
		jsExecutor.executeScript("arguments[0].value='" + firstName + "';", driver.findElement(By.id("inputFirstName")));
		jsExecutor.executeScript("arguments[0].value='" + lastName + "';", driver.findElement(By.id("inputLastName")));
		jsExecutor.executeScript("arguments[0].value='" + userName + "';", driver.findElement(By.id("inputUsername")));
		jsExecutor.executeScript("arguments[0].value='" + password + "';", driver.findElement(By.id("inputPassword")));
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(By.id("buttonSignup")));
	}
	
	private void loginUser(String userName, String password) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) this.driver;	
		
		driver.get("http://localhost:" + this.port + "/login");				
		jsExecutor.executeScript("arguments[0].value='" + userName + "';", driver.findElement(By.id("inputUsername")));
		jsExecutor.executeScript("arguments[0].value='" + password + "';", driver.findElement(By.id("inputPassword")));
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(By.id("buttonLogin")));
	}
	
	private boolean isLoggedIn() {
		return (driver.getTitle().equals("Home")) ? true : false; 
	}
	
	private void createNote(String title, String description) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) this.driver;
		
		// Create test note
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(By.id("nav-notes")));
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(By.id("button-new-note")));
		jsExecutor.executeScript("arguments[0].value='" + title + "';", driver.findElement(By.id("note-title")));
		jsExecutor.executeScript("arguments[0].value='" + description + "';", driver.findElement(By.id("note-description")));
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(By.id("button-note-save")));
		
		// Click link to get back to home page from the results page
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(By.tagName("a")));
	}
	
	private void createCredential(String url, String userName, String password) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) this.driver;
		
		// Create test credential
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(By.id("nav-credentials")));
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(By.id("button-new-cred")));
		jsExecutor.executeScript("arguments[0].value='" + url + "';", driver.findElement(By.id("credential-url")));
		jsExecutor.executeScript("arguments[0].value='" + userName + "';", driver.findElement(By.id("credential-username")));
		jsExecutor.executeScript("arguments[0].value='" + password + "';", driver.findElement(By.id("credential-password")));
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(By.id("button-cred-save")));
		
		// Click link to get back to home page from the results page
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(By.tagName("a")));	
	}

}
