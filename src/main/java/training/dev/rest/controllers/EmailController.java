/*
 * REST Training v1.0
 * 
 * @author Alexis Garcia - Service Architecture Software Developer at Evertec, Inc.
 * 
 * Date: 1/6/2019
 * 
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import training.dev.models.Response;
import training.dev.models.User;
import training.dev.valid.Validation;

/*
 * RestController annotation provides Controller plus ResponseBody annotation 
 * in just one single annotation. 
 * 	1] this allows programmer to not declare @ResponseBody over each method in EmailController
 * 	2] @RestController still uses Controller architecture like MVC to identify which handler method client is 
 * 		calling by submitting request call through URI each method has after path=
 * 	3] @ResponseBody allows to serializes Java Object properties to JSON request body
 */
/*
 * Content-Type tells HTTP Client what type of data is being sent as (Request media type of Body)
 * Accept HTTP Client sent what media type it prefers (respond prefer media type)
 */
@RestController
@Api(value="REST Endpoint Controller")
public class EmailController {

	/*
	 * Private fields (members of class) 
	 *	1] Response (class for setting message to return with status code)
	 *	2] ArrayList<String> a simple database we called MySQL
	 *	3] HttpHeader to create the headers for the responses (when needed)
	 */
	private Response report = new Response();
	private ArrayList<String> MySQL = new ArrayList<String>();
	private HttpHeaders httpHeader = new HttpHeaders();
	/*
	 * Return all e-mails addresses available from MySQL database: ArrayList<String>
	 */
	@GetMapping(path="/users/emails-addresses/emailController", 
			produces = {MediaType.APPLICATION_JSON_VALUE}, 
			consumes = {MediaType.APPLICATION_JSON_VALUE}) 
	@ApiOperation(value="HTTP Method Return All Emails in MySQL")
	public ResponseEntity<ArrayList<String>> readEmailAddress() {

		httpHeader.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		httpHeader.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

		/*
		 * Here ResponseEntity represent the HTTP protocol and we use ResponseEntity Builder Pattern Design
		 * This will return: 
		 * 		1] status: 200
		 * 		2] headers: ACCEPT & MediaType application/json
		 * 		3] database MyQSL with e-mails created
		 */
		return ResponseEntity.status(HttpStatus.OK).headers(httpHeader).body(MySQL);
	}

	/*
	 * Insert new e-mail addresses to MySQL local database
	 * 	1] produces: mediaType application/json
	 * 	2] consumes: mediaType application/json
	 * 	3] @RequestBody allows to deserializes JSON request body to Java Object properties
	 */
	@PostMapping(path="/users/emails-addresses/emailController", 
			produces = {MediaType.APPLICATION_JSON_VALUE}, 
			consumes = {MediaType.APPLICATION_JSON_VALUE}) 
	@ApiOperation(value="HTTP Method Create All Emails Addresses in MySQL")
	public ResponseEntity<Response> createEmailAddress(@RequestBody User user) {
		
		httpHeader.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		httpHeader.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		
		/*
		 * doValid is a method from Validation class
		 * 	1] will receive as parameter object User
		 * 	2] this object will be evaluated to true if and only if user email is not null or empty
		 * 	3] if user email is null or empty it will return "Request has not email sent"
		 * 
		 */
		boolean result = Validation.doValid(user);
		if(result) {
			report.setMessage("Request has no email address sent in body request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(httpHeader).body(report);
		}

		/*
		 * Call MySQL database methods contains and insert email in getEmail() method
		 * to verify if the email input by user in another post request is already recorded'
		 * in MySQL database
		 */
		boolean existByEmail = MySQL.contains(user.getEmail());

		/*
		 * If e-mail address exist in MySQL database
		 * If existByEmail is true then ResponseEntity returns: 
		 * 		1] status: conflict (which means, it is a duplication of data)
		 * 		2] message: a human readable message to say to client: hey, user exist! 
		 * 		3] this is a basic Exception Handling checkpoint
		 */
		if(existByEmail) {
			report.setMessage("Email address entered -> " + user.getEmail() + " <- live");
			return ResponseEntity.status(HttpStatus.CONFLICT).headers(httpHeader).body(report);
		}

		/*
		 * if e-mail address record does not exist in MySQL database add it
		 * 		1] status: created, because if existByEmail does evaluate to false 
		 * 			program will create (MySQL.add()) email to MySQL database 
		 * 		2] location header: is good practice to tell the client where it can find the email created 
		 * 			in this case the URI for the GetMapping method which retrieves all email records
		 */
		MySQL.add(user.getEmail());
		
		httpHeader.set(HttpHeaders.LOCATION, "/users/emails-addresses/emailController");
		
		report.setMessage("Email address -> " + user.getEmail() + " <- saved");
		return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeader).body(report);
	}
}
