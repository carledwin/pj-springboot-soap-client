package com.carledwinti.soap.client;

import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.TransformerException;

import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessage;

import com.carledwinti.notes.Authorization;
import com.carledwinti.notes.ObjectFactory;

public class SoapConnector extends WebServiceGatewaySupport{

	private static final String PASSWORD = "Password";
	private static final String USERNAME = "Username";
	private static final String USERNAME_TOKEN = "UsernameToken";
	private static final String HTTP_DOCS_OASIS_OPEN_ORG_WSS_2004_01 = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
	private static final String WSSE = "wsse";
	private static final String SECURITY = "Security";
	
	public Object callWebService(String uri, Object request) {
		
		return getWebServiceTemplate().marshalSendAndReceive(uri, request, new WebServiceMessageCallback() {
			
			@Override
			public void doWithMessage(WebServiceMessage webServiceMessage) throws IOException, TransformerException {
				
				try {
					
					SaajSoapMessage saajSoapMessage = (SaajSoapMessage) webServiceMessage;

					SOAPMessage soapMessage = saajSoapMessage.getSaajMessage();

					SOAPPart soapPart = soapMessage.getSOAPPart();

					SOAPEnvelope soapEnvelope = soapPart.getEnvelope();

					SOAPHeader soapHeader = soapEnvelope.getHeader();

					Name headerElementName = soapEnvelope.createName(SECURITY, WSSE, HTTP_DOCS_OASIS_OPEN_ORG_WSS_2004_01);
					
					SOAPHeaderElement soapHeaderElement = soapHeader.addHeaderElement(headerElementName);

					SOAPElement usernameTokenSOAPElement = soapHeaderElement.addChildElement(USERNAME_TOKEN, WSSE);
					SOAPElement userNameSOAPElement = usernameTokenSOAPElement.addChildElement(USERNAME, WSSE);
					SOAPElement passwordSOAPElement = usernameTokenSOAPElement.addChildElement(PASSWORD, WSSE);
					
					userNameSOAPElement.addTextNode("fulano");
					passwordSOAPElement.addTextNode("senha");

					soapMessage.saveChanges();
				} catch (Exception e) {
					logger.error("Error during marshalling of the SOAP headers", e);
 
				}
			}
		});
	}
}
