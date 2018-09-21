package Lab;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class OpenBrowser {

	//@Test
	public void test() throws InterruptedException, MalformedURLException {

		// System.setProperty("webdriver.chrome.driver",
		// "C:/BddFramework/FrameWorkWeb/ChromeDriver/chromedriver.exe");
		//
		// WebDriver driver = new ChromeDriver();
		//
		// driver.get("https://www.google.com");
		//
		// Thread.sleep(5000);
		//
		// driver.close();
	}

	public static void main(String[] args) throws InterruptedException, MalformedURLException {
		DesiredCapabilities cap = DesiredCapabilities.firefox();

		// 1.define des Cap
		cap.setPlatform(Platform.WIN8_1);
		
		URL hubURL = new URL("http://localhost:4444/wd/hub");
		WebDriver driver = new RemoteWebDriver(hubURL, cap);
		
		driver.get("http://www.google.com");

		Thread.sleep(5000);
		System.out.println(driver.getTitle());
		
		driver.quit();
	}
}
