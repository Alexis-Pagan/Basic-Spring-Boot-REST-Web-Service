package training.dev.models;

import io.swagger.annotations.ApiModel;

@ApiModel(value="Response Model", description="This model will save message configured to be sent to describe Exceptions")
public class Response {
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
