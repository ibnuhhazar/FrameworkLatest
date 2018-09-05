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
	public void installApps() throws IOException {
		install();
	}
	
	@Given(".*?loged.*? \"(.*?)\"$")
	public void loged(String userID) {
		logger.debug("tap Object " + userID);
		logon(driver, userID);
	}

	@Given(".*?click.*? \"(.*?)\"$")
	public void tap(String objectName) throws InterruptedException {
		logger.debug("tap Object " + objectName);
		tap(driver, objectName);
	}

	@Given(".*?enter.*? \"(.*?)\" in \"(.*?)\"$")
	public void setText(String text, String objectName) throws InterruptedException {
		logger.debug("setText " + text + " in Object element " + objectName);
		setText(driver, text, objectName);
	}

	@Given(".*?select.*? \"(.*?)\" in \"(.*?)\"$")
	public void selectListItemBylabel(String text, String objectName) {
		logger.debug("selectListItemBylabel " + text + " in Object element " + objectName);
		selectListItemBylabel(driver, text, objectName);
	}

	@Given(".*?check.*? \"(.*?)\"$")
	public void checkElement(String objectName) {
		logger.debug("checkElement Object element " + objectName);
		checkElement(driver, objectName);
	}

	@Given(".*? \"([^\"]*)\" .*?display.*?")
	public void verifyElementExist(String objectName) {
		logger.debug("verifyElementExist Object element " + objectName);
		verifyElementExist(driver, objectName);
	}
	
	@Given(".*?swipe up.*?")
	public void swipeupElementExist() {
		swipeUp(driver);
	}
	
	@Given(".*?swipe down.*?")
	public void swipedownElementExist() {
		swipeDown(driver);
	}

	@Then(".*?verify.*? \"(.*?)\" is \"(.*?)\"$")
	public void verifyEqual(String objectName, String text) {
		logger.debug("verifyEqual Object element " + objectName);
		verifyEqual(driver, objectName, text);
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
