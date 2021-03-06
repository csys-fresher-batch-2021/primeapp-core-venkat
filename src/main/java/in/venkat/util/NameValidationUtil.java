package in.venkat.util;

import in.venkat.exceptions.EmptyFieldException;
import in.venkat.exceptions.InvalidNameException;

public class NameValidationUtil {
	private NameValidationUtil() {
		/**
		 * Adding private constructor
		 */
	}

	/**
	 * This method validates the given name
	 * 
	 * @param Name
	 * @return
	 * @throws EmptyFieldException
	 * @throws InvalidNameException
	 */
	public static boolean validateName(String name) throws EmptyFieldException, InvalidNameException {
		boolean isValid = false;
		if (!name.trim().isEmpty()) {
			isValid = checkName(name);
		} else {
			throw new EmptyFieldException("empty field");
		}

		return isValid;
	}

	public static boolean checkName(String name) throws InvalidNameException {
		boolean valid = false;
		String regex = "[a-zA-Z ]+\\.?";
		if (name.matches(regex) && name.length() <= 20) {
			valid = true;
		} else {
			throw new InvalidNameException("Invalid name");
		}
		return valid;

	}

}
