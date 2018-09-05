package com.test.eventConnector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.AssertJUnit;

import com.test.otp.OTPTest;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class ObjEvent {

	String packageApp = "com.btpn.wow.eform:id/";
	//String packageApp = "io.selendroid.testapp:id/";
	Properties capabilitiesRepo = null;

	DesiredCapabilities desiredCapabilities;
	Logger logger = Logger.getLogger(ObjEvent.class);

	private static Scanner x;

	OTPTest otpt = new OTPTest();
	protected String otp;

	@SuppressWarnings("rawtypes")
	protected AndroidDriver driver;

	public ObjEvent(OTPTest otpTest) {
		this.otp = otpTest.getOtp();
	}

	public ObjEvent() throws IOException {
		// TODO Auto-generated constructor stub
		capabilitiesRepo = new Properties();
		FileInputStream fsCapabilities = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\test\\java\\com\\test\\config\\Capabilities.properties");
		capabilitiesRepo.load(fsCapabilities);

	}

	@SuppressWarnings("rawtypes")
	public void open() throws IOException, InterruptedException {

		if (driver == null) {
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

			desiredCapabilities.setCapability("autoGrantPermissions", true);

			desiredCapabilities.setCapability("appActivity", capabilitiesRepo.getProperty("appActivity"));

			desiredCapabilities.setCapability("automationName", "uiautomator2");

			driver = new AndroidDriver(new URL(capabilitiesRepo.getProperty("1URL")), desiredCapabilities);

		} else {
			logger.debug("Open Apps ...............................................");

			desiredCapabilities.setCapability("appWaitActivity", capabilitiesRepo.getProperty("appActivity"));

			desiredCapabilities.setCapability("appPackage", capabilitiesRepo.getProperty("appPackage"));

			desiredCapabilities.setCapability("appActivity", capabilitiesRepo.getProperty("appActivity"));
		}

		Thread.sleep(10000);
	}

	@SuppressWarnings("rawtypes")
	public void install() throws IOException {
		desiredCapabilities = new DesiredCapabilities();

		capabilitiesRepo = new Properties();
		FileInputStream fsCapabilities = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\test\\java\\com\\test\\config\\Capabilities.properties");
		capabilitiesRepo.load(fsCapabilities);

		File sourceDir = new File(capabilitiesRepo.getProperty("APK_PATH"));

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

		desiredCapabilities.setCapability("autoGrantPermissions", true);

		desiredCapabilities.setCapability("automationName", "uiautomator2");
		// install Apps
		desiredCapabilities.setCapability(MobileCapabilityType.APP, apkFilepath);

		driver = new AndroidDriver(new URL(capabilitiesRepo.getProperty("1URL")), desiredCapabilities);
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
	public void tap(AndroidDriver driver, String objectName) throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

		try {
			// TODO : Find Element by content-desc
			logger.debug("Find Element "
					+ driver.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")"));
			driver.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")").click();
		} catch (Exception a) {
			try {
				// TODO : Find Element by ID
				logger.debug("Find Element " + driver.findElement(By.id(packageApp + objectName)));
				driver.findElement(By.id(packageApp + objectName)).click();

			} catch (Exception c) {
				// TODO : Find Element by Xpath for All Tab
				driver.findElementByXPath("//*[@class = 'android.widget.TextView' and @text = '" + objectName + "']")
						.click();

			}
		}
	}

	@SuppressWarnings("rawtypes")
	public void setText(AndroidDriver driver, String text, String objectName) throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		try {
			// TODO : Find Element by content-desc
			logger.debug("Find Element "
					+ driver.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")"));
			driver.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")").sendKeys(text);

		} catch (Exception exce) {
			// TODO : Find Element by ID
			driver.findElement(By.id(packageApp + objectName)).sendKeys(text);
		}
		driver.hideKeyboard();
	}

	@SuppressWarnings("rawtypes")
	public void selectListItemBylabel(AndroidDriver driver, String text, String objectName) {
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

		// TODO: Find element by ID and Text
		driver.findElement(By.id(packageApp + objectName)).click();
		driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + text + "\")").click();

	}

	@SuppressWarnings("rawtypes")
	public void checkElement(AndroidDriver driver, String objectName) {
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

		try {
			// TODO : Find Element by content-desc
			logger.debug("Find Element "
					+ driver.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")"));
			driver.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")").click();
		} catch (Exception a) {
			// TODO : Find Element by ID
			logger.debug("Find Element " + driver.findElement(By.id(packageApp + objectName)));
			driver.findElement(By.id(packageApp + objectName)).click();
		}
	}

	@SuppressWarnings("rawtypes")
	public void verifyEqual(AndroidDriver driver, String objectName, String text) {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		try {
			// TODO : Find Element by content-desc
			logger.debug("Find Element "
					+ driver.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")"));
			String getText = driver.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")")
					.getText();
			AssertJUnit.assertEquals(text, getText);
		} catch (Exception a) {
			// TODO : Find element by ID
			logger.debug("Find Element " + driver.findElement(By.id(packageApp + objectName)));
			String getText = driver.findElement(By.id(packageApp + objectName)).getText();
			AssertJUnit.assertEquals(text, getText);
		}
	}

	@SuppressWarnings("rawtypes")
	public void verifyElementExist(AndroidDriver driver, String objectName) {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		try {
			// TODO : Find Element by content-desc
			logger.debug("Find Element "
					+ driver.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")"));
			driver.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")").isDisplayed();

		} catch (Exception a) {
			// TODO : Find Element by ID
			logger.debug("Find Element " + driver.findElement(By.id(packageApp + objectName)));
			driver.findElement(By.id(packageApp + objectName)).isDisplayed();
		}
	}

	@SuppressWarnings("rawtypes")
	public void swipeUp(AndroidDriver driver) {
		Dimension size;
		int starty = 0;
		int endy = 0;
		int startx = 0;
		int timeduration = 1000;

		size = driver.manage().window().getSize();
		System.out.println(size);
		starty = (int) (size.height * 0.80);
		System.out.println("starty = " + starty);
		endy = (int) (size.height * 0.20);
		System.out.println("endy = " + endy);
		startx = size.width / 2;
		System.out.println("startx = " + startx);
		System.out.println("Start swipe operation");
		driver.swipe(startx, starty, startx, endy, timeduration);
	}

	@SuppressWarnings("rawtypes")
	public void swipeDown(AndroidDriver driver) {

	}
}
