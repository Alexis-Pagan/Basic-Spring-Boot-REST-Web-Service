package training.dev.error.handling;

public class RESTApiError {
	
	private String message;
	
	public RESTApiError(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
