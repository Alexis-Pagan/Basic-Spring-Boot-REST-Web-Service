package training.dev.error.handling;

import javax.servlet.http.HttpServletRequest;


import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/*
 * This is a Global Exception Handler class 
 * 1] handles when user enters a URI that has not mapped to a handler-method
 */
@RestControllerAdvice
public class GlobalGenericErrorClass {
	
	/*
	 * cross-cutting concern Exception used Globally to allow in every method on RestController class 
	 * to have Exception globally adapted
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<Error> error(NoHandlerFoundException ex, HttpServletRequest servletEx) {
		
		/*
		 * HttpServletRequest allows use to access to servlet container and acquire the URL sent by client 
		 * 1] here we have a message to display to user when no handler method can managed the requested resource
		 * 2] response is sent with status code and message using @ResponseEntity
		 */
		String message = "Sorry, no handler method found for: " + servletEx.getRequestURL();
		
		Error responseError = new Error(message);
		
		return ResponseEntity.status(404).body(responseError);
		
	}
	
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<Error> error(HttpMediaTypeNotSupportedException ex) {
		/*
		 * this handler method exception will issue message of 415 
		 * 1] when no media type is supported by the service "Unsupported media type" is returned
		 */
		String message = "Sorry, this service only support Media Type JSON.";
		
		Error responseError = new Error(message);
		
		return ResponseEntity.status(415).body(responseError);
	}
}
