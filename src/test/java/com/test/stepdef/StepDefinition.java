package com.test.stepdef;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.test.eventConnector.ObjEvent;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefinition extends ObjEvent {
	
	private Scenario myScenario;
	static Logger logger = Logger.getLogger(StepDefinition.class);

	public StepDefinition() throws IOException {
		super();
	}
	
	@Before() 
	public void definedScenario(Scenario scenario) {
		myScenario = scenario;
	}
	
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
	
	@When(".*?click by text.*\"(.*?)\"$")
	public void tapByTEXT(String objectName) throws InterruptedException {
		logger.debug("tap Object " + objectName);
		tapByText(driver, objectName, 100);
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

	@Then(".*? \"([^\"]*)\" .*?display by content")
	public void verifyElementExistByContentdesc(String objectName) {
		logger.debug("verifyElementExist Object element " + objectName);
		verifyElementExistByContentDesc(driver, objectName);
	}
	
	@Then(".*? \"([^\"]*)\" .*?display by Id")
	public void verifyElementExistById(String objectName) {
		logger.debug("verifyElementExist Object element " + objectName);
		verifyElementExistByContentDesc(driver, objectName);
	}
	
	@Then(".*? \"([^\"]*)\" .*?display by text")
	public void verifyElementExistByTEXT(String objectName) {
		logger.debug("verifyElementExist Object element " + objectName);
		verifyElementExistByText(driver, myScenario, objectName, 100);
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
	
	@Then(".*? \"([^\"]*)\" .*?disable by id")
	public void verifyElementDisableByID(String objectName) throws IOException {
		logger.debug("verifyElementDisable Object element " + objectName);
		verifyElementDisableById(driver, myScenario, objectName, 100);
	}
	
	@When(".*?i enter as otp.*?")
	public void enterOTP() throws InterruptedException {
		inputOTP();
	}
	
	@Given(".*?wait for id \"(.*?)\"$")
	public void waitForID(String objectName) throws InterruptedException {
		waitForId(driver, 10, objectName, false);
	}
	
	@Given(".*?wait for text \"(.*?)\"$")
	public void waitForText(String objectName) throws InterruptedException {
		waitForText(driver, 10, objectName, false);
	}
	
	@Given(".*?wait for index \"(.*?)\"$")
	public void waitForINDEX(String objectName) throws InterruptedException {
		waitForIndex(driver, 10, objectName, false);
	}
	
	@Given(".*?wait for content \"(.*?)\"$")
	public void waitForCONTENT(String objectName) throws InterruptedException {
		waitForContent(driver, 10, objectName, false);
	}
	
	@Then(".*?screenshot.*?")
	public void screenShot() {
		screenShot(driver, myScenario);
	}
	
	@Given(".*?swipe to text \"(.*?)\"$")
	public void swipeToElementText(String objectName) throws InterruptedException {
		swipeToEleText(driver, objectName);
	}
	
	@Given(".*?swipe to id \"(.*?)\"$")
	public void swipeToElementID(String objectName) throws InterruptedException {
		swipeToEleId(driver, objectName);
	}
	
	@After
	public void afterTest() throws Exception {
	
		logger.debug("After Hook ...............................................");

		if (myScenario.isFailed()) {
			myScenario.embed(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png");
			myScenario.write("Scenario Fail");
		} else {
			myScenario.write("Scenario Pass");
		}

		logger.debug("Quit Session ..............................................." + driver);

		Thread.sleep(5000);
		
		driver.quit();
	}
}
