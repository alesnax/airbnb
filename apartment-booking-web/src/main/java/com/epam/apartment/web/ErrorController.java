package com.epam.apartment.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

	@RequestMapping(value = "/error404")
	public String error404Handle() {
		return "error/error404";
	}

	@RequestMapping(value = "/error")
	public String errorHandle() {
		return "error/error";
	}

}
