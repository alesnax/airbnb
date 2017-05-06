package com.epam.apartment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Apartment Not Found")
public class ApartmentNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7988714559392884196L;

}
