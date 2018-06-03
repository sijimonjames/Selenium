package com.acneuro.test.automation._06_Legacy_price_plan_WithOut_MyJOi;

import java.io.IOException;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.acneuro.test.automation.libraries.Constant;
import com.acneuro.test.automation.libraries.RestApiAutomation;
import com.acneuro.test.automation.mvno_project_library.FranceProjectSpecific;
import com.acneuro.test.automation.mvno_project_library.MVNOProjectSpecific;
import com.acneuro.test.automation.xmlParser.Xml_Parser_for_Legacy_Talkplans;

/*
 * @Author: Sijimon James
 * Test: France - English customer account creation, order a price plan Liberty
 * and subscription activation
 */

public class TC_01_FR_JOi_Fraternity_English_New_Customer {

	private static String orderId = "";
	private static String orderNumber = "";
	private static String newEmailId = "";
	private static String customerNumber = "";
	private static String customerId = "1234";
	private static final String talkplanToBeCreated = "JOi Fratenity";

	@Test
	public void CustomerActivation() throws InterruptedException, IOException {

		// create a customer account
		account_creation();
		// add price plan to the customer
		createOrder();
	}

	public void account_creation() throws IOException {

		newEmailId = FranceProjectSpecific.uniqueEmailIdForFRCustomer();

		// Send customer creation rest api request
		String requestCustomerCreation = Xml_Parser_for_Legacy_Talkplans.france_new_mvno_customer_creation(newEmailId);
		System.out.println("xml request:-----" + requestCustomerCreation);

		// send api request
		String xmlResponse = RestApiAutomation.httpPostTest_Latest(Constant.CREATE_CUSTOMER, requestCustomerCreation);
		// get customer details from response
		customerNumber = RestApiAutomation.getSpecificValueFromRestResponse(xmlResponse,
				"/CustomerResponse/Customer/CustomerNumber");
		Reporter.log("Customer Number: " + customerNumber, true);

		customerId = RestApiAutomation.getSpecificValueFromRestResponse(xmlResponse, "/CustomerResponse/Customer/Id");
		Reporter.log("Customer Id: " + customerId, true);
		// Change the email ID to receive the mails
		// Send a request to add order to the created customer
	}

	public void createOrder() {

		/*
		 * Send customer creation rest api request Need to update talkplans with
		 * right offers
		 */
		String requestToAddPricePlan = Xml_Parser_for_Legacy_Talkplans.FR_Customer_Create_Order_updated(customerId,
				talkplanToBeCreated);
		System.out.println("xml request:-----" + requestToAddPricePlan);

		 String OrderResponse =
		 RestApiAutomation.httpPostTest_Latest(Constant.ORDER_CREATION_REQUEST,
		 requestToAddPricePlan);
		
		 // Get order details if request is success
		 
		 orderNumber =
		 RestApiAutomation.getSpecificValueFromRestResponse(OrderResponse,
		 "/OrderEntryResponse/OrderEntry/Order/OrderNumber");
		 orderId =
		 RestApiAutomation.getSpecificValueFromRestResponse(OrderResponse,
		 "/OrderEntryResponse/OrderEntry/Order/OrderId");
		
		 Reporter.log("Order Number: " + orderNumber, true);
		 Reporter.log("Order ID: " + orderId, true);

	}

}