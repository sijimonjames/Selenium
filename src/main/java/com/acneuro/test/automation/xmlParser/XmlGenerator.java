/**
 * 
 */
package com.acneuro.test.automation.xmlParser;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * @author sijames
 *
 */
public class XmlGenerator {

	private static String[] listOfArguments(String[]... args) {
		String[] currentList = args[0];

		for (String[] list : args) {
			currentList = list;
		}
		return currentList;

	}

	private static String xmlCreator(String[] parameters) {

		String xmlString = null;
		String[] args = listOfArguments(parameters);
		OrderOfferXml xmlCreation = new OrderOfferXml();

		xmlCreation.setProductId(args[0]);

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(OrderOfferXml.class);
			Marshaller jaxbMarshellar = jaxbContext.createMarshaller();

			jaxbMarshellar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			StringWriter sw = new StringWriter();
			jaxbMarshellar.marshal(xmlCreation, sw);
			xmlString = sw.toString();

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return xmlString;

	}

	public static void createXML_test() {

		String xmlString = xmlCreator(new String[]{ "sijimon", "30", "5555"});
		System.out.println(xmlString);
	}
	
}