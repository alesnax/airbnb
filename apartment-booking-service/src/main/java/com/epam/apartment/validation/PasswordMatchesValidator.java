package com.epam.apartment.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.epam.apartment.dto.ChangePasswordDto;
import com.epam.apartment.dto.UserDto;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

	@Override
	public void initialize(final PasswordMatches constraintAnnotation) {
		//
	}

	@Override
	public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
		if (obj instanceof UserDto) {
			final UserDto user = (UserDto) obj;
			return user.getPassword().equals(user.getMatchingPassword());
		} else if (obj instanceof ChangePasswordDto) {
			final ChangePasswordDto changePassword = (ChangePasswordDto) obj;
			return changePassword.getNewPassword().equals(changePassword.getMatchingPassword());
		} else {
			return false;
		}

	}

}