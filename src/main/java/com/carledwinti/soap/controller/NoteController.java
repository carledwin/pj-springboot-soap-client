package com.carledwinti.soap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carledwinti.notes.Fault;
import com.carledwinti.notes.GetNotesRequest;
import com.carledwinti.notes.GetNotesResponse;
import com.carledwinti.notes.Note;
import com.carledwinti.notes.User;
import com.carledwinti.soap.client.SoapConnector;

@RestController
@RequestMapping("/notes")
public class NoteController {

	@Value("${uri.ws.notes}")
	private String uri;
	
	@Autowired
	private SoapConnector soapConnector;
	
	@GetMapping
	public String hello() {
		return "Hello!\n"; 
	}
	
	@PostMapping
	public GetNotesResponse validate(@RequestBody User user) {

		GetNotesResponse getNotesResponse = null;

		try {
			
			GetNotesRequest getNotesRequest = new GetNotesRequest();
			getNotesRequest.setUser(user);
			
			getNotesResponse = (GetNotesResponse) soapConnector.callWebService(uri, getNotesRequest);

			if (getNotesResponse != null) {

				if (getNotesResponse.getFault() != null) {

					System.out.println(getNotesResponse.getFault().getMessage());
				}

				if (getNotesResponse.getNote() != null) {

					Note note = getNotesResponse.getNote();

					if (note.getUser() != null) {
						User userResponse = note.getUser();

						System.out.println(userResponse.getDocument());
						System.out.println(userResponse.getDocumentType());
						System.out.println(userResponse.getName());
						System.out.println(userResponse.getAge());
					}
				}
			}

		} catch (Exception e) {
			
			getNotesResponse = new GetNotesResponse();
			Fault fault = new Fault();
			fault.setCode(500);
			fault.setMessage("Falha ao tentar consultar nota! Message: " + e.getMessage());
			
			getNotesResponse.setFault(fault );
		}
		
		return getNotesResponse;

	}
}
