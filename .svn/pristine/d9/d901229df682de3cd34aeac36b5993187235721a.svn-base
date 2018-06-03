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
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class WebServicesAutomationLibrary2 {
	private static final String FranceDiseAuthentication = "ACNWS090:WEBCONN";
	// private static final String UKDiseAuthentication = "ACNWS010:WEBCONN";

	public static HttpURLConnection httpPostTest(String Url, String request) {

		HttpURLConnection httpConn = null;

		try {
			URL url = new URL(Url);
			httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setConnectTimeout(10000);
			httpConn.setDoOutput(true);
			httpConn.setRequestMethod("POST");
			httpConn.setRequestProperty("SOAPAction", "");

			byte[] authEncBytes = Base64.getEncoder().encode(FranceDiseAuthentication.getBytes());
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

	public static Map<String, String> getResponseValueFromDise(String request) {

		HttpURLConnection httpConn = null;
		String responseString = "";
		String outputString = "";
		BufferedReader br = null;
		// String nodeResult = "";
		Document document = null;
		// String request = requestToDiseToGetAccountDetails(diseAccountNumber);
		String url = Constant.DISE_REQUEST;
		httpConn = httpPostTest(url, request);

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
		// Parse the String output to a org.w3c.dom.Document and be able to
		// reach every node with the org.w3c.dom API.

		try {
			// create a dictionary that can accommodate all tags and values
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();

			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(outputString));

			document = db.parse(is);

			// NodeList nodeLst = document.getElementsByTagName(nodeValue);
			// NodeList nodeLst = document.getChildNodes();

			// nodeResult = nodeLst.item(0).getTextContent();
			// System.out.println(String.format("%s: %s", nodeValue,
			// nodeResult));

		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		} finally {
			try {
				httpConn.disconnect();
				System.out.println("cunnection is successfully closed");
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new Error("No connections are closed");
			}
		}
		return createMap(document.getDocumentElement());
	}

	public static Map<String, String> createMap(Node node) {
		Map<String, String> map = new HashMap<String, String>();
		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node currentNode = nodeList.item(i);
			NodeList childNodes = currentNode.getChildNodes();
			// if (currentNode.hasAttributes()) {
			for (int j = 0; j < childNodes.getLength(); j++) {
				Node grantChild = childNodes.item(j);
				NodeList grantChildNodes = grantChild.getChildNodes();
				for (int k = 0; k < grantChildNodes.getLength(); k++) {
					grantChildNodes.item(k).getTextContent();
					map.put(childNodes.item(i).getNodeName(), grantChildNodes.item(k).getTextContent());
					System.out.println(map);
				}
			}
		}
		return map;
	}

	public static String requestToDiseToGetAccountDetails(String diseAccountNumber) {
		return "<?xml version=\"1.0\" encoding=\"utf-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
				+ "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soapenv:Body>"
				+ "<ns2:QueryAccount xmlns:ns2=\"http://mdsuk.com/ws/dise3g/account/definition\">"
				+ "<ns2:Request><AccountNumber>" + diseAccountNumber
				+ "</AccountNumber><Datasets><Dataset>PAYMENT_DETAILS</Dataset>"
				+ "</Datasets></ns2:Request></ns2:QueryAccount></soapenv:Body></soapenv:Envelope>";
	}

	public static String requestToDiseToUpdatePaymentMethodForCreditCard(String externalAccountId, String diseAccountNumber) {
		return "<?xml version=\"1.0\" encoding=\"utf-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
				+ "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soapenv:Body>"
				+ "<ns2:UpdateRecurringPaymentDetails xmlns:ns2=\"http://mdsuk.com/ws/dise3g/account/definition\">"
				+ "<ns2:Request><ExternalReference>ACC-ID:"+externalAccountId+"</ExternalReference>" + "<AccountNumber>"
				+ diseAccountNumber + "</AccountNumber>"
				+ "<PaymentType>VISA</PaymentType><PaymentDetails><PaymentCardDetails>"
				+ "<CancellationInformation><ReasonCode>APCC</ReasonCode><CancellationDate>2016-05-25</CancellationDate>"
				+ "</CancellationInformation>" + "</PaymentCardDetails></PaymentDetails>"
				+ "<LastAmendedDate>1899-01-01T00:00:00.000Z</LastAmendedDate></ns2:Request>"
				+ "</ns2:UpdateRecurringPaymentDetails>";
	}
}