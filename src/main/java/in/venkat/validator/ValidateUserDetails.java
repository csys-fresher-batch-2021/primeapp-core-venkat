package in.venkat.validator;

import in.venkat.exceptions.EmptyFieldException;
import in.venkat.exceptions.InvalidNameException;
import in.venkat.exceptions.InvalidNumberException;
import in.venkat.exceptions.InvalidPasswordException;
import in.venkat.exceptions.PasswordMismatchException;
import in.venkat.util.MobileNumberValidationUtil;
import in.venkat.util.NameValidationUtil;
import in.venkat.util.PasswordValidationUtil;

public class ValidateUserDetails {
	private ValidateUserDetails() {
		/**
		 * adding private constructor
		 */
	}

	/**
	 * This method is used to validate the user registration details
	 * 
	 * @param name
	 * @param mobileNumber
	 * @param userPasscode
	 * @param reEnteredPasscode
	 * @return
	 * @throws EmptyFieldException
	 * @throws InvalidNameException
	 * @throws InvalidNumberException
	 * @throws InvalidPasswordException
	 * @throws PasswordMismatchException
	 */
	public static boolean validateUserDetails(String name, long mobileNumber, String userPasscode,
			String reEnteredPasscode) throws EmptyFieldException, InvalidNameException, InvalidNumberException,
			InvalidPasswordException, PasswordMismatchException {
		boolean isAllValid = false;
		boolean isEqual = isEqual(userPasscode, reEnteredPasscode);
		boolean isNameValid = NameValidationUtil.validateName(name);
		boolean isMobileNumValid = MobileNumberValidationUtil.validateMobileNumber(mobileNumber);
		boolean isUserPasscodeValid = PasswordValidationUtil.validatePassword(userPasscode);
		if (isEqual && isNameValid && isMobileNumValid && isUserPasscodeValid) {
			isAllValid = true;

		}
		return isAllValid;
	}

	/**
	 * This method is used to check whether password and re-entered password are
	 * same
	 * 
	 * @param userPasscode
	 * @param reEnteredPasscode
	 * @return
	 * @throws PasswordMismatchException
	 */
	private static boolean isEqual(String userPasscode, String reEnteredPasscode) throws PasswordMismatchException {
		boolean isEqual = false;
		if (userPasscode.trim().equals(reEnteredPasscode.trim())) {
			isEqual = true;

		} else {
			throw new PasswordMismatchException("the re-entered password is not matching");
		}
		return isEqual;

	}

}