package com.acneuro.test.automation.sikuli;

import java.io.File;

import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.visual.Canvas;
import org.sikuli.api.visual.DesktopCanvas;
import org.sikuli.script.App;

public class SikuliLibrary {

	public static App myApp = new App("Bsmdemul");

	public static App wakeUpCallForDise() throws InterruptedException {
		// Screen s = new Screen();
		//
		App myApp = new App("install-diseMP-client");

		myApp = App
				.open("C:\\Program Files\\diseMP\\4.2.5\\ACN-QA-UAT1\\Copy-ForAutomation\\install-diseMP-client.bat");
		App.close("diseMp");

		// Username and password
		return myApp;
	}

	public static App wakeUpCallForBosanova() {

		myApp = App.open("C:\\Program Files\\BOSaNOVA\\BOSaNOVA Secure\\Bsmdemul.exe");

		return myApp;

	}

	public static void closeDownBosanova() {

		myApp.close();

	}

	public static void sikulifindAndClickOnImageLocation(String imageFileLocation) {

		/*
		 * Screen is a base class provided by Sikuli. We need to create object
		 * for this screen class first, then only we can access all the methods
		 * provided by Sikuli.
		 */
		ScreenRegion s = new DesktopScreenRegion();

		Target target = new ImageTarget(new File(imageFileLocation));
		/*
		 * waiting for the target to appear on the screen and time out after 80
		 * seconds
		 */

		ScreenRegion r = s.wait(target, 80000);
		r = s.find(target); // locating target in the desktop screen

		// construct a canvas object of the type desktop canvas
		Canvas canvas = new DesktopCanvas();

		// add a box around the screen region
		canvas.addBox(r);
		// add a label on the screen region
		canvas.addLabel(r, "Found the image");
		// display the canvas for 3 seconds
		canvas.display(3);

		if (r == null) {
			System.out.println("not found");
		} else {
			Mouse mouse = new DesktopMouse();
			mouse.click(r.getCenter());
		}

	}

	public static boolean checkImageIsPresentAndReturnTrue(String imageFileLocation) {

		/*
		 * Screen is a base class provided by Sikuli. We need to create object
		 * for this screen class first, then only we can access all the methods
		 * provided by Sikuli.
		 */
		ScreenRegion s = new DesktopScreenRegion();

		Target target = new ImageTarget(new File(imageFileLocation));

		ScreenRegion r = s.wait(target, 80000); // waiting for the target to
												// appear on the screen
												// time out after 80 seconds
		r = s.find(target); // locating target in the desktop screen

		// construct a canvas object of the type desktop canvas
		Canvas canvas = new DesktopCanvas();

		// add a box around the screen region
		canvas.addBox(r);
		// add a label on the screen region
		canvas.addLabel(r, "Found the image");
		// display the canvas for 3 seconds
		canvas.display(3);

		if (r == null) {
			System.out.println("not found");
			return false;

		} else {
			return true;
		}
	}
}
