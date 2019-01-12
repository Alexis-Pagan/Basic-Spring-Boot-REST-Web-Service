package training.dev.error.handling;

public class RESTApiError {
	
	private String status; 
	private String message;
	
	public RESTApiError(String code, String message) {
		this.status = code;
		this.message = message;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
