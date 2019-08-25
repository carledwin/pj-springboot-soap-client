package com.carledwinti.soap.client;

import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.TransformerException;

import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;

import com.carledwinti.notes.Authorization;
import com.carledwinti.notes.ObjectFactory;

public class SoapConnector extends WebServiceGatewaySupport{

	public Object callWebService(String uri, Object request) {
		
		return getWebServiceTemplate().marshalSendAndReceive(uri, request, new WebServiceMessageCallback() {
			
			@Override
			public void doWithMessage(WebServiceMessage webServiceMessage) throws IOException, TransformerException {
				
				try {
					
					SoapHeader soapHeader = ((SoapMessage) webServiceMessage).getSoapHeader();
					
					ObjectFactory factory = new ObjectFactory();
					
					Authorization authorization = factory.createAuthorization();
					
					authorization.setUsername("testuser");
					
					authorization.setPassword("testpass");
					
					JAXBContext context = JAXBContext.newInstance(Authorization.class);
					
					Marshaller marshaller = context.createMarshaller();
					marshaller.marshal(authorization, soapHeader.getResult());
				} catch (Exception e) {
					logger.error("Error during marshalling of the SOAP headers", e);
 
				}
			}
		});
	}
}
