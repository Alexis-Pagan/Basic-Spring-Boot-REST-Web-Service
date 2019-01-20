
package training.dev.error.handling;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders; import
org.springframework.http.HttpStatus; import
org.springframework.http.MediaType; import
org.springframework.http.ResponseEntity; import
org.springframework.web.HttpMediaTypeNotSupportedException; import
org.springframework.web.bind.annotation.ControllerAdvice; import
org.springframework.web.bind.annotation.ExceptionHandler; import
org.springframework.web.bind.annotation.ResponseBody; import
org.springframework.web.servlet.NoHandlerFoundException;


/*
 * This is a Global Exception Handler class 1] handles when user enters a URI
 * that has not mapped to a handler-method

 */

@ControllerAdvice public class GlobalExceptionHandler { // extends]	
	
	private HttpHeaders httpHeader = new HttpHeaders();

	/*
	 * cross-cutting concern Exception used Globally to allow in every method on
	 * 	RestController class to have Exception globally adapted

	 */

	@ExceptionHandler(NoHandlerFoundException.class)

	@ResponseBody public ResponseEntity<Error> error(NoHandlerFoundException ex,
			HttpServletRequest servletEx) {

		System.err.println("No handler-method found exception");

		String message = "Sorry, no handler method found for: " +
				servletEx.getRequestURL();

		Error responseError = new Error(message);

		httpHeader.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeader).body(
				responseError);

	}

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)

	@ResponseBody public ResponseEntity<Error>
	error(HttpMediaTypeNotSupportedException ex) {

		/*
		 * this handler method exception will issue message of 415 1] when no media type
		 * is supported by the service "Unsupported media type" is returned
		 */


System.err.println("No MediaType Support found exception");

		String message = "Sorry, this service only support Media Type JSON.";

		Error responseError = new Error(message);

		httpHeader.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

		return ResponseEntity.status(415).headers(httpHeader).body(responseError); }
}
