package com.acneuro.test.automation.direct_debit_And_Dunning_Tests;

import org.testng.annotations.Test;

import com.acneuro.test.automation.mvno_project_library.MVNOProjectSpecific;

// @Author: SIJIMON JAMES
// @Test: Reinstate of DD as payment method when CC retry OK after a NON-Fatal DD rejection

public class TestClass {

	@Test
	public static void FranceFrenchUrlSelection() {
		try {
			MVNOProjectSpecific.getProductIds("Fratenity");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}