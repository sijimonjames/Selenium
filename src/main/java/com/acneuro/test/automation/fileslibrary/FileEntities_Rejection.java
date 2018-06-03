package com.acneuro.test.automation.fileslibrary;

import java.util.Date;

import com.acneuro.test.automation.libraries.Constant;
import com.acneuro.test.automation.mvno_project_library.MVNOProjectSpecific;

/**
 * @author sijimon james
 *
 */
public class FileEntities_Rejection {

	private String customerNumber; // Cues customer account number
	private String diseAccountnumber; // from wildbill tables
	private String dueAmount; // Amount
	private String rejectionreason; // Fatal or Non-fatal
	private String country;
	/**
	 * @return the customerNumber
	 */
	public String getCustomerNumber() {
		return customerNumber;
	}
	/**
	 * @param customerNumber the customerNumber to set
	 */
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	/**
	 * @return the diseAccountnumber
	 */
	public String getDiseAccountnumber() {
		return diseAccountnumber;
	}
	/**
	 * @param diseAccountnumber the diseAccountnumber to set
	 */
	public void setDiseAccountnumber(String diseAccountnumber) {
		this.diseAccountnumber = diseAccountnumber;
	}
	/**
	 * @return the dueAmount
	 */
	public String getDueAmount() {
		return dueAmount;
	}
	/**
	 * @param dueAmount the dueAmount to set
	 */
	public void setDueAmount(String dueAmount) {
		this.dueAmount = dueAmount;
	}
	/**
	 * @return the rejectionreason
	 */
	public String getRejectionreason() {
		return rejectionreason;
	}
	/**
	 * @param rejectionreason the rejectionreason to set
	 */
	public void setRejectionreason(String rejectionreason) {
		this.rejectionreason = rejectionreason;
	}
	/**
	 * @param customerNumber
	 * @param diseAccountnumber
	 * @param dueAmount
	 * @param rejectionreason
	 * @param country 
	 * @param creationDate
	 * @return 
	 */
	public FileEntities_Rejection(String customerNumber, String diseAccountnumber, String dueAmount, String rejectionreason, String country) {
		super();
		this.customerNumber = customerNumber;
		this.diseAccountnumber = diseAccountnumber;
		this.dueAmount = dueAmount;
		this.rejectionreason = rejectionreason;
		this.country = country;
	}
	/**
	 * 
	 */
	public FileEntities_Rejection() {
		super();
	}
	
	public static String ocxFileContentLine1() {
		return "Mandate Schema Type,Mandate Language,Mandate SEPA Reference (UMR),"
				+ "Mandate Description,Mandate Contract Reference,Mandate Issue Date,Signature Date,"
				+ "Signature Location,Debtor Name,Debtor Address Line 1,"
				+ "Debtor Address Line 2,Debtor Address Line 3,Debtor Address City/Town,Debtor Address Post Code,"
				+ "Debtor Address Country,Debtor Account BIC,Debtor Account IBAN,Creditor Name,"
				+ "Creditor Address Line 1,Creditor Address Line 2,Creditor Address Line 3,"
				+ "Creditor Address City/Town,Creditor Address Post Code,Creditor Address Country,"
				+ "Creditor Account BIC,Creditor Account IBAN,Domestic Mandate Reference,Domestic Mandate Country,"
				+ "Domestic Account Number,Domestic Account Checksum,Domestic Bank Code,"
				+ "Domestic Branch Code,Domestic Originator Number,Next Payment Amount,Next Payment Currency,"
				+ "Next Payment Date,Payment Frequency,Ultimate Debtor,Ultimate Creditor";
	}
	public static String ocxFileContentLine2(String customerNumber) {
		String date = MVNOProjectSpecific.dateToString(new Date(), "yyyy-MM-dd");
		return "SEPA_CORE,en,090"+customerNumber+"00000001,,090"+customerNumber+","+date+","+date+",,"
				+ "Test Reject and Upload in Orig,Test Reject and Upload in Origix Line1,Test Reject and Upload in Origix Line2,,"
				+ "Amsterdam,1054 MC,FR,"+Constant.FrSwiftCode+","+Constant.FrIbanNumber+",ACN Europe BV - CUST,"
				+ "Thomas R Maulthustraat 1-6,,,Amsterdam,1066 JR,NL,DEUTATWW,AT271910000038172002,,AT,00024236020,,19985,,,,,,FIRST,,";
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

}