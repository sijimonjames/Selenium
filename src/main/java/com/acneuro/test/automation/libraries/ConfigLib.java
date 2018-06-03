package com.acneuro.test.automation.libraries;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;

public class ConfigLib {

	// opening the required browser and page
	public static WebDriver driver;
	public static String username = System.getenv().get("USERNAME");
	
	// Create a new instance of the search page class
//    // and initialise any WebElement fields in it.
//	My_Subscription_Manage_Settings_Page page = PageFactory.initElements(driver, My_Subscription_Manage_Settings_Page.class);


	@BeforeMethod
	public void preCondition() {
		System.out.println(String.format("Running Tests on user name \'%s\'", username));
		String configFile = Constant.DEFAULT_TESTDATA_LOCATION + "/" + Constant.configFileName;
		// System.out.println(configFile);
		String browserType = Generic.getCellValue(configFile, "Browser_selection", 0, 1);
		Reporter.log("Tests are Running on: " + browserType, true);

		if (browserType.equals("Internet Explorer")) {
			System.setProperty("webdriver.ie.driver", Constant.IE_Driver_Path);
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.FORCE_CREATE_PROCESS, true);
			capabilities.setCapability(InternetExplorerDriver.IE_SWITCHES, "-private");
			capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
			driver = new InternetExplorerDriver();			
		} else if (browserType.equals("Google Chrome")) {
			System.out.println("###### OS: " + System.getProperty("os.name"));
			System.setProperty("webdriver.chrome.driver", Constant.Chrome_Driver_Path);
			driver = new ChromeDriver();
		} else {
			driver = new FirefoxDriver();
		}
		// maximize browser window
		driver.manage().window().maximize();

		// implicit wait to complete each browser action
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	public static void postCondition() {
		driver.quit();
		System.out.println("Webpage is closed down");
	}
}