package com.acneuro.test.automation.libraries;

import org.openqa.selenium.WebDriver;

public abstract class BaseClass {
	
	public static WebDriver driver;
	public static boolean bResult;

	public BaseClass(WebDriver driver) {

		ConfigLib.driver = driver;
		BaseClass.bResult = true;
	}

}
