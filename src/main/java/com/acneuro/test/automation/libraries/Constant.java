package com.acneuro.test.automation.libraries;

public interface Constant {

	// File for datadriven tests
	String DEFAULT_TESTDATA_LOCATION = "./src/main/resources/Test_data";
	String configFileName = "config.xlsx";
	String FranceIdFileName = "C:\\testing\\workspace\\mvno\\src\\main\\resources\\Test_data\\test.doc";

	// Path to drivers
	String IE_Driver_Path = "./src/test/resources/Drivers/IEDriverServer.exe";
	String Chrome_Driver_Path = System.getProperty("os.name").contains("Mac")
			? "./src/test/resources/Drivers/chromedriver" : "./src/test/resources/Drivers/chromedriver.exe";

	// Common Password for all account created
	String DEFAULT_MYJOI_PASSWORD = "test123";

	// Bank details (Direct debit) for UK
	String Bank_Name_UK = "ING";
	String UK_Account_Number = "01234567";
	String UK_Sort_code = "123456";

	// Bank details (Direct debit) for France
	String FrIbanNumber = "FR1420041010050500013M02606";
	String FrSwiftCode = "ABNAFRPPXXX";

	// Common test Credit card details for both UK and France
	String Card_Type1 = "Visa";
	String Type1_CC_number = "4444333322221111";
	String Type1_CC_CVV = "123";

	String NUMPOS_DEFAULT_RIO_CODE = "02E01QQ03KEC";
	String NUMPOS_OP_CODE_1 = "16";
	String NUMPOS_OP_CODE_2 = "56";

	// test ICCID's for Shipping
	// String ICCID_2 = "89331067130001531399";
	String ICCID_1_GB = "8944200012851004865";
	String ICCID_1_FR = "89331067130002051397";
	String ICCID_2_GB = "8944200012851004949";
	String ICCID_2_FR = "89331067130002039392";

	//// test ICCID's for Shipping
	String billsrvWorkerUrl = "https://uatcocoon.eu.acncorp.com/billsrv/gui/";
	String DDMANDATEWorkerUrl = "http://ams1-uatjob-02:8080/sepa-mandate-update-disemp-web/";
	String JACKALWORKERSURL = "https://uatcocoon.eu.acncorp.com/jackal-batch/";
	

	// http, post request connections
	String cue_Update = "http://ams1-uatcuerest-01/orc/cue/updateProcess";
	String cue_Execute = "http://ams1-uatcuerest-01/orc/oe/execute";
	String CIN_EXECUTE = "http://ams1-uatcuerest-01/orc/cin/execute";
	String CREATE_CUSTOMER = "http://ams1-uatcuerest-01/orc/cust/createCustomer";
	String ORDER_CREATION_REQUEST = "http://ams1-uatcuerest-01/orc/oe/createOrderEntry";
	String DISE_REQUEST = "http://eudbl01:1049/ws/dise3g/services/AccountPort";

	// https, post Numpos connections
	String NUMPOS_INCOMING_REQUEST = "http://ams1-uatsupse-01/numpos/incoming/request";

	// Bill srv Worker retry count
	Integer DEFAULT_RETRY_COUNT = 35;
	
	Integer PROVISIONING_RETRY_COUNT = 10;
	// time is in millisecond
	Integer DEFAULT_SLEEP_TIME = 5000;
	Integer DEFAULT_SHORT_SLEEP_TIME = 10000;
	Integer DEFAULT_MEDIUM_SLEEP_TIME = 30000;
	Integer DEFAULT_LONG_SLEEP_TIME = 1000 * 90;

	// PAC code details
	String FR_Portin_Number = "06%";
	String UK_Portin_Number = "07%";
	String UK_PAC_CODE = "LCS627751";

	// Price plans fees
	String UK_Liberty_FEE = "�17.99"; // Don't add space for UK
	String FR_EN_Liberty_FEE = "� 24.99"; // Don't remove space for France
	String FR_FR_Liberty_FEE = "� 24,99"; // Don't remove space for France

	String FR_FR_Egalitte_FEE = "� 11,99";
	String FR_EN_Egalitte_FEE = "� 11.99";

	// Connection Fee
	String UK_CONNECTION_FEE = "�0.00";
	String FR_EN_CONNECTION_FEE = "� 0.00";
	String FR_FR_CONNECTION_FEE = "� 0,00";

	// Total price
	String UK_LIBERTY_TOTAL_PRICE = "�17.99";
	String FR_EN_LIBERTY_TOTAL_PRICE = "� 24.99";
	String FR_FR_LIBERTY_TOTAL_PRICE = "� 24,99";
	String FR_FR_EGALITTE_TOTAL_PRICE = "� 11,99";
	String FR_EN_EGALITTE_TOTAL_PRICE = "� 11.99";

	// Total price in datacash page
	String UK_LIBERTY_DATACASH_PRICE = "17.99 GBP";
	String FR_LIBERTY_DATACASH_PRICE = "24.99 EUR";
	String FR_EGALITTE_DATACASH_PRICE = "11.99 EUR";

	// SIM price
	String FR_EN_SIM_PRICE = "� 0.00";
	String FR_FR_SIM_PRICE = "� 0,00";
	String UK_SIM_PRICE = "� 0.00";

	// Handset fields for Block and Unblock Handset
	String HANDSET_MODEL = "SAMSUNG";
	String HANDSET_IMEI = "012345678912345";

	// Personal Email Address
	String PERSONAL_EMAIL_ADRRESS = "amsterdamtestteam@acneuro.com";

	// Test Team Credentials
	String COMMON_USERNAME = "srv_jenkins";
	String COMMON_PASSWORD = "/Dxd2za+dT";
	String My_Password = "SIJI2016";
	String WILDBILL_HOSTNAME = "ams1-uatjob-09";
	String DD_HOSTNAME = "ams1-uatjob-02";
	String JACKAL_HOSTNAME = "ams-uatjob-18";
}