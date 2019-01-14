/*
 * custom validation class that will validate if user email provided is null (no value) or empty (empty strings)
 */

package training.dev.valid;

import training.dev.models.User;

public class Validation {
	
	/*
	 * doValid receives:
	 * 	1] User object
	 * 	2] User is evaluated to null or empty
	 * 	3] null or empty will be interpreted as false
	 * 	4] not null or not empty will be interpreted as true
	 */
	public static boolean doValid(User user) {
		if(user.getEmail().equals(null) || user.getEmail().isEmpty()) {
			return true;
		}
		return false;
	}
}
