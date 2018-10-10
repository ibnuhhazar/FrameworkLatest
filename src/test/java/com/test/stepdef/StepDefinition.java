package com.test.stepdef;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.test.eventConnector.ObjEvent;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefinition extends ObjEvent {

	public StepDefinition() throws IOException {
		super();
	}

	static Logger logger = Logger.getLogger(StepDefinition.class);
	
	@Given(".*?dashboard.*?")
	public void dashboard() {

	}
	
	@Given(".*?open.*?")
	public void openApps() throws IOException, InterruptedException {
		open();
	}
	
	@Given(".*?install.*?")
	public void installApps() throws IOException, InterruptedException {
		install();
	}
	
	@When(".*?loged.*? \"(.*?)\"$")
	public void loged(String userID) {
		logger.debug("tap Object " + userID);
		logon(driver, userID);
	}

	@When(".*?click button \"(.*?)\"$")
	public void tapByContentdesc(String objectName) throws InterruptedException {
		logger.debug("tap Object " + objectName);
		tapByContentdesc(driver, objectName);
	}
	
	@When(".*?click button by id \"(.*?)\"$")
	public void tapById(String objectName) throws InterruptedException {
		logger.debug("tap Object " + objectName);
		tapById(driver, objectName);
	}
	
	@When(".*?click button by Xpath \"(.*?)\"$")
	public void tapByXpath(String objectName) throws InterruptedException {
		logger.debug("tap Object " + objectName);
		tapByXpath(driver, objectName);
	}

	@When(".*?enter.*? \"(.*?)\" in \"(.*?)\"$")
	public void setTextbyContentdesc(String text, String objectName) throws InterruptedException {
		logger.debug("setText " + text + " in Object element " + objectName);
		setTextbyContentdesc(driver, text, objectName);
	}
	
	@When(".*?enter.*? \"(.*?)\" in id \"(.*?)\"$")
	public void setTextByID(String text, String objectName) throws InterruptedException {
		logger.debug("setText " + text + " in Object element " + objectName);
		setTextByID(driver, text, objectName);
	}
	
	@When(".*?enter.*? \"(.*?)\" in xpath \"(.*?)\"$")
	public void setTextByXpath(String text, String objectName) throws InterruptedException {
		logger.debug("setText " + text + " in Object element " + objectName);
		setTextByXpath(driver, text, objectName);
	}
	
	@When(".*?select.*? \"(.*?)\" in \"(.*?)\"$")
	public void selectListItemByContentDesc(String text, String objectName) throws InterruptedException {
		logger.debug("selectListItemBylabel " + text + " in Object element " + objectName);
		selectListItemByContentDesc(driver, text, objectName);
	}

	@When(".*?select.*? \"(.*?)\" in id \"(.*?)\"$")
	public void selectListItemById(String text, String objectName) throws InterruptedException {
		logger.debug("selectListItemBylabel " + text + " in Object element " + objectName);
		selectListItemById(driver, text, objectName);
	}
	
	@When(".*?check checkbox \"(.*?)\"$")
	public void checkElementByContentDesc(String objectName) {
		logger.debug("checkElement Object element " + objectName);
		checkElementByContentDesc(driver, objectName);
	}
	
	@When(".*?check checkbox by id \"(.*?)\"$")
	public void checkElementById(String objectName) {
		logger.debug("checkElement Object element " + objectName);
		checkElementById(driver, objectName);
	}
	
	@When(".*?check checkbox xpath \"(.*?)\"$")
	public void checkElementByXpath(String objectName) {
		logger.debug("checkElement Object element " + objectName);
		checkElementByXpath(driver, objectName);
	}

	@Then(".*? \"([^\"]*)\" .*?display")
	public void verifyElementExistByContentdesc(String objectName) {
		logger.debug("verifyElementExist Object element " + objectName);
		verifyElementExistByContentDesc(driver, objectName);
	}
	
	@Then(".*? \"([^\"]*)\" .*?display by Id")
	public void verifyElementExistById(String objectName) {
		logger.debug("verifyElementExist Object element " + objectName);
		verifyElementExistByContentDesc(driver, objectName);
	}
	
	@Then(".*? \"([^\"]*)\" .*?display by xpath")
	public void verifyElementExistByXpath(String objectName) {
		logger.debug("verifyElementExist Object element " + objectName);
		verifyElementExistByXpath(driver, objectName);
	}
	
	@When(".*?swipe up.*?")
	public void swipeup() {
		swipeUp(driver);
	}
	
	@When(".*?swipe down.*?")
	public void swipedownElementExist() {
		swipeDown(driver);
	}
	
	@When(".*?swipe right.*?")
	public void swiperightElementExist() {
		swipeRight(driver);
	}
	
	@When(".*?swipe left.*?")
	public void swipeleftElementExist() {
		swipeLeft(driver);
	}

	@Then(".*?verify, so that \"(.*?)\" is \"(.*?)\"$")
	public void verifyEqualByContentdesc(String objectName, String text) {
		logger.debug("verifyEqual Object element " + objectName);
		verifyEqualByContentDesc(driver, objectName, text); 
	}
	
	@Then(".*?verify, so that id \"(.*?)\" is \"(.*?)\"$")
	public void verifyEqualById(String objectName, String text) throws InterruptedException {
		logger.debug("verifyEqual Object element " + objectName);
		verifyEqualById(driver, objectName, text); 
	}
	
	@Then(".*?verify, so that xpath \"(.*?)\" is \"(.*?)\"$")
	public void verifyEqualByXpath(String objectName, String text) {
		logger.debug("verifyEqual Object element " + objectName);
		verifyEqualByXpath(driver, objectName, text); 
	}	
	
	@When(".*?i enter as otp.*?")
	public void enterOTP() throws InterruptedException {
		inputOTP();
	}
	
	@After
	public void afterTest(Scenario scenario) throws Exception {
	
		logger.debug("After Hook ...............................................");

		if (scenario.isFailed()) {
			scenario.embed(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png");
			scenario.write("Scenario Fail");
		} else {
			scenario.write("Scenario Pass");
		}

		logger.debug("Quit Session ..............................................." + driver);

		driver.quit();
	}
}
