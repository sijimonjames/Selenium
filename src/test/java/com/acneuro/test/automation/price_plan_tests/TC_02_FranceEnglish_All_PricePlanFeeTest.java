package com.acneuro.test.automation.price_plan_tests;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.acneuro.test.automation.libraries.ConfigLib;
import com.acneuro.test.automation.libraries.Constant;
import com.acneuro.test.automation.mvno_project_library.FranceProjectSpecific;
import com.acneuro.test.automation.mvno_project_library.MVNOProjectSpecific;

/**
 * @author SIJIMON JAMES
 * @Tests: France Price Plan tests. Takes screen shot of each page of Order
 *         entry process, test will verify Liberty/Egalitte Fee, Connection Fee and Total
 *         price
 */
public class TC_02_FranceEnglish_All_PricePlanFeeTest extends ConfigLib {

	@Test
	public void franceAllFeeTests() throws IOException {
		// clear the directory

		FileUtils.deleteDirectory(new File(".//ScreenShots/Connection_Fee_Tests/Egalitte_PP/France_English"));

		// Egalitte price plan
		france_fees_tests_in_English_For_Egalitte_Price_plan();

		FileUtils.deleteDirectory(new File(".//ScreenShots/Connection_Fee_Tests/Liberty_PP/France_English"));
		// Liberty Price plan
		france_fees_tests_in_English_For_Liberty_Price_plan();
	}

	public static void france_fees_tests_in_English_For_Egalitte_Price_plan() {

		FranceProjectSpecific.FranceEnglishUrlSelection();

		// Select the price plan
		MVNOProjectSpecific.orderCreationPageInEnglish();
		MVNOProjectSpecific.pricePlanSelection_France_English("joiEgalitteFR");
		MVNOProjectSpecific.SimTypeSelectionForPricePlan("combi", "egalitte");

		/*
		 * verify fees on Mobile Subscription Offer page, after Price plan and
		 * sim type is selected
		 */
		System.out.println("Fees in Offer page");
		MVNOProjectSpecific.VariousFeesForPricePlans(Constant.FR_EN_Egalitte_FEE, Constant.FR_EN_CONNECTION_FEE,
				Constant.FR_EN_EGALITTE_TOTAL_PRICE);
		System.out.println("Fees are as expected in Offer page");
		MVNOProjectSpecific.takeScreenShot(
				".//ScreenShots/Connection_Fee_Tests/Egalitte_PP/France_English/1_EGALITTE_Offer_Page.png");
		/*
		 * verify fees on additional product page
		 */
		MVNOProjectSpecific.ourOfferPageAddToBasketButton();
		System.out.println("Fees in additional products page");
		MVNOProjectSpecific.VariousFeesForPricePlans(Constant.FR_EN_Egalitte_FEE, Constant.FR_EN_CONNECTION_FEE,
				Constant.FR_EN_EGALITTE_TOTAL_PRICE);
		System.out.println("Fees are as expected in additional products page");
		MVNOProjectSpecific.takeScreenShot(
				".//ScreenShots/Connection_Fee_Tests/Egalitte_PP/France_English/2_EGALITTE_additional_products_page.png");

		/*
		 * verify fees on offer basket page
		 */
		MVNOProjectSpecific.add_to_basket_without_porting();
		System.out.println("Fees in offer basket page");
		MVNOProjectSpecific.VariousFeesForPricePlans(Constant.FR_EN_Egalitte_FEE, Constant.FR_EN_CONNECTION_FEE,
				Constant.FR_EN_EGALITTE_TOTAL_PRICE);
		System.out.println("Fees are as expected in offer basket page");
		MVNOProjectSpecific.takeScreenShot(
				".//ScreenShots/Connection_Fee_Tests/Egalitte_PP/France_English/3_EGALITTE_offer_basket_page.png");

		/*
		 * verify fees on create account page
		 */
		MVNOProjectSpecific.checkOutOrder();
		System.out.println("Fees in create account page");
		MVNOProjectSpecific.VariousFeesForPricePlans(Constant.FR_EN_Egalitte_FEE, Constant.FR_EN_CONNECTION_FEE,
				Constant.FR_EN_EGALITTE_TOTAL_PRICE);
		System.out.println("Fees are as expected in create account page");
		MVNOProjectSpecific.takeScreenShot(
				".//ScreenShots/Connection_Fee_Tests/Egalitte_PP/France_English/4_EGALITTE_create_account_page.png");
		/*
		 * verify fees on Create account-Personal details page
		 */
		FranceProjectSpecific.FranceCustomerAccountCreation();
		System.out.println("Fees in Create account-Personal details page");
		MVNOProjectSpecific.VariousFeesForPricePlans(Constant.FR_EN_Egalitte_FEE, Constant.FR_EN_CONNECTION_FEE,
				Constant.FR_EN_EGALITTE_TOTAL_PRICE);
		System.out.println("Fees are as expected in Create account-Personal details page");
		MVNOProjectSpecific.takeScreenShot(
				".//ScreenShots/Connection_Fee_Tests/Egalitte_PP/France_English/5_EGALITTE_account_Personal_details_page.png");

		/*
		 * verify fees on account creation success page
		 */
		MVNOProjectSpecific.Customer_details("Mrs.", "ACNEuro", "MVNO", "Fees Automation", "01121960", "0123467890");
		FranceProjectSpecific.uploadCustomerIdCopyForFrenchEnglish();
		MVNOProjectSpecific.contactAndBillingAdress("75007", "38", "1");

		System.out.println("Fees in account creation success page");
		MVNOProjectSpecific.VariousFeesForPricePlans(Constant.FR_EN_Egalitte_FEE, Constant.FR_EN_CONNECTION_FEE,
				Constant.FR_EN_EGALITTE_TOTAL_PRICE);
		System.out.println("Fees are as expected in account creation success page");
		MVNOProjectSpecific.takeScreenShot(
				".//ScreenShots/Connection_Fee_Tests/Egalitte_PP/France_English/6_EGALITTE_account_creation_success_page.png");

		/*
		 * verify fees on mobile subscription details page
		 */
		MVNOProjectSpecific.continueButtonAfterAccountCreation();

		System.out.println("Fees in mobile subscription details page");
		MVNOProjectSpecific.VariousFeesForPricePlans(Constant.FR_EN_Egalitte_FEE, Constant.FR_EN_CONNECTION_FEE,
				Constant.FR_EN_EGALITTE_TOTAL_PRICE);
		System.out.println("Fees are as expected in mobile subscription details page");
		MVNOProjectSpecific.takeScreenShot(
				".//ScreenShots/Connection_Fee_Tests/Egalitte_PP/France_English/7_EGALITTE_mobile_subscription_details_page.png");

		/*
		 * verify fees on Payment details page
		 */
		MVNOProjectSpecific.continueToStep3WithoutPorting();

		System.out.println("Fees in Payment details page");
		MVNOProjectSpecific.VariousFeesForPricePlans(Constant.FR_EN_Egalitte_FEE, Constant.FR_EN_CONNECTION_FEE,
				Constant.FR_EN_EGALITTE_TOTAL_PRICE);
		System.out.println("Fees are as expected in Payment details page");
		MVNOProjectSpecific.takeScreenShot(
				".//ScreenShots/Connection_Fee_Tests/Egalitte_PP/France_English/8_EGALITTE_Payment_details_page.png");

		/*
		 * verify fees on Order summary page
		 */
		MVNOProjectSpecific.franceBankDetailsAndClickToContinue(Constant.FrIbanNumber, Constant.FrSwiftCode);

		System.out.println("Fees in Order summary page");
		MVNOProjectSpecific.VariousFeesForPricePlans(Constant.FR_EN_Egalitte_FEE, Constant.FR_EN_CONNECTION_FEE,
				Constant.FR_EN_EGALITTE_TOTAL_PRICE);
		System.out.println("Fees are as expected in Order summary page");
		MVNOProjectSpecific.takeScreenShot(
				".//ScreenShots/Connection_Fee_Tests/Egalitte_PP/France_English/9_EGALITTE_Order_summary_page.png");

		// Payment page
		MVNOProjectSpecific.orderSummaryPageForNewCustomer("999999");

		System.out.println("Check total price in Payment page");
		MVNOProjectSpecific.verifyPricePlanAmountInDatacashPage(Constant.FR_EGALITTE_DATACASH_PRICE);
		System.out.println("Total price is as expected in Payment page");
		MVNOProjectSpecific.takeScreenShot(
				".//ScreenShots/Connection_Fee_Tests/Egalitte_PP/France_English/10_EGALITTE_Datacash_page.png");

		// complete the order
		MVNOProjectSpecific.Payment_Datacash(Constant.Card_Type1, Constant.Type1_CC_number, "2018",
				Constant.Type1_CC_CVV);

		// close the webpage
		driver.quit();

		Reporter.getOutput();
	}

	public void france_fees_tests_in_English_For_Liberty_Price_plan() {

		// get the URL of France English website
		FranceProjectSpecific.FranceEnglishUrlSelection();

		// Select the price plan
		MVNOProjectSpecific.orderCreationPageInEnglish();
		MVNOProjectSpecific.pricePlanSelection_France_English("joiLibertyFR");
		MVNOProjectSpecific.SimTypeSelectionForPricePlan("combi", "liberty");

		/*
		 * verify fees on Mobile Subscription Offer page, after Price plan and
		 * sim type is selected
		 */
		System.out.println("Fees in Offer page");
		MVNOProjectSpecific.VariousFeesForPricePlans(Constant.FR_EN_Liberty_FEE, Constant.FR_EN_CONNECTION_FEE,
				Constant.FR_EN_LIBERTY_TOTAL_PRICE);
		System.out.println("Fees are as expected in Offer page");
		MVNOProjectSpecific.takeScreenShot(
				".//ScreenShots/Connection_Fee_Tests/Liberty_PP/France_English/1_Liberty_Offer_Page.png");
		/*
		 * verify fees on additional product page
		 */
		MVNOProjectSpecific.ourOfferPageAddToBasketButton();
		System.out.println("Fees in additional products page");
		MVNOProjectSpecific.VariousFeesForPricePlans(Constant.FR_EN_Liberty_FEE, Constant.FR_EN_CONNECTION_FEE,
				Constant.FR_EN_LIBERTY_TOTAL_PRICE);
		System.out.println("Fees are as expected in additional products page");
		MVNOProjectSpecific.takeScreenShot(
				".//ScreenShots/Connection_Fee_Tests/Liberty_PP/France_English/2_Liberty_additional_products_page.png");

		/*
		 * verify fees on offer basket page
		 */
		MVNOProjectSpecific.add_to_basket_without_porting();
		System.out.println("Fees in offer basket page");
		MVNOProjectSpecific.VariousFeesForPricePlans(Constant.FR_EN_Liberty_FEE, Constant.FR_EN_CONNECTION_FEE,
				Constant.FR_EN_LIBERTY_TOTAL_PRICE);
		System.out.println("Fees are as expected in offer basket page");
		MVNOProjectSpecific.takeScreenShot(
				".//ScreenShots/Connection_Fee_Tests/Liberty_PP/France_English/3_Liberty_offer_basket_page.png");

		/*
		 * verify fees on create account page
		 */
		MVNOProjectSpecific.checkOutOrder();
		System.out.println("Fees in create account page");
		MVNOProjectSpecific.VariousFeesForPricePlans(Constant.FR_EN_Liberty_FEE, Constant.FR_EN_CONNECTION_FEE,
				Constant.FR_EN_LIBERTY_TOTAL_PRICE);
		System.out.println("Fees are as expected in create account page");
		MVNOProjectSpecific.takeScreenShot(
				".//ScreenShots/Connection_Fee_Tests/Liberty_PP/France_English/4_Liberty_create_account_page.png");
		/*
		 * verify fees on Create account-Personal details page
		 */
		FranceProjectSpecific.FranceCustomerAccountCreation();
		System.out.println("Fees in Create account-Personal details page");
		MVNOProjectSpecific.VariousFeesForPricePlans(Constant.FR_EN_Liberty_FEE, Constant.FR_EN_CONNECTION_FEE,
				Constant.FR_EN_LIBERTY_TOTAL_PRICE);
		System.out.println("Fees are as expected in Create account-Personal details page");
		MVNOProjectSpecific.takeScreenShot(
				".//ScreenShots/Connection_Fee_Tests/Liberty_PP/France_English/5_Liberty_account_Personal_details_page.png");

		/*
		 * verify fees on account creation success page
		 */
		MVNOProjectSpecific.Customer_details("Mrs.", "ACNEuro", "MVNO", "Fees Automation", "01121960", "0123467890");
		FranceProjectSpecific.uploadCustomerIdCopyForFrenchEnglish();
		MVNOProjectSpecific.contactAndBillingAdress("75007", "38", "1");

		System.out.println("Fees in account creation success page");
		MVNOProjectSpecific.VariousFeesForPricePlans(Constant.FR_EN_Liberty_FEE, Constant.FR_EN_CONNECTION_FEE,
				Constant.FR_EN_LIBERTY_TOTAL_PRICE);
		System.out.println("Fees are as expected in account creation success page");
		MVNOProjectSpecific.takeScreenShot(
				".//ScreenShots/Connection_Fee_Tests/Liberty_PP/France_English/6_Liberty_account_creation_success_page.png");

		/*
		 * verify fees on mobile subscription details page
		 */
		MVNOProjectSpecific.continueButtonAfterAccountCreation();

		System.out.println("Fees in mobile subscription details page");
		MVNOProjectSpecific.VariousFeesForPricePlans(Constant.FR_EN_Liberty_FEE, Constant.FR_EN_CONNECTION_FEE,
				Constant.FR_EN_LIBERTY_TOTAL_PRICE);
		System.out.println("Fees are as expected in mobile subscription details page");
		MVNOProjectSpecific.takeScreenShot(
				".//ScreenShots/Connection_Fee_Tests/Liberty_PP/France_English/7_Liberty_mobile_subscription_details_page.png");

		/*
		 * verify fees on Payment details page
		 */
		MVNOProjectSpecific.continueToStep3WithoutPorting();

		System.out.println("Fees in Payment details page");
		MVNOProjectSpecific.VariousFeesForPricePlans(Constant.FR_EN_Liberty_FEE, Constant.FR_EN_CONNECTION_FEE,
				Constant.FR_EN_LIBERTY_TOTAL_PRICE);
		System.out.println("Fees are as expected in Payment details page");
		MVNOProjectSpecific.takeScreenShot(
				".//ScreenShots/Connection_Fee_Tests/Liberty_PP/France_English/8_Liberty_Payment_details_page.png");

		/*
		 * verify fees on Order summary page
		 */
		MVNOProjectSpecific.franceBankDetailsAndClickToContinue(Constant.FrIbanNumber, Constant.FrSwiftCode);

		System.out.println("Fees in Order summary page");
		MVNOProjectSpecific.VariousFeesForPricePlans(Constant.FR_EN_Liberty_FEE, Constant.FR_EN_CONNECTION_FEE,
				Constant.FR_EN_LIBERTY_TOTAL_PRICE);
		System.out.println("Fees are as expected in Order summary page");
		MVNOProjectSpecific.takeScreenShot(
				".//ScreenShots/Connection_Fee_Tests/Liberty_PP/France_English/9_Liberty_Order_summary_page.png");

		// Payment page
		MVNOProjectSpecific.orderSummaryPageForNewCustomer("999999");

		System.out.println("Check total price in Payment page");
		MVNOProjectSpecific.verifyPricePlanAmountInDatacashPage(Constant.FR_LIBERTY_DATACASH_PRICE);
		System.out.println("Total price is as expected in Payment page");
		MVNOProjectSpecific.takeScreenShot(
				".//ScreenShots/Connection_Fee_Tests/Liberty_PP/France_English/10_Liberty_Datacash_page.png");

		// complete the order
		MVNOProjectSpecific.Payment_Datacash(Constant.Card_Type1, Constant.Type1_CC_number, "2018",
				Constant.Type1_CC_CVV);

		// close the webpage
		driver.quit();

		Reporter.getOutput();
	}
}
