/*
 * REST Training v1.0
 * @author Alexis Garcia 
 * 
 * DATE: 1/6/2019
 * 
 * THE FOLLOWING PROGRAM IS DISTRIBUTED FOR 
 * EDUCATIONAL PURPOSES. REST SERVICE WITH POST
 * AND GET VERBS FOR CREATION AND READING OF 
 * EMAIL ADDRESSES.
 * 
 * º
 */

package training.dev.rest.controllers;

import java.util.ArrayList;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import training.dev.models.Response;
import training.dev.models.UserInformation;

/*
 * RestController annotation provides Controller and ResponseBody annotation 
 * in just one single annotation
 */
@RestController public class EmailController {

	private Response label = new Response();
	private ArrayList<String> dblocale = new ArrayList<String>();

	/**
	 * return all e-mails address available
	 */
	@GetMapping(path="/users/emails-addresses/emailController") 
	public ResponseEntity<ArrayList<String>> readEmailAddress() {
		label.setMessage("All resource available");
		return new ResponseEntity<ArrayList<String>>(dblocale, HttpStatus.OK);
	}

	/**
	 * insert new e-mail addresses
	 */
	@PostMapping(path="/users/emails-addresses/emailController", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE}) 
	public ResponseEntity<Response> createEmailAddress(@RequestBody UserInformation information) {

		/*
		 * check for null or empty value of e-mail address
		 * no try and catch in this Basic
		 */
		if(information.getEmail().equals(null) || information.getEmail().isEmpty()) {
			label.setMessage("Request Body is empty");
			return new ResponseEntity<Response>(label, HttpStatus.OK);
		}

		boolean existByEmail = dblocale.contains(information.getEmail());

		/*
		 * if e-mail address exist in database
		 */
		if(existByEmail) {
			label.setMessage("The email " + information.getEmail() + " exist");
			return new ResponseEntity<Response>(label, HttpStatus.CONFLICT);
		}

		/*
		 * if e-mail address record does not exist added to database
		 */
		dblocale.add(information.getEmail());
		
		HttpHeaders header = new HttpHeaders();
		header.set(HttpHeaders.LOCATION, "/users/emails-addresses/emailController");
		
		label.setMessage("User information with " + information.getEmail() + " created");
		return new ResponseEntity<Response>(label, header, HttpStatus.CREATED);
	}
}
