package com.acneuro.test.automation.libraries;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.acneuro.test.automation.mvno_project_library.Country;
import com.acneuro.test.automation.mvno_project_library.MVNOProjectSpecific;

import javax.xml.parsers.*;

public class WebServicesAutomationLibrary {


	public static HttpURLConnection httpPostTest(String Url, String request, String country) {

		HttpURLConnection httpConn = null;
		
		
		try {
			URL url = new URL(Url);
			httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setConnectTimeout(10000);
			httpConn.setDoOutput(true);
			httpConn.setRequestMethod("POST");
			httpConn.setRequestProperty("SOAPAction", "");

			byte[] authEncBytes = Base64.getEncoder().encode(Country.valueOf(country).getDiseAuthentication().getBytes());
			String authStringEnc = new String(authEncBytes);
			// System.out.println("Base64 encoded auth string: " +
			// authStringEnc);
			httpConn.setRequestProperty("Authorization", "Basic " + authStringEnc);
			httpConn.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");

			OutputStream os = httpConn.getOutputStream();
			os.write(request.toString().getBytes());
			os.flush();
			if (httpConn.getResponseCode() != HttpURLConnection.HTTP_CREATED
					&& httpConn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				ByteArrayOutputStream buffer = new ByteArrayOutputStream();
				InputStream input = httpConn.getErrorStream();
				IOUtils.copy(input, buffer);
				String errorMessage = new String(buffer.toByteArray());
				System.err.println(
						"Failed : HTTP error code : " + httpConn.getResponseCode() + " errorMessage:" + errorMessage);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return httpConn;
	}

	public static String getResponseValueFromDise(String request, String nodeValue,String country) {

		HttpURLConnection httpConn = null;
		String responseString = "";
		String outputString = "";
		BufferedReader br = null;
		String Result = "";
		// String request = requestToDiseToGetAccountDetails(diseAccountNumber);
		String url = Constant.DISE_REQUEST;
		httpConn = httpPostTest(url, request,country);

		try {
			br = new BufferedReader(new InputStreamReader((httpConn.getInputStream())));

			// Write the SOAP message response to a String.
			while ((responseString = br.readLine()) != null) {
				outputString = outputString + responseString;
				System.out.println(outputString);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
		 * Parse the String output to a org.w3c.dom.Document and be able to
		 * reach every node with the org.w3c.dom API.
		 */

		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document;

			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(outputString));

			document = db.parse(is);
			NodeList nodeLst = document.getElementsByTagName(nodeValue);
			Result = nodeLst.item(0).getTextContent();
			System.out.println(String.format("%s: %s", nodeValue, Result));

		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		} finally {
			try {
				httpConn.disconnect();
				System.out.println("http cunnection is successfully closed");
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new Error("No connections are closed");
			}
		}
		return Result;
	}

	public static String requestToDiseToGetAccountDetails(String diseAccountNumber) {
		return "<?xml version=\"1.0\" encoding=\"utf-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
				+ "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soapenv:Body>"
				+ "<ns2:QueryAccount xmlns:ns2=\"http://mdsuk.com/ws/dise3g/account/definition\">"
				+ "<ns2:Request><AccountNumber>" + diseAccountNumber
				+ "</AccountNumber><Datasets><Dataset>PAYMENT_DETAILS</Dataset>"
				+ "</Datasets></ns2:Request></ns2:QueryAccount></soapenv:Body></soapenv:Envelope>";
	}

	public static String requestToDiseToUpdatePaymentMethodForCreditCard(String diseAccountNumber,
			String lastAmendedDate) {
		String date = MVNOProjectSpecific.dateToString(new Date(), "yyyy-MM-dd");
		return "<?xml version=\"1.0\" encoding=\"utf-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
				+ "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soapenv:Body>"
				+ "<ns2:UpdateRecurringPaymentDetails xmlns:ns2=\"http://mdsuk.com/ws/dise3g/account/definition\">"
				+ "<ns2:Request><ExternalReference>ACC-ID:SIJI0001</ExternalReference>" + "<AccountNumber>"
				+ diseAccountNumber + "</AccountNumber>"
				+ "<PaymentType>VISA</PaymentType><PaymentDetails><PaymentCardDetails>"
				+ "<CancellationInformation><ReasonCode>APCC</ReasonCode><CancellationDate>" + date
				+ "</CancellationDate>" + "</CancellationInformation>" + "</PaymentCardDetails></PaymentDetails>"
				+ "<LastAmendedDate>" + lastAmendedDate + "</LastAmendedDate></ns2:Request>"
				+ "</ns2:UpdateRecurringPaymentDetails></soapenv:Body></soapenv:Envelope>";
	}

	public static String requestToDiseToUpdatePaymentMethodForDirectDebit(String diseAccountNumber, String lastAmendedDate) {

		String date = MVNOProjectSpecific.dateToString(new Date(), "yyyy-MM-dd");
		return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:def=\"http://mdsuk.com/ws/dise3g/account/definition\">"
				+ "<soapenv:Header/><soapenv:Body><def:UpdateRecurringPaymentDetails><def:Request>"
				+ "<ExternalReference>ACC-ID:SIJI00002</ExternalReference>" + "<AccountNumber>"
				+ diseAccountNumber + "</AccountNumber>"
				+ "<PaymentType>DD</PaymentType><PaymentDetails><DirectDebitDetails>" + "<CancellationInformation>"
				+ "<ReasonCode>APCC</ReasonCode>" + "<CancellationDate>" + date + "</CancellationDate>"
				+ "</CancellationInformation>" + "</DirectDebitDetails></PaymentDetails>" + "<LastAmendedDate>"
				+ lastAmendedDate + "</LastAmendedDate></def:Request>"
				+ "</def:UpdateRecurringPaymentDetails></soapenv:Body></soapenv:Envelope>";
	}
}