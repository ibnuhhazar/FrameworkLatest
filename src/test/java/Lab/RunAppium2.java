package Lab;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

@Test
public class RunAppium2 {

	public AndroidDriver<MobileElement> driver;
	public DesiredCapabilities cap;
	public AppiumDriverLocalService service;
	Properties capabilitiesRepo = null;
	
	
	@BeforeTest
	public void startServer() throws IOException {
		System.setProperty(AppiumServiceBuilder.NODE_PATH, "C:\\Program Files\\nodejs\\node.exe");
		System.setProperty(AppiumServiceBuilder.APPIUM_PATH, "C:\\Users\\17053682\\node_modules\\appium\\build\\lib\\main.js");
		
		service = AppiumDriverLocalService
				.buildService(new AppiumServiceBuilder()
				.usingAnyFreePort()
				.withIPAddress("127.0.0.1"));
		service.start();
		
		System.out.println("Service Started");
		
		capabilitiesRepo = new Properties();
		FileInputStream fsCapabilities = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\test\\java\\com\\test\\config\\Capabilities.properties");
		capabilitiesRepo.load(fsCapabilities);
		
		cap = new DesiredCapabilities();
		// Specify the device name (any name)
		cap.setCapability("deviceName", capabilitiesRepo.getProperty("2deviceName"));

		// Platform version
		cap.setCapability("platformVersion", capabilitiesRepo.getProperty("2platformVersion"));

		// platform name
		cap.setCapability("platformName", "Android");

		cap.setCapability("appWaitActivity", capabilitiesRepo.getProperty("appActivity"));

		cap.setCapability("appPackage", capabilitiesRepo.getProperty("appPackage"));

		cap.setCapability("autoGrantPermissions", true);

		cap.setCapability("appActivity", capabilitiesRepo.getProperty("appActivity"));

		cap.setCapability("automationName", "uiautomator2");

		driver = new AndroidDriver<MobileElement>(service.getUrl(), cap);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void startingAppiumServer() throws InterruptedException {
		Thread.sleep(5000);
	}

	@AfterTest
	public void stopServer() {
		driver.quit();
		
		service.stop();
		System.out.println("Current status of server" + service.isRunning());
	}
}
