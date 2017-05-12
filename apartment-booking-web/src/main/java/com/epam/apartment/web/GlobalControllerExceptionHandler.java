package com.epam.apartment.web;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

	@ExceptionHandler({ SQLException.class, DataAccessException.class })
	@ResponseBody
	public String databaseError() {

		return "<h1>Sorry something was happened on server!</h1>";
	}

}
