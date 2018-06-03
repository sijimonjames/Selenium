package com.acneuro.test.automation.miscellaneous;

import org.testng.annotations.Test;

import com.acneuro.test.automation.libraries.Constant;
import com.acneuro.test.automation.libraries.RioGenerator;
import com.acneuro.test.automation.mvno_project_library.FranceProjectSpecific;

public class FrancePortInNumberAndRioCodeGenerator {

	@Test
	public void testToGiveRioCodeAndUniqueNumberForManualTest() {
		try {
			String uniquePortinNumber = FranceProjectSpecific.franceUniquePortInNumber();

			Thread.sleep(500);

			System.out.println("New Portin number: " + uniquePortinNumber + "");
			String rioCode = RioGenerator.generateRio(Constant.NUMPOS_OP_CODE_1,
					Constant.NUMPOS_DEFAULT_RIO_CODE.substring(2, 3), Constant.NUMPOS_DEFAULT_RIO_CODE.substring(3, 9),
					uniquePortinNumber);

			System.out.println("RIO Code: " + rioCode);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}