package com.acneuro.test.automation.france_InProgress;



import java.util.Date;

import org.testng.annotations.Test;

import com.acneuro.test.automation.mvno_project_library.MVNOProjectSpecific;

public class TestClass {

	@Test
	public void accountCreation() {
		
		String date = MVNOProjectSpecific.dateToString(new Date(), "yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		
		System.out.println("date format: "+date);
		
	}

}