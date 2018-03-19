package com.uniovi.validators;

import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.uniovi.entities.User;
import com.uniovi.services.UserService;

@Component
public class SignUpFormValidator implements Validator {

	private static final Logger logger = LoggerFactory.getLogger(SignUpFormValidator.class);

	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Error.empty");
		logger.debug("Error: campo email vacío");

		ValidationUtils.rejectIfEmpty(errors, "name", "Error.empty");
		logger.debug("Error: campo nombre vacío");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", "Error.empty");
		logger.debug("Error: campo confirmar contraseña vacío");

		ValidationUtils.rejectIfEmpty(errors, "password", "Error.empty");
		logger.debug("Error: campo contraseña vacío");

		if (!EmailValidator.getInstance().isValid(user.getEmail())) {
			errors.rejectValue("email", "Error.signup.email.format");
			logger.debug("Error: email inválido");
		}

		if (userService.getUserByEmail(user.getEmail()) != null) {
			errors.rejectValue("email", "Error.signup.email.duplicate");
			logger.debug("Error: email duplicado");
		}

		if (user.getPassword().length() < 5 || user.getPassword().length() > 24) {
			errors.rejectValue("password", "Error.signup.password.length");
			logger.debug("Error: longitud contraseña incorrecta");
		}

		if (!user.getPasswordConfirm().equals(user.getPassword())) {
			errors.rejectValue("passwordConfirm", "Error.signup.passwordConfirm.coincidence");
			logger.debug("Error: las contraseñas no coinciden");
		}
	}
}