package com.carledwinti.soap.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class MarshallConfig {

	@Bean
	public Jaxb2Marshaller marshaller() {
		
		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		jaxb2Marshaller.setContextPath("com.carledwinti.notes");
		return jaxb2Marshaller;
	}
	
	@Bean
	public SoapConnector soapConnector(Jaxb2Marshaller jaxb2Marshaller) {
		
		SoapConnector soapConnector = new SoapConnector();
		soapConnector.setDefaultUri("http://localhost:8080/ws/notesWS");
		soapConnector.setMarshaller(jaxb2Marshaller);
		soapConnector.setUnmarshaller(jaxb2Marshaller);
		return soapConnector;
	}
	
}
