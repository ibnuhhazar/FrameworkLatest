package com.test.eventConnector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;

import com.test.otp.OTPTest;

import cucumber.api.Scenario;
import io.appium.java_client.MobileBy.ByAndroidUIAutomator;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class ObjEvent {

	// String packageApp = "com.btpn.wow.eform:id/";
	String packageApp = "io.selendroid.testapp:id/";
	private Properties capabilitiesRepo = null;
	private Logger logger = Logger.getLogger(ObjEvent.class);
	private OTPTest otpt = new OTPTest();

	private DesiredCapabilities desiredCapabilities;
	private AppiumDriverLocalService service;
	private Scanner x;
	private WebDriverWait wait;

	protected String otp;
	@SuppressWarnings("rawtypes")
	protected AndroidDriver driver;

	public ObjEvent(OTPTest otpTest) {
		this.otp = otpTest.getOtp();
	}

	public ObjEvent() throws IOException {
		// TODO Auto-generated constructor stub

		capabilitiesRepo = new Properties();
		File properFile = new File(System.getProperty("user.dir"),
				"/src/test/java/com/test/config/Capabilities.properties");
		FileInputStream fsCapabilities = new FileInputStream(properFile);
		capabilitiesRepo.load(fsCapabilities);

	}

	@SuppressWarnings("rawtypes")
	public void open() throws IOException, InterruptedException {

		if (driver == null) {

			if (SystemUtils.IS_OS_WINDOWS) {
				// TODO : WINDOWS
				System.setProperty(AppiumServiceBuilder.NODE_PATH, "C:\\Program Files\\nodejs\\node.exe");
				System.setProperty(AppiumServiceBuilder.APPIUM_PATH,
						System.getenv("USER_HOME") + "\\node_modules\\appium\\build\\lib\\main.js");
			} else {
				// TODO : MAC
				System.setProperty(AppiumServiceBuilder.NODE_PATH, "/usr/local/bin/node.sh");
				System.setProperty(AppiumServiceBuilder.APPIUM_PATH,
						"//usr/local/lib/node_modules/appium/build/lib/main.js");
			}

			service = AppiumDriverLocalService
					.buildService(new AppiumServiceBuilder().usingAnyFreePort().withIPAddress("127.0.0.1"));
			service.start();

			desiredCapabilities = new DesiredCapabilities();

			// Specify the device name (any name)
			desiredCapabilities.setCapability("deviceName", capabilitiesRepo.getProperty("1deviceName"));

			// Platform version
			desiredCapabilities.setCapability("platformVersion", capabilitiesRepo.getProperty("1platformVersion"));

			// platform name
			desiredCapabilities.setCapability("platformName", "Android");

			logger.debug("Open Apps ...............................................");

			desiredCapabilities.setCapability("appWaitActivity", capabilitiesRepo.getProperty("appActivity"));

			desiredCapabilities.setCapability("appPackage", capabilitiesRepo.getProperty("appPackage"));

			desiredCapabilities.setCapability("appActivity", capabilitiesRepo.getProperty("appActivity"));

			desiredCapabilities.setCapability("automationName", "uiautomator2");

			driver = new AndroidDriver(service.getUrl(), desiredCapabilities);

		} else {
			logger.debug("Open Apps ...............................................");

			desiredCapabilities.setCapability("appWaitActivity", capabilitiesRepo.getProperty("appActivity"));

			desiredCapabilities.setCapability("appPackage", capabilitiesRepo.getProperty("appPackage"));

			desiredCapabilities.setCapability("appActivity", capabilitiesRepo.getProperty("appActivity"));
		}
	}

	@SuppressWarnings("rawtypes")
	public void install() throws IOException, InterruptedException {
		desiredCapabilities = new DesiredCapabilities();

		if (SystemUtils.IS_OS_WINDOWS) {
			// TODO : WINDOWS
			System.setProperty(AppiumServiceBuilder.NODE_PATH, "C:\\Program Files\\nodejs\\node.exe");
			System.setProperty(AppiumServiceBuilder.APPIUM_PATH,
					System.getenv("USER_HOME") + "\\node_modules\\appium\\build\\lib\\main.js");
		} else {
			// TODO : MAC
			System.setProperty(AppiumServiceBuilder.NODE_PATH, "/usr/local/bin/node.sh");
			System.setProperty(AppiumServiceBuilder.APPIUM_PATH,
					"//usr/local/lib/node_modules/appium/build/lib/main.js");
		}

		service = AppiumDriverLocalService
				.buildService(new AppiumServiceBuilder().usingAnyFreePort().withIPAddress("127.0.0.1"));
		service.start();

		File sourceDir = new File(System.getProperty("user.dir"), capabilitiesRepo.getProperty("APK_PATH"));

		String[] apkFilenames = sourceDir.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if (name.endsWith(".apk"))
					return true;
				else
					return false;
			}
		});

		// TODO: Get newest file for list of apkFilenames
		String apkFilepath = sourceDir.getAbsolutePath() + File.separator + apkFilenames[0];

		// Specify the device name (any name)
		desiredCapabilities.setCapability("deviceName", capabilitiesRepo.getProperty("1deviceName"));

		// Platform version
		desiredCapabilities.setCapability("platformVersion", capabilitiesRepo.getProperty("1platformVersion"));

		// platform name
		desiredCapabilities.setCapability("platformName", "Android");

		// install Apps
		desiredCapabilities.setCapability(MobileCapabilityType.APP, apkFilepath);

		desiredCapabilities.setCapability("automationName", "uiautomator2");

		driver = new AndroidDriver(service.getUrl(), desiredCapabilities);

	}

	@SuppressWarnings("rawtypes")
	public void logon(AndroidDriver driver, String userID) {
		boolean found = false;

		String filePath = System.getProperty("user.dir") + "\\DataFile\\DataLogin.txt";
		String searchTerm = userID;
		String username = "";
		String password = "";

		try {
			x = new Scanner(new File(filePath));
			x.useDelimiter("[,\n]");

			while (x.hasNext() && !found) {
				username = x.next();
				password = x.next();

				if (username.equals(searchTerm)) {
					found = true;
				}
			}

			if (found) {
				logger.debug("Find Element " + driver
						.findElementByAndroidUIAutomator("UiSelector().description(\"item_inputtxt_mobilenumber\")"));
				driver.findElementByAndroidUIAutomator("UiSelector().description(\"item_inputtxt_mobilenumber\")")
						.sendKeys(username);

				logger.debug("Find Element "
						+ driver.findElementByAndroidUIAutomator("UiSelector().description(\"item_inputtxt_pin\")"));
				driver.findElementByAndroidUIAutomator("UiSelector().description(\"item_inputtxt_pin\")")
						.sendKeys(password.trim());

			} else {
				System.out.println("Record Not Found: ");
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@SuppressWarnings("rawtypes")
	public void tapByContentdesc(AndroidDriver driver, String objectName) throws InterruptedException {
		wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(ByAndroidUIAutomator.AccessibilityId(objectName)));

		// TODO : Find Element by content-desc
		logger.debug("Find Element "
				+ driver.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")"));
		driver.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")").click();
	}

	@SuppressWarnings("rawtypes")
	public void tapByName(AndroidDriver driver, String objectName) {

		// TODO : Find Element by name
		logger.debug("Find Element " + driver.findElement(By.name(objectName)));
		driver.findElement(By.name(objectName)).click();
	}

	@SuppressWarnings("rawtypes")
	public void tapById(AndroidDriver driver, String objectName) throws InterruptedException {
		wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(packageApp + objectName)));

		// TODO : Find Element by ID
		logger.debug("Find Element " + driver.findElement(By.id(packageApp + objectName)));
		driver.findElement(By.id(packageApp + objectName)).click();
	}

	@SuppressWarnings("rawtypes")
	public void tapByXpath(AndroidDriver driver, String objectName) {

		// TODO : Find Element by Xpath for All Tab
		driver.findElementByXPath("//*[@class = 'android.widget.TextView' and @text = '" + objectName + "']").click();
	}

	@SuppressWarnings("rawtypes")
	public void setTextbyContentdesc(AndroidDriver driver, String text, String objectName) throws InterruptedException {
		wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(ByAndroidUIAutomator.AccessibilityId(objectName)));

		// TODO : Find Element by content-desc
		logger.debug("Find Element "
				+ driver.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")"));
		driver.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")").sendKeys(text);

	}

	@SuppressWarnings("rawtypes")
	public void setTextByID(AndroidDriver driver, String text, String objectName) {
		wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(packageApp + objectName)));

		// TODO : Find Element by ID
		logger.debug("Find Element " + driver.findElement(By.id(packageApp + objectName)));
		driver.findElement(By.id(packageApp + objectName)).sendKeys(text);

	}

	@SuppressWarnings("rawtypes")
	public void setTextByXpath(AndroidDriver driver, String text, String objectName) {
		// TODO : Find Element By Xpath
	}

	@SuppressWarnings("rawtypes")
	public void selectListItemByContentDesc(AndroidDriver driver, String text, String objectName)
			throws InterruptedException {
		wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(ByAndroidUIAutomator.AccessibilityId(objectName)));

		// TODO: Find element by content-desc
		driver.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")").click();
		Thread.sleep(1000);
		driver.findElementByAndroidUIAutomator("UiSelector().text(\"" + text + "\")").click();
	}

	@SuppressWarnings("rawtypes")
	public void selectListItemById(AndroidDriver driver, String text, String objectName) throws InterruptedException {
		wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(By.id(packageApp + objectName)));

		// TODO: Find element by Id
		driver.findElement(By.id(packageApp + objectName)).click();
		Thread.sleep(1000);
		driver.findElementByAndroidUIAutomator("UiSelector().text(\"" + text + "\")").click();
	}

	@SuppressWarnings("rawtypes")
	public void checkElementByContentDesc(AndroidDriver driver, String objectName) {
		wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(ByAndroidUIAutomator.AccessibilityId(objectName)));

		// TODO : Find Element by content-desc
		logger.debug("Find Element "
				+ driver.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")"));
		driver.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")").click();
	}

	@SuppressWarnings("rawtypes")
	public void checkElementById(AndroidDriver driver, String objectName) {
		wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(By.id(packageApp + objectName)));

		// TODO : Find Element by ID
		logger.debug("Find Element " + driver.findElement(By.id(packageApp + objectName)));
		driver.findElement(By.id(packageApp + objectName)).click();
	}

	@SuppressWarnings("rawtypes")
	public void checkElementByXpath(AndroidDriver driver, String objectName) {
		// TODO : Find Element by xpath
	}

	@SuppressWarnings("rawtypes")
	public void verifyEqualByContentDesc(AndroidDriver driver, String objectName, String text) {
		wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(ByAndroidUIAutomator.AccessibilityId(objectName)));

		logger.debug("Find Element "
				+ driver.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")"));
		String getText = driver.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")")
				.getText();
		AssertJUnit.assertEquals(text, getText);
	}

	@SuppressWarnings("rawtypes")
	public void verifyEqualById(AndroidDriver driver, String objectName, String text) throws InterruptedException {
		wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(By.id(packageApp + objectName)));

		// TODO : Find element by ID
		logger.debug("Find Element " + driver.findElement(By.id(packageApp + objectName)));
		String getText = driver.findElement(By.id(packageApp + objectName)).getText();
		AssertJUnit.assertEquals(text, getText);

	}

	@SuppressWarnings("rawtypes")
	public void verifyEqualByXpath(AndroidDriver driver, String objectName, String text) {
		// TODO : Find element by Xpath
	}

	@SuppressWarnings("rawtypes")
	public void verifyElementExistByContentDesc(AndroidDriver driver, String objectName) {
		wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(ByAndroidUIAutomator.AccessibilityId(objectName)));

		// TODO : Find Element by content-desc
		logger.debug("Find Element "
				+ driver.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")"));
		driver.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")").isDisplayed();
	}

	@SuppressWarnings("rawtypes")
	public void verifyElementExistById(AndroidDriver driver, String objectName) {
		wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(By.id(packageApp + objectName)));

		// TODO : Find Element by ID
		logger.debug("Find Element " + driver.findElement(By.id(packageApp + objectName)));
		driver.findElement(By.id(packageApp + objectName)).isDisplayed();
	}

	@SuppressWarnings("rawtypes")
	public void verifyElementExistByXpath(AndroidDriver driver, String objectName) {
		// TODO : Find Element by Xpath
	}

	@SuppressWarnings("rawtypes")
	public void swipeUp(AndroidDriver driver) {
		Dimension size;
		int starty = 0;
		int endy = 0;
		int startx = 0;
		int timeduration = 1000;

		size = driver.manage().window().getSize();
		starty = (int) (size.height * 0.80);
		endy = (int) (size.height * 0.20);
		startx = size.width / 2;
		driver.swipe(startx, starty, startx, endy, timeduration);
	}

	@SuppressWarnings("rawtypes")
	public void swipeDown(AndroidDriver driver) {
		Dimension size;
		int starty = 0;
		int endy = 0;
		int startx = 0;
		int timeduration = 1000;

		size = driver.manage().window().getSize();
		System.out.println(size);
		starty = (int) (size.height * 0.20);
		System.out.println("starty = " + starty);
		endy = (int) (size.height * 0.80);
		System.out.println("endy = " + endy);
		startx = size.width / 2;
		System.out.println("startx = " + startx);
		System.out.println("Start swipe operation");
		driver.swipe(startx, starty, startx, endy, timeduration);
	}

	@SuppressWarnings("rawtypes")
	public void swipeRight(AndroidDriver driver) {
		Dimension size;
		int starty = 0;
		int endx = 0;
		int startx = 0;
		int timeduration = 1000;

		size = driver.manage().window().getSize();
		System.out.println(size);
		startx = (int) (size.width * 0.80);
		System.out.println("startx = " + startx);
		endx = (int) (size.width * 0.20);
		System.out.println("endx = " + endx);
		starty = size.height / 2;
		System.out.println("startx = " + startx);
		System.out.println("Start swipe operation");
		driver.swipe(startx, starty, endx, starty, timeduration);
	}

	@SuppressWarnings("rawtypes")
	public void swipeLeft(AndroidDriver driver) {
		Dimension size;
		int starty = 0;
		int endx = 0;
		int startx = 0;
		int timeduration = 1000;

		size = driver.manage().window().getSize();
		System.out.println(size);
		startx = (int) (size.width * 0.20);
		System.out.println("starty = " + startx);
		endx = (int) (size.width * 0.80);
		System.out.println("endy = " + endx);
		starty = size.height / 2;
		System.out.println("startx = " + startx);
		System.out.println("Start swipe operation");
		driver.swipe(startx, starty, endx, starty, timeduration);
	}

	@SuppressWarnings("rawtypes")
	public void swipeUp2(AndroidDriver driver) {
		Dimension size;
		int starty = 0;
		int endy = 0;
		int startx = 0;
		int timeduration = 1000;

		size = driver.manage().window().getSize();
		starty = (int) (size.height * 0.40);
		endy = (int) (size.height * 0.20);
		startx = size.width / 1;
		driver.swipe(startx, starty, startx, endy, timeduration);
	}

	public void inputOTP() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		Thread.sleep(5000);
		try {
			driver.findElement(By.id(packageApp + "message")).isDisplayed();
			System.out.println("Button1 Is Display");
		} catch (Exception a) {

			otpt.init();
			otpt.testA();

			// TODO : INPUT OTP VALUE
			int[] ary = Arrays.stream(otpt.getOtp().split("")).mapToInt(Integer::parseInt).toArray();

			driver.findElementByXPath("//*[@index = '0' and @content-desc ='item_inputtxt_otp']")
					.sendKeys(String.valueOf(ary[0]));
			driver.findElementByXPath("//*[@index = '1' and @content-desc ='item_inputtxt_otp']")
					.sendKeys(String.valueOf(ary[1]));
			driver.findElementByXPath("//*[@index = '2' and @content-desc ='item_inputtxt_otp']")
					.sendKeys(String.valueOf(ary[2]));
			driver.findElementByXPath("//*[@index = '3' and @content-desc ='item_inputtxt_otp']")
					.sendKeys(String.valueOf(ary[3]));
			driver.findElementByXPath("//*[@index = '4' and @content-desc ='item_inputtxt_otp']")
					.sendKeys(String.valueOf(ary[4]));
			driver.findElementByXPath("//*[@index = '5' and @content-desc ='item_inputtxt_otp']")
					.sendKeys(String.valueOf(ary[5]));

		}
	}

	@SuppressWarnings("rawtypes")
	public void screenShot(AndroidDriver driver, Scenario myScenario) {
		System.out.println("This is Scenario" + myScenario);
		myScenario.embed(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png");
	}
}