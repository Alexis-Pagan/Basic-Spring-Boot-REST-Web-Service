package training.dev.models;

import io.swagger.annotations.ApiModel;

@ApiModel(value="Model class for Email value", description="Email sent by user will be save in this model")
public class User {
	
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
