package com.test.eventConnector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.AssertJUnit;

import com.test.otp.OTPTest;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class ObjEvent {

	//String packageApp = "com.btpn.wow.eform:id/";
	String packageApp = "io.selendroid.testapp:id/";
	private Properties capabilitiesRepo = null;
	private Logger logger = Logger.getLogger(ObjEvent.class);
	private OTPTest otpt = new OTPTest();

	private DesiredCapabilities desiredCapabilities;
	private AppiumDriverLocalService service;
	private Scanner x;

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

			System.setProperty(AppiumServiceBuilder.NODE_PATH, "C:\\Program Files\\nodejs\\node.exe");
			System.setProperty(AppiumServiceBuilder.APPIUM_PATH,
					"C:\\Users\\17053682\\node_modules\\appium\\build\\lib\\main.js");

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
			
			Thread.sleep(5000);

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

		System.setProperty(AppiumServiceBuilder.NODE_PATH, "C:\\Program Files\\nodejs\\node.exe");
		System.setProperty(AppiumServiceBuilder.APPIUM_PATH,
				"C:\\Users\\17053682\\node_modules\\appium\\build\\lib\\main.js");

		service = AppiumDriverLocalService
				.buildService(new AppiumServiceBuilder().usingAnyFreePort().withIPAddress("127.0.0.1"));
		service.start();

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

		// install Apps
		desiredCapabilities.setCapability(MobileCapabilityType.APP, apkFilepath);

		desiredCapabilities.setCapability("automationName", "uiautomator2");

		driver = new AndroidDriver(service.getUrl(), desiredCapabilities);

		Thread.sleep(5000);

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
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// TODO : Find Element by content-desc
		logger.debug("Find Element "
				+ driver.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")"));
		driver.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")").click();
	}

	@SuppressWarnings("rawtypes")
	public void tapByName(AndroidDriver driver, String objectName) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// TODO : Find Element by name
		logger.debug("Find Element " + driver.findElement(By.name(objectName)));
		driver.findElement(By.name(objectName)).click();
	}

	@SuppressWarnings("rawtypes")
	public void tapById(AndroidDriver driver, String objectName) throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Thread.sleep(3000);
		// TODO : Find Element by ID
		logger.debug("Find Element " + driver.findElement(By.id(packageApp + objectName)));
		driver.findElement(By.id(packageApp + objectName)).click();
	}

	@SuppressWarnings("rawtypes")
	public void tapByXpath(AndroidDriver driver, String objectName) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// TODO : Find Element by Xpath for All Tab
		driver.findElementByXPath("//*[@class = 'android.widget.TextView' and @text = '" + objectName + "']").click();
	}

	@SuppressWarnings("rawtypes")
	public void setTextbyContentdesc(AndroidDriver driver, String text, String objectName) throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		// TODO : Find Element by content-desc
		logger.debug("Find Element "
				+ driver.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")"));
		driver.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")").sendKeys(text);

	}

	@SuppressWarnings("rawtypes")
	public void setTextByName(AndroidDriver driver, String text, String objectName) {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		// TODO : Find Element By Name
		logger.debug("Find Element " + driver.findElement(By.name(objectName)));
		driver.findElement(By.name(objectName)).sendKeys(text);

	}

	@SuppressWarnings("rawtypes")
	public void setTextByID(AndroidDriver driver, String text, String objectName) {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		// TODO : Find Element by ID
		logger.debug("Find Element " + driver.findElement(By.id(packageApp + objectName)));
		driver.findElement(By.id(packageApp + objectName)).sendKeys(text);

	}

	@SuppressWarnings("rawtypes")
	public void selectListItemBylabel(AndroidDriver driver, String text, String objectName) {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		try {
			// TODO: Find element by content-desc
			driver.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")").click();

			switch (text) {
			case ("6 bulan"):
				driver.findElementByXPath("//*[@class = 'android.widget.CheckedTextView' and @instance = '0']").click();
				break;
			case ("12 bulan"):
				driver.findElementByXPath("//*[@class = 'android.widget.CheckedTextView' and @instance = '1']").click();
				break;
			default:
				driver.findElementByXPath("//*[@class = 'android.widget.TextView' and @text = '" + text + "']").click();
			}

		} catch (Exception a) {
			System.out.println(a);
		}
	}

	@SuppressWarnings("rawtypes")
	public void checkElement(AndroidDriver driver, String objectName) {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		try {
			// TODO : Find Element by content-desc
			logger.debug("Find Element "
					+ driver.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")"));
			driver.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")").click();
		} catch (Exception a) {
			try {
				// TODO : Find Element by name
				logger.debug("Find Element " + driver.findElement(By.name(objectName)));
				driver.findElement(By.name(objectName)).click();
			} catch (Exception b) {
				// TODO : Find Element by ID
				logger.debug("Find Element " + driver.findElement(By.id(packageApp + objectName)));
				driver.findElement(By.id(packageApp + objectName)).click();
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public void verifyEqualByContentDesc(AndroidDriver driver, String objectName, String text) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// TODO : Find Element by content-desc
		logger.debug("Find Element "
				+ driver.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")"));
		String getText = driver.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")")
				.getText();
		AssertJUnit.assertEquals(text, getText);
	}

	@SuppressWarnings("rawtypes")
	public void verifyEqualById(AndroidDriver driver, String objectName, String text) throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Thread.sleep(2000);
		// TODO : Find element by ID
		logger.debug("Find Element " + driver.findElement(By.id(packageApp + objectName)));
		String getText = driver.findElement(By.id(packageApp + objectName)).getText();
		AssertJUnit.assertEquals(text, getText);

	}

	@SuppressWarnings("rawtypes")
	public void verifyEqualByXpath(AndroidDriver driver, String objectName, String text) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@SuppressWarnings("rawtypes")
	public void verifyElementExistByContentDesc(AndroidDriver driver, String objectName) {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		// TODO : Find Element by content-desc
		logger.debug("Find Element "
				+ driver.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")"));
		driver.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")").isDisplayed();
	}

	@SuppressWarnings("rawtypes")
	public void verifyElementExistById(AndroidDriver driver, String objectName) {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		// TODO : Find Element by ID
		logger.debug("Find Element " + driver.findElement(By.id(packageApp + objectName)));
		driver.findElement(By.id(packageApp + objectName)).isDisplayed();
	}

	@SuppressWarnings("rawtypes")
	public void verifyElementExistByXpath(AndroidDriver driver, String objectName) {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

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

	public int calculatePointMultiplication(String objectName, int multiplier) {
		return Integer.parseInt(
				driver.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")").getText())
				* multiplier;
	}

	@SuppressWarnings("rawtypes")
	public void calculatePoint(AndroidDriver driver, String text, String objectName) throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		// "text" --> u/ menampung agentClass
		// "objectName" --> u/ mengambil nilai yang diinputkan oleh user
		// LANGKAH-LANGKAH :
		// 1. menentukan agentClass
		// 2. ambil nilai yang ditampung dalam variabel "ambilCashout" --> perhitungan
		// dari frontEnd
		// 3. hitung nilai yang akan ditampung dalam variabel "hitungCashout" -->
		// perhitungan sesuai dgn rumus
		// 4. bandingkan nilai "ambilCashout" dgn "hitungCashout"

		int ambilCashout = 0, ambilCustomer = 0, ambilAquisition = 0, ambilBFI = 0, ambilTransaction50 = 0;
		int ambilSusanLogin = 0, ambilWeselHP = 0, ambilBankTransfer = 0, ambilOTC = 0;

		int hitungBFI = 0, hitungTransaction50 = 0, hitungSusanLogin = 0;
		int hitungBankTransfer = 0, hitungOTC = 0;

		int hitungSpecial = 0;
		// variabel khusus yg u/ menampung nilai yang akan diinputkan user pada label :
		// cashout, customer, aquisition, weselHP

		switch (objectName) {
		case ("calpoint_est_cashout"):
			ambilCashout = Integer.parseInt(driver
					.findElementByAndroidUIAutomator("UiSelector().description(\"calpoint_val_cashout\")").getText());
			System.out.println("ambilCashout = " + ambilCashout);

		case ("calpoint_est_customer"):
			ambilCustomer = Integer.parseInt(driver
					.findElementByAndroidUIAutomator("UiSelector().description(\"calpoint_val_customer\")").getText());
			System.out.println("ambilCustomer = " + ambilCustomer);

		case ("calpoint_est_acquisition"):
			ambilAquisition = Integer.parseInt(
					driver.findElementByAndroidUIAutomator("UiSelector().description(\"calpoint_val_acquisition\")")
							.getText());
			System.out.println("ambilAquisition = " + ambilAquisition);

		case ("calpoint_est_weselHP"):
			ambilWeselHP = Integer.parseInt(driver
					.findElementByAndroidUIAutomator("UiSelector().description(\"calpoint_val_weselhp\")").getText());
			System.out.println("ambilWeselHP = " + ambilWeselHP);

			switch (text) {
			case ("Star"):
				hitungSpecial = calculatePointMultiplication(objectName, 3);
				System.out.println(objectName + " = " + hitungSpecial);
				break;
			case ("Gold"):
				hitungSpecial = calculatePointMultiplication(objectName, 2);
				System.out.println(objectName + " = " + hitungSpecial);
				break;
			case ("Silver"):
			case ("Bronze"):
				hitungSpecial = calculatePointMultiplication(objectName, 1);
				System.out.println(objectName + " = " + hitungSpecial);
				break;
			case ("Stone"):
				hitungSpecial = calculatePointMultiplication(objectName, 0);
				System.out.println(objectName + " = " + hitungSpecial);
				break;
			}
			break;

		case ("calpoint_est_bfi"):
			ambilBFI = Integer.parseInt(
					driver.findElementByAndroidUIAutomator("UiSelector().description(\"calpoint_val_bfi\")").getText());
			System.out.println("ambilBFI = " + ambilBFI);

			switch (text) {
			case ("Star"):
				hitungBFI = calculatePointMultiplication(objectName, 50);
				System.out.println("hitungBFI = " + hitungBFI);
				break;
			case ("Gold"):
				hitungBFI = calculatePointMultiplication(objectName, 25);
				System.out.println("hitungBFI = " + hitungBFI);
				break;
			case ("Silver"):
			case ("Bronze"):
				hitungBFI = calculatePointMultiplication(objectName, 10);
				System.out.println("hitungBFI = " + hitungBFI);
				break;
			case ("Stone"):
				hitungBFI = calculatePointMultiplication(objectName, 0);
				System.out.println("hitungBFI = " + hitungBFI);
				break;
			}
			break;

		case ("calpoint_est_transaction50"):
			ambilTransaction50 = Integer.parseInt(
					driver.findElementByAndroidUIAutomator("UiSelector().description(\"calpoint_val_transaction50\")")
							.getText());
			System.out.println("ambilTransaction50 = " + ambilTransaction50);
			hitungTransaction50 = Integer.parseInt(driver
					.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")").getText());
			switch (text) {
			case ("Star"):
				if (hitungTransaction50 < 50) {
					hitungTransaction50 = 0;
				} else if (hitungTransaction50 >= 50) {
					hitungTransaction50 = 10;
				}
				System.out.println("hitungTransaction50 = " + hitungTransaction50);
				break;
			case ("Gold"):
				if (hitungTransaction50 < 50) {
					hitungTransaction50 = 0;
				} else if (hitungTransaction50 >= 50) {
					hitungTransaction50 = 7;
				}
				System.out.println("hitungTransaction50 = " + hitungTransaction50);
				break;
			case ("Silver"):
			case ("Bronze"):
				if (hitungTransaction50 < 50) {
					hitungTransaction50 = 0;
				} else if (hitungTransaction50 >= 50) {
					hitungTransaction50 = 5;
				}
				System.out.println("hitungTransaction50 = " + hitungTransaction50);
				break;
			case ("Stone"):
				hitungTransaction50 = 0;
				System.out.println("hitungTransaction50 = " + hitungTransaction50);
				break;
			}
			break;

		case ("calpoint_est_susanlogin"):
			ambilSusanLogin = Integer.parseInt(
					driver.findElementByAndroidUIAutomator("UiSelector().description(\"calpoint_val_susanlogin\")")
							.getText());
			System.out.println("ambilSusanLogin = " + ambilSusanLogin);
			hitungSusanLogin = Integer.parseInt(driver
					.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")").getText());
			switch (text) {
			case ("Star"):
			case ("Gold"):
			case ("Silver"):
			case ("Bronze"):
				if (hitungSusanLogin >= 1) {
					hitungSusanLogin = 3;
				} else if (hitungSusanLogin < 1) {
					hitungSusanLogin = 0;
				}
				System.out.println("hitungSusanLogin = " + hitungSusanLogin);
				break;
			case ("Stone"):
				hitungSusanLogin = 0;
				System.out.println("hitungSusanLogin = " + hitungSusanLogin);
				break;
			}
			break;

		case ("calpoint_est_banktransfer"):
			ambilBankTransfer = Integer.parseInt(
					driver.findElementByAndroidUIAutomator("UiSelector().description(\"calpoint_val_banktransfer\")")
							.getText());
			System.out.println("ambilBankTransfer = " + ambilBankTransfer);
			hitungBankTransfer = Integer.parseInt(driver
					.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")").getText());
			switch (text) {
			case ("Star"):
			case ("Gold"):
			case ("Silver"):
			case ("Bronze"):
				if (hitungBankTransfer < 2) {
					hitungBankTransfer = 0;
				} else if (hitungBankTransfer == 2) {
					hitungBankTransfer = 52;
				} else if (hitungBankTransfer == 3) {
					hitungBankTransfer = 78;
				} else if (hitungBankTransfer == 4) {
					hitungBankTransfer = 104;
				} else if (hitungBankTransfer >= 5) {
					hitungBankTransfer = 130;
				}
				System.out.println("hitungBankTransfer = " + hitungBankTransfer);
				break;
			case ("Stone"):
				hitungBankTransfer = 0;
				System.out.println("hitungBankTransfer = " + hitungBankTransfer);
				break;
			}
			break;

		case ("calpoint_est_otc"):
			ambilOTC = Integer.parseInt(
					driver.findElementByAndroidUIAutomator("UiSelector().description(\"calpoint_val_otc\")").getText());
			System.out.println("ambilOTC = " + ambilOTC);
			hitungOTC = Integer.parseInt(driver
					.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")").getText());
			switch (text) {
			case ("Star"):
			case ("Gold"):
			case ("Silver"):
			case ("Bronze"):
				hitungOTC = (Integer.parseInt(driver
						.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")").getText()))
						* 1;
				System.out.println("hitungOTC = " + hitungOTC);
				break;
			case ("Stone"):
				hitungOTC = (Integer.parseInt(driver
						.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")").getText()))
						* 0;
				System.out.println("hitungOTC = " + hitungOTC);
				break;
			}
			break;
		}
	}

	@SuppressWarnings("rawtypes")
	public void totalPoint(AndroidDriver driver, String objectName) throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		int ambilCashout = Integer.parseInt(
				driver.findElementByAndroidUIAutomator("UiSelector().description(\"viewcal_val_cashout\")").getText());
		int ambilCustomer = Integer.parseInt(
				driver.findElementByAndroidUIAutomator("UiSelector().description(\"viewcal_val_customer\")").getText());
		int ambilAquisition = Integer.parseInt(driver
				.findElementByAndroidUIAutomator("UiSelector().description(\"viewcal_val_acquisition\")").getText());

		swipeUp(driver);

		int ambilBFI = Integer.parseInt(
				driver.findElementByAndroidUIAutomator("UiSelector().description(\"viewcal_val_bfi\")").getText());
		int ambilTransaction50 = Integer.parseInt(driver
				.findElementByAndroidUIAutomator("UiSelector().description(\"viewcal_val_transaction50\")").getText());
		int ambilSusanLogin = Integer.parseInt(driver
				.findElementByAndroidUIAutomator("UiSelector().description(\"viewcal_val_susanlogin\")").getText());
		int ambilWeselHP = Integer.parseInt(
				driver.findElementByAndroidUIAutomator("UiSelector().description(\"viewcal_val_weselhp\")").getText());
		int ambilBankTransfer = Integer.parseInt(driver
				.findElementByAndroidUIAutomator("UiSelector().description(\"viewcal_val_banktransfer\")").getText());
		int ambilOTC = Integer.parseInt(
				driver.findElementByAndroidUIAutomator("UiSelector().description(\"viewcal_val_otc\")").getText());

		int ambilTotalPoin = Integer.parseInt(
				driver.findElementByAndroidUIAutomator("UiSelector().description(\"" + objectName + "\")").getText());
		int hitungTotalPoin = ambilCashout + ambilCustomer + ambilAquisition + ambilBFI + ambilTransaction50
				+ ambilSusanLogin + ambilWeselHP + ambilBankTransfer + ambilOTC;

		System.out.println("ambilTotalPoin = " + ambilTotalPoin);
		System.out.println("hitungTotalPoin = " + hitungTotalPoin);

	}

	@SuppressWarnings("rawtypes")
	public void choose(AndroidDriver driver, String objectName) throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		switch (objectName) {
		case ("rekening"):

			String eMoneyBalance = driver
					.findElementByAndroidUIAutomator("UiSelector().description(\"card_txt_emoneybalance\")").getText();
			Pattern pa = Pattern.compile("[([0-9]+)]");
			Matcher ma = pa.matcher(eMoneyBalance);
			String resultMoneyBalance = "";
			while (ma.find())
				resultMoneyBalance += ma.group();
			int resultMBalance = Integer.parseInt(resultMoneyBalance);

			String eMoneyThreshold = driver
					.findElementByAndroidUIAutomator("UiSelector().description(\"UiSelectorcard_txt_emoneythreshold\")")
					.getText();
			Pattern pb = Pattern.compile("[([0-9]+)]");
			Matcher mb = pb.matcher(eMoneyThreshold);
			String resultMoneyThreshold = "";
			while (mb.find())
				resultMoneyThreshold += mb.group();
			int resultMThreshold = Integer.parseInt(resultMoneyThreshold);

			int batasSaldoDekatMin = 0;
			batasSaldoDekatMin = (int) Math.round(resultMBalance / 1.2);
			// batasSaldoDekatMin += 1;

			if (resultMBalance < resultMThreshold) {
				try {
					// TODO: Find element by content-desc
					driver.findElementByAndroidUIAutomator("UiSelector().description(\"card_btn_liquidity\")").click();
					driver.findElementByAndroidUIAutomator("UiSelector().description(\"button1\")").click();
				} catch (Exception e) {
					System.out.println(e);
				}
			} else if (resultMThreshold > batasSaldoDekatMin && resultMThreshold <= resultMBalance) {
				// click button
			}
			break;
		case ("tunai"):

			break;

		}
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
}