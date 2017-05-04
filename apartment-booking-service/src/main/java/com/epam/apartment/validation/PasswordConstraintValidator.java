package com.epam.apartment.validation;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.passay.DigitCharacterRule;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.SpecialCharacterRule;
import org.passay.UppercaseCharacterRule;
import org.passay.WhitespaceRule;

import com.google.common.base.Joiner;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

	@Override
	public void initialize(final ValidPassword arg0) {

	}

	@Override
	public boolean isValid(final String password, final ConstraintValidatorContext context) {
		/*
		 * Properties props = new Properties(); try { props.load(new
		 * FileInputStream("classpath:/i18n/messages.properties")); } catch
		 * (IOException e) { // TODO Auto-generated catch block // logging
		 * e.printStackTrace(); } MessageResolver resolver = new
		 * PropertiesMessageResolver(props);
		 */
		final PasswordValidator validator = new PasswordValidator(/* resolver, */
				Arrays.asList(new LengthRule(8, 30), new UppercaseCharacterRule(1), new DigitCharacterRule(1), new SpecialCharacterRule(1), new WhitespaceRule()));
		final RuleResult result = validator.validate(new PasswordData(password));
		if (result.isValid()) {
			return true;
		}
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(Joiner.on("\n").join(validator.getMessages(result))).addConstraintViolation();
		return false;
	}

}