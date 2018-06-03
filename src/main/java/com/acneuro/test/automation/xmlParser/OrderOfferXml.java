package com.acneuro.test.automation.xmlParser;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author sijames
 * @Library: xml parser
 */

/*
 *
 * <OrderLine> <ProductId>1075</ProductId> <ParentProductId>1</ParentProductId>
 * <ParentProductLocation>ORDER_ENTRY</ParentProductLocation>
 * <ProductCode>FRMV08AODB80</ProductCode> <ProductType>DATABUNDLE</ProductType>
 * <ProductQuantity>0</ProductQuantity> <PriceOneOff>0</PriceOneOff>
 * <PriceMrc>0</PriceMrc> <Currency>EUR</Currency> <VatOneOff>0.00</VatOneOff>
 * <VatMrc>0.00</VatMrc> <OrderLineNumber>3</OrderLineNumber> </OrderLine>
 * </OrderOfferProduct>
 */

@XmlRootElement
public class OrderOfferXml {

	String ProductId;

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return ProductId;
	}

	/**
	 * @param productId
	 *            the productId to set
	 */
	public void setProductId(String productId) {
		ProductId = productId;
	}

}
