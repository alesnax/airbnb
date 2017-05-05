package com.epam.apartment.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.epam.apartment.service.ApartmentService;

@Controller
@RequestMapping(value = "/apartment")
public class ApartmentController {

	@Autowired
	private ApartmentService apartmentService;

}
