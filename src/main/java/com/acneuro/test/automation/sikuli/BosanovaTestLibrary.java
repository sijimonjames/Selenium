package com.acneuro.test.automation.sikuli;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Screen;

public class BosanovaTestLibrary {

	private static Screen screen = new Screen();

	public static void wakeUpCallForBosanovaAndLogin() throws InterruptedException, FindFailed {

		SikuliLibrary.wakeUpCallForBosanova();

		Thread.sleep(10000);

		boolean sessionActive = SikuliLibrary
				.checkImageIsPresentAndReturnTrue(".//sikuli_Images/Session_Existing_1.PNG");
		// Click on session button using the image
		if (sessionActive == true) {

			SikuliLibrary.sikulifindAndClickOnImageLocation(".//sikuli_Images/session_bosanova.PNG");

			// Down arrow twice to close all connections
			screen.type(Key.DOWN + Key.DOWN + Key.ENTER);
			Thread.sleep(5000);
		}

		// open a new session

		SikuliLibrary.sikulifindAndClickOnImageLocation(".//sikuli_Images/session_bosanova.PNG");

		screen.type(Key.DOWN + Key.DOWN + Key.RIGHT + Key.ENTER);

		// Wait for the image
		screen.wait(".//sikuli_Images/Username_Password_bosanova.PNG");

		// Username and password
		screen.type("sijames" + Key.TAB + "siji2016" + Key.ENTER);

		Thread.sleep(5000);

	}

	public static void franceBosanovaLoginFrance() {

		// press tab key 12 times
		for (int i = 1; i < 13; i++) {
			screen.type(Key.TAB);
		}

		screen.type("1" + Key.ENTER);

	}

	public static void franceBosanovaLoginUK() {

		// press tab key 10 times
		for (int i = 1; i < 11; i++) {
			screen.type(Key.TAB);
		}

		screen.type("1" + Key.ENTER);

	}

}
