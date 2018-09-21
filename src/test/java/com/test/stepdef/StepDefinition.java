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
	
	@Given(".*?loged.*? \"(.*?)\"$")
	public void loged(String userID) {
		logger.debug("tap Object " + userID);
		logon(driver, userID);
	}

	@When(".*?click button \"(.*?)\"$")
	public void tapByContentdesc(String objectName) throws InterruptedException {
		logger.debug("tap Object " + objectName);
		tapByContentdesc(driver, objectName);
	}
	
	@When(".*?click button by name \"(.*?)\"$")
	public void tapByName(String objectName) throws InterruptedException {
		logger.debug("tap Object " + objectName);
		tapByName(driver, objectName);
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
	
	@When(".*?enter.*? \"(.*?)\" in name \"(.*?)\"$")
	public void setTextByName(String text, String objectName) throws InterruptedException {
		logger.debug("setText " + text + " in Object element " + objectName);
		setTextByName(driver, text, objectName);
	}
	
	@When(".*?enter.*? \"(.*?)\" in id \"(.*?)\"$")
	public void setTextByID(String text, String objectName) throws InterruptedException {
		logger.debug("setText " + text + " in Object element " + objectName);
		setTextByID(driver, text, objectName);
	}
	

	@Given(".*?select.*? \"(.*?)\" in \"(.*?)\"$")
	public void selectListItemBylabel(String text, String objectName) {
		logger.debug("selectListItemBylabel " + text + " in Object element " + objectName);
		selectListItemBylabel(driver, text, objectName);
	}

	@When(".*?check.*? \"(.*?)\"$")
	public void checkElement(String objectName) {
		logger.debug("checkElement Object element " + objectName);
		checkElement(driver, objectName);
	}

	@Then(".*? \"([^\"]*)\" .*?display.*?")
	public void verifyElementExistByContentdesc(String objectName) {
		logger.debug("verifyElementExist Object element " + objectName);
		verifyElementExistByContentDesc(driver, objectName);
	}
	
	@Then(".*? \"([^\"]*)\" .*?display by Id.*?")
	public void verifyElementExistById(String objectName) {
		logger.debug("verifyElementExist Object element " + objectName);
		verifyElementExistByContentDesc(driver, objectName);
	}
	
	@Then(".*? \"([^\"]*)\" .*?display by xpath.*?")
	public void verifyElementExistByXpath(String objectName) {
		logger.debug("verifyElementExist Object element " + objectName);
		verifyElementExistByXpath(driver, objectName);
	}
	
	@When(".*?swipe up.*?")
	public void swipeup() {
		swipeUp(driver);
	}

	@Then(".*?verify.*? \"(.*?)\" is \"(.*?)\"$")
	public void verifyEqualByContentdesc(String objectName, String text) {
		logger.debug("verifyEqual Object element " + objectName);
		verifyEqualByContentDesc(driver, objectName, text); 
	}
	
	@Then(".*?verify by id.*? \"(.*?)\" is \"(.*?)\"$")
	public void verifyEqualById(String objectName, String text) {
		logger.debug("verifyEqual Object element " + objectName);
		verifyEqualById(driver, objectName, text); 
	}
	
	@Then(".*?verify by xpath.*? \"(.*?)\" is \"(.*?)\"$")
	public void verifyEqualByXpath(String objectName, String text) {
		logger.debug("verifyEqual Object element " + objectName);
		verifyEqualByXpath(driver, objectName, text); 
	}
	
	@Then(".*?calculate.*? as \"(.*?)\" in \"(.*?)\"$")
	public void calculatePoint(String text, String objectName) throws InterruptedException {
		logger.debug("calculatePoint " + text + " in Object element " + objectName);
		calculatePoint(driver, text, objectName);
	}
	
	@Then(".*?view total point.*? is \"(.*?)\"$")
	public void totalPoint(String objectName) throws InterruptedException {
		logger.debug("totalPoint in Object element " + objectName);
		totalPoint(driver, objectName);
	}
	
	@When(".*?choose.*? \"(.*?)\"$")
	public void choose(String objectName) throws InterruptedException {
		logger.debug("choose Object element " + objectName);
		/*input/change method below*/
		choose(driver, objectName);
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
