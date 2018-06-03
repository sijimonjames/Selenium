package com.acneuro.test.automation.xmlParser;

import java.util.Date;

import com.acneuro.test.automation.libraries.ConfigLib;
import com.acneuro.test.automation.mvno_project_library.MVNOProjectSpecific;

public class Xml_Parser_for_Legacy_Talkplans extends ConfigLib {

	public static String france_new_mvno_customer_creation(String emailId) {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + "<CustomerRequest><Customer>"
				+ "<Name>ACNEuro France Automation</Name>"
				+ "<ContactPerson xsi:type=\"ContactPersonVO\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
				+ "<FirstName>" + username + "</FirstName>"
				+ "<MiddleName>MVNO</MiddleName><LastName>France En</LastName>"
				+ "<PreferredLanguage>EN</PreferredLanguage>" + "<Title>Mrs.</Title>"
				+ "<DateOfBirth>01011991</DateOfBirth></ContactPerson>" + "<Type>RESIDENTIAL</Type>"
				+ "<Address xsi:type=\"AddressVO\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
				+ "<Attributes/><Country>FR</Country><City>PARIS</City>" + "<PostalCode>75007</PostalCode>"
				+ "<HouseNumber>38</HouseNumber>" + "<Street>RUE DE BABYLONE</Street>" + "</Address>" + "<Email>"
				+ emailId + "</Email>" + "<PhoneNumber>0123467890</PhoneNumber><RepId>0</RepId>"
				+ "<Country>FR</Country>" + "<MainIdentification type=\"NID\">12345678910</MainIdentification>"
				+ "<Parameters/><Attributes>"
				+ "<Attribute name=\"creditProfile\">VALID</Attribute></Attributes><Identity>"
				+ "<IdentityImage>0M8R4KGxGuEAAAAAAAAAAAAAAAAAAAAAPgADAP7/CQAG"
				+ "AAAAAAAAAAAAAAABAAAAUQAAAAAAAAAAEAAAUwAAAAEAAAD+/</IdentityImage>"
				+ "<IdentityImageType>application/msword</IdentityImageType>" + "<Valid>false</Valid>" + "</Identity>"
				+ "<MarketingConsent>false</MarketingConsent><AddressVerified>true</AddressVerified>" + "</Customer>"
				+ "<User>" + "<Username>" + emailId + "</Username>"
				+ "<Password>ecd71870d1963316a97e3ac3408c9835ad8cf0f3c1bc703527c30265534f75ae</Password>"
				+ "<SecretQuestion>asdf?</SecretQuestion>"
				+ "<SecretQuestionAnswer>asdf!</SecretQuestionAnswer></User></CustomerRequest>";
	}

	public static String uk_new_mvno_customer_creation(String emailId) {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + "<CustomerRequest><Customer>"
				+ "<Name>" + username + "</Name>"
				+ "<ContactPerson xsi:type=\"ContactPersonVO\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
				+ "<FirstName>kmanisha</FirstName><MiddleName>MVNO</MiddleName><LastName>Automation</LastName>"
				+ "<PreferredLanguage>EN</PreferredLanguage><Title>Mr.</Title><DateOfBirth>31121976</DateOfBirth>"
				+ "</ContactPerson><Type>RESIDENTIAL</Type>"
				+ "<Address xsi:type=\"AddressVO\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
				+ "<Attributes/><Country>GB</Country><City>London</City><PostalCode>SW6 5DP</PostalCode>"
				+ "<HouseNumber>21</HouseNumber><Street>Lilyville Road</Street></Address><Email>" + emailId + "</Email>"
				+ "<PhoneNumber>03900000001</PhoneNumber><RepId>0</RepId><Country>GB</Country>"
				+ "<MainIdentification type=\"NID\">12345678910</MainIdentification>" + "<Parameters/><Attributes>"
				+ "<Attribute name=\"creditProfile\">VALID</Attribute>"
				+ "</Attributes><MarketingConsent>false</MarketingConsent><AddressVerified>true</AddressVerified>"
				+ "</Customer><User><Username>" + emailId + "</Username>"
				+ "<Password>ecd71870d1963316a97e3ac3408c9835ad8cf0f3c1bc703527c30265534f75ae</Password>"
				+ "<SecretQuestion>asdf?</SecretQuestion>" + "<SecretQuestionAnswer>asdf!</SecretQuestionAnswer>"
				+ "</User></CustomerRequest>";
	}

	public static String UK_Customer_Create_Order() {

		return "";

	}

	public static String shippingAddressXml() {
		return "<ShippingAddress><Attributes/><Country>FR</Country>"
				+ "<City>PARIS</City><PostalCode>75005</PostalCode><HouseNumber>7</HouseNumber><Street>RUE DE LA HUCHETTE</Street></ShippingAddress>";
	}

	public static String billingAddressXml() {
		return "<InvoiceAddress><Attributes/><Country>FR</Country>"
				+ "<City>PARIS</City><PostalCode>75005</PostalCode><HouseNumber>7</HouseNumber><Street>RUE DE LA HUCHETTE</Street></InvoiceAddress>";
	}

	public static String pricePlanPriceXml() {
		return "<TotalPriceMrc>8.99</TotalPriceMrc><TotalPriceOneOff>8.99</TotalPriceOneOff><Currency>EUR</Currency>"
				+ "<Country>FR</Country><TotalVatOneOff>1.80</TotalVatOneOff>" + "<TotalVatMrc>1.80</TotalVatMrc>";
	}

	public static String mbSubscriptionProductxml() {
		return "<OrderOfferProduct><OfferProductId>1341</OfferProductId>"
				+ "<OrderedQuantity>0</OrderedQuantity><Promotion/><OrderLine><Parameters>"
				+ "<Parameter name=\"msisdn\"></Parameter></Parameters><ProductId>1073</ProductId>"
				+ "<ProductCode>FRMV08MainMOB78</ProductCode><ProductType>MBSUBSCRIPTION</ProductType>"
				+ "<ProductQuantity>0</ProductQuantity><PriceOneOff>8.99</PriceOneOff><PriceMrc>8.99</PriceMrc>"
				+ "<Currency>EUR</Currency><VatOneOff>1.80</VatOneOff><VatMrc>1.80</VatMrc>"
				+ "<OrderLineNumber>1</OrderLineNumber></OrderLine></OrderOfferProduct>";
	}

	public static String OrderOfferProductXml(int OfferProductId, int OrderProductId, int ProductId, String ProductCode,
			String ProductType, String Currency, int OrderLineNumber) {
		return "<OrderOfferProduct><OfferProductId>" + OfferProductId
				+ "</OfferProductId><OrderedQuantity>0</OrderedQuantity>" + "<Promotion/><OrderLine><OrderProductId>"
				+ OrderProductId + "</OrderProductId>" + "<OrderProductStatus>PENDING</OrderProductStatus><ProductId>"
				+ ProductId + "</ProductId><ParentProductId>1</ParentProductId>"
				+ "<ParentProductLocation>ORDER_ENTRY</ParentProductLocation><ProductCode>" + ProductCode
				+ "</ProductCode>" + "<ProductType>" + ProductType
				+ "</ProductType><ProductQuantity>0</ProductQuantity>"
				+ "<PriceOneOff>0</PriceOneOff><PriceMrc>0</PriceMrc><Currency>" + Currency + "</Currency>"
				+ "<VatOneOff>0.00</VatOneOff><VatMrc>0.00</VatMrc><OrderLineNumber>" + OrderLineNumber
				+ "</OrderLineNumber></OrderOfferProduct>";
	}

	public static String FR_Customer_Create_Order_updated(String customerId, String pricePlan) {

		String date = MVNOProjectSpecific.dateToString(new Date(), "yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		int offerId = MVNOProjectSpecific.FR_GetOfferIdFromPricePlanName(pricePlan);

		return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><OrderEntryRequest>"
				+ "<OrderEntry><Order><CreateTimestamp>" + date + "</CreateTimestamp>"
				+ "<TeamId>999999</TeamId><CustomerId>" + customerId + "</CustomerId> " + shippingAddressXml()
				+ billingAddressXml() + "" + pricePlanPriceXml()
				+ "<OrderLines><OrderLine><Parameters><Parameter name=\"msisdn\">0</Parameter>"
				+ "<Parameter name=\"accountId\">0</Parameter><Parameter name=\"provider\">0</Parameter>"
				+ "<Parameter name=\"portingDate\">01-01-1970</Parameter></Parameters><ProductId>369</ProductId>"
				+ "<ParentProductId>1</ParentProductId><ParentProductLocation>ORDER_ENTRY</ParentProductLocation>"
				+ "<ProductCode>NUMBERPORTINGFR</ProductCode>"
				+ "<ProductType>NUMBERPORTING</ProductType><ProductQuantity>1</ProductQuantity>"
				+ "<PriceOneOff>0</PriceOneOff><PriceMrc>0</PriceMrc><Currency>EUR</Currency><VatOneOff>0.00</VatOneOff>"
				+ "<VatMrc>0.00</VatMrc>" + "<OrderLineNumber>8</OrderLineNumber></OrderLine><OrderOffer><OfferId>"
				+ offerId + "</OfferId><OrderOfferProducts>"
				+ "<OrderOfferProduct><OfferProductId>1341</OfferProductId><OrderedQuantity>0</OrderedQuantity>"
				+ "<Promotion/><OrderLine><Parameters><Parameter name=\"msisdn\"></Parameter></Parameters>"
				+ "<ProductId>1073</ProductId><ProductCode>FRMV08MainMOB78</ProductCode><ProductType>MBSUBSCRIPTION</ProductType>"
				+ "<ProductQuantity>0</ProductQuantity><PriceOneOff>8.99</PriceOneOff><PriceMrc>8.99</PriceMrc><Currency>EUR</Currency>"
				+ "<VatOneOff>1.80</VatOneOff><VatMrc>1.80</VatMrc><OrderLineNumber>1</OrderLineNumber></OrderLine>"
				+ "</OrderOfferProduct><OrderOfferProduct>"
				+ "<OfferProductId>1345</OfferProductId><OrderedQuantity>0</OrderedQuantity><Promotion/><OrderLine>"
				+ "<ProductId>368</ProductId>"
				+ "<ParentProductId>1</ParentProductId><ParentProductLocation>ORDER_ENTRY</ParentProductLocation>"
				+ "<ProductCode>FRMV1407AOSIM20</ProductCode><ProductType>SIMCARD</ProductType>"
				+ "<ProductQuantity>0</ProductQuantity><PriceOneOff>0</PriceOneOff>"
				+ "<PriceMrc>0</PriceMrc><Currency>EUR</Currency><VatOneOff>0.00</VatOneOff><VatMrc>0.00</VatMrc>"
				+ "<OrderLineNumber>2</OrderLineNumber></OrderLine></OrderOfferProduct><OrderOfferProduct>"
				+ "<OfferProductId>1342</OfferProductId><OrderedQuantity>0</OrderedQuantity>"
				+ "<Promotion/><OrderLine><ProductId>1075</ProductId><ParentProductId>1</ParentProductId>"
				+ "<ParentProductLocation>ORDER_ENTRY</ParentProductLocation><ProductCode>FRMV08AODB80</ProductCode>"
				+ "<ProductType>DATABUNDLE</ProductType><ProductQuantity>0</ProductQuantity><PriceOneOff>0</PriceOneOff>"
				+ "<PriceMrc>0</PriceMrc><Currency>EUR</Currency><VatOneOff>0.00</VatOneOff><VatMrc>0.00</VatMrc>"
				+ "<OrderLineNumber>3</OrderLineNumber></OrderLine></OrderOfferProduct><OrderOfferProduct>"
				+ "<OfferProductId>1344</OfferProductId><OrderedQuantity>0</OrderedQuantity><Promotion/><OrderLine>"
				+ "<ProductId>385</ProductId><ParentProductId>1</ParentProductId>"
				+ "<ParentProductLocation>ORDER_ENTRY</ParentProductLocation>"
				+ "<ProductCode>FRMV1407AOSMS18</ProductCode><ProductType>SMSBUNDLE</ProductType>"
				+ "<ProductQuantity>0</ProductQuantity><PriceOneOff>0</PriceOneOff>"
				+ "<PriceMrc>0</PriceMrc><Currency>EUR</Currency><VatOneOff>0.00</VatOneOff><VatMrc>0.00</VatMrc>"
				+ "<OrderLineNumber>4</OrderLineNumber></OrderLine></OrderOfferProduct><OrderOfferProduct>"
				+ "<OfferProductId>1343</OfferProductId>"
				+ "<OrderedQuantity>0</OrderedQuantity><Promotion/><OrderLine><ProductId>1074</ProductId>"
				+ "<ParentProductId>1</ParentProductId><ParentProductLocation>ORDER_ENTRY</ParentProductLocation>"
				+ "<ProductCode>FRMV08AOVB79</ProductCode><ProductType>VOICEBUNDLE</ProductType><ProductQuantity>0</ProductQuantity>"
				+ "<PriceOneOff>0</PriceOneOff><PriceMrc>0</PriceMrc><Currency>EUR</Currency><VatOneOff>0.00</VatOneOff>"
				+ "<VatMrc>0.00</VatMrc><OrderLineNumber>5</OrderLineNumber></OrderLine></OrderOfferProduct><OrderOfferProduct><OfferProductId>1347</OfferProductId>"
				+ "<OrderedQuantity>0</OrderedQuantity>"
				+ "<Promotion/><OrderLine><ProductId>261</ProductId><ParentProductId>1</ParentProductId>"
				+ "<ParentProductLocation>ORDER_ENTRY</ParentProductLocation><ProductCode>FRMV1407SYSCF23</ProductCode>"
				+ "<ProductType>MBCONNECTION</ProductType>"
				+ "<ProductQuantity>0</ProductQuantity><PriceOneOff>0</PriceOneOff><PriceMrc>0</PriceMrc>"
				+ "<Currency>EUR</Currency><VatOneOff>0.00</VatOneOff><VatMrc>0.00</VatMrc>"
				+ "<OrderLineNumber>6</OrderLineNumber>" + "</OrderLine></OrderOfferProduct><OrderOfferProduct>"
				+ "<OfferProductId>1348</OfferProductId><OrderedQuantity>0</OrderedQuantity>"
				+ "<Promotion/><OrderLine><ProductId>1</ProductId><ParentProductId>1</ParentProductId>"
				+ "<ParentProductLocation>ORDER_ENTRY</ParentProductLocation><ProductCode>FRMV1407SYSSH21</ProductCode>"
				+ "<ProductType>SHIPHANDLE</ProductType>"
				+ "<ProductQuantity>0</ProductQuantity><PriceOneOff>0</PriceOneOff><PriceMrc>0</PriceMrc>"
				+ "<Currency>EUR</Currency><VatOneOff>0.00</VatOneOff><VatMrc>0.00</VatMrc>"
				+ "<OrderLineNumber>7</OrderLineNumber></OrderLine></OrderOfferProduct></OrderOfferProducts>"
				+ "</OrderOffer></OrderLines><Parameters><Parameter name=\"realUser\">   </Parameter>"
				+ "</Parameters><Terms><Terms><TermId>23</TermId><Type>LGTRMAGR</Type><TermCode>LGTRMAGRFR</TermCode>"
				+ "<Description>&lt;p style=\"text-align: justify;\"&gt;By submitting this order, "
				+ "I am making a binding offer to ACN Communications France (\"ACN\") to purchase the selected services. "
				+ "I understand that ACN may reject my order for any reason and that my order is not complete until I receive a confirmation email from ACN. "
				+ "I have read and agree to the &lt;a target=\"_blank\" href=\"http://www.joitelecom.fr/en/common/terms-and-conditions\" "
				+ "class=\"opennew\"&gt;Terms and Conditions&lt;/a&gt;  and the  &lt;a target=\"_blank\" "
				+ "href=\"http://www.joitelecom.fr/en/common/legal-information#goto-tab-PRICEGUIDES\" class=\"opennew\"&gt;Price Guide&lt;/a&gt;  "
				+ "for the service that I have ordered. I consent to the collection and processing of my personal data and the transfer my personal data to third parties as described in the &lt;a "
				+ "target=\"_blank\" href=\"http://www.joitelecom.fr/en/common/privacy-policy\" "
				+ "class=\"opennew\"&gt;Privacy Policy&lt;/a&gt;.&lt;/p&gt;</Description><Language>EN</Language></Terms>"
				+ "<Terms><TermId>21</TermId><Type>LGTRMINV</Type><TermCode>LGTRMINV</TermCode>"
				+ "<Description>I agree to accept electronic invoicing.</Description>"
				+ "<Language>EN</Language></Terms><Terms><TermId>22</TermId><Type>LGTRMRVC</Type>"
				+ "<TermCode>LGTRMRVCFR</TermCode><Description>&lt;p style=\"text-align: justify;\"&gt;"
				+ "I request ACN to provision the selected services before the end of the Delai de Rétractation as described "
				+ "in the &lt;a target=\"_blank\" href=\"http://www.joitelecom.fr/en/common/terms-and-conditions\" "
				+ "class=\"opennew\"&gt;Terms and Conditions&lt;/a&gt;. "
				+ "I understand and agree that if I retract this Agreement after my services are provisioned, "
				+ "I will have to pay for the services provided to me prior to the date that I retract."
				+ "&lt;/p&gt;</Description><Language>EN</Language></Terms></Terms><OrderLogs><OrderLog>"
				+ "<OrderLogType>AGREEMENT</OrderLogType><Log>22</Log><Key>term_LGTRMRVCFR</Key></OrderLog><OrderLog>"
				+ "<OrderLogType>AGREEMENT</OrderLogType><Log>23</Log><Key>term_LGTRMAGRFR</Key></OrderLog>"
				+ "<OrderLog><OrderLogType>AGREEMENT</OrderLogType><Log>21</Log><Key>term_LGTRMINV</Key>"
				+ "</OrderLog></OrderLogs><RecurringPaymentDetails><DirectDebitDetails>"
				+ "<AccountNumber>FR1420041010050500013M02606</AccountNumber><Bank>ABNAFRPPXXX</Bank>"
				+ "<AccountHolderName>sijames Fr Automation</AccountHolderName>"
				+ "<BranchCode>ABNAFRPPXXX</BranchCode></DirectDebitDetails></RecurringPaymentDetails><OrderTasks>"
				+ "<OrderTask><Code>CUS_ID_VAL_REQ</Code><Status>OPEN</Status></OrderTask></OrderTasks><PaymentPlan>"
				+ "<Installment sequence=\"0\" month=\"0\"><Total>8.99</Total>"
				+ "<TotalVat>1.80</TotalVat><TotalDiscount>0.00</TotalDiscount><TotalVatDiscount>0.00</TotalVatDiscount>"
				+ "<TotalWithDiscount>8.99</TotalWithDiscount><TotalVatWithDiscount>1.80</TotalVatWithDiscount></Installment>"
				+ "<Installment sequence=\"1\" month=\"1\"><Total>8.99</Total><TotalVat>1.80</TotalVat>"
				+ "<TotalDiscount>0.00</TotalDiscount><TotalVatDiscount>0.00</TotalVatDiscount>"
				+ "<TotalWithDiscount>8.99</TotalWithDiscount><TotalVatWithDiscount>1.80</TotalVatWithDiscount></Installment>"
				+ "</PaymentPlan></Order></OrderEntry></OrderEntryRequest>";

	}

}