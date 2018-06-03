package com.acneuro.test.automation.libraries;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import org.apache.commons.io.IOUtils;

public class SoapApiAutomation {
	private static final String FranceDiseAuthentication = "ACNWS090:WEBCONN";
	// private static final String UKDiseAuthentication = "ACNWS010:WEBCONN";

	public static Boolean httpPostTest(String Url, String request) {

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

			BufferedReader br = new BufferedReader(new InputStreamReader((httpConn.getInputStream())));
			
			String response = IOUtils.toString(br);
			
			System.out.println(response);

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {
			try {
				httpConn.disconnect();
				System.out.println("cunnection is successfully closed");
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new Error("No connections are closed");
			}
		}
		return false;
	}

	public static String requestToDiseToGetAccountDetails(String diseAccountNumber) {
		return "<?xml version=\"1.0\" encoding=\"utf-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
				+ "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soapenv:Body>"
				+ "<ns2:QueryAccount xmlns:ns2=\"http://mdsuk.com/ws/dise3g/account/definition\">"
				+ "<ns2:Request><AccountNumber>" + diseAccountNumber
				+ "</AccountNumber><Datasets><Dataset>PAYMENT_DETAILS</Dataset>"
				+ "</Datasets></ns2:Request></ns2:QueryAccount></soapenv:Body></soapenv:Envelope>";
	}

	public static String requestToDiseToCancelCreditCard(String processId, String taskId) {
		return "<CueRequest> <Parameters> <Parameter name=\"processId\">" + processId + "</Parameter> "
				+ "<Parameter name=\"command\">orderTaskCuesSignal</Parameter> <Parameter name=\"taskId\">" + taskId
				+ "</Parameter> <Parameter name=\"taskResult\">override</Parameter> "
				+ "<Parameter name=\"comments\">Updated Incomplete order task</Parameter> "
				+ "<Parameter name=\"resolvedBy\">asasidha</Parameter> </Parameters> </CueRequest>";
	}

}