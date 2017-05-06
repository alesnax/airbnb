package com.epam.apartment.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.epam.apartment.model.Apartment;
import com.epam.apartment.model.ApartmentCriteria;
import com.epam.apartment.service.ApartmentService;

@Controller
@RequestMapping(value = "/apartment")
public class ApartmentController {

	@Autowired
	private ApartmentService apartmentService;

	@InitBinder
	public final void initBinderUsuariosFormValidator(final WebDataBinder binder, final Locale locale) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value = "/search_by_name")
	public String searchApartmentByName(@RequestParam("apartment_name") String name, Model model) {
		List<Apartment> apartments = apartmentService.findConcreteApartments(name);

		model.addAttribute("apartments", apartments);
		model.addAttribute("page_name", "apartments by name");
		return "apartments";
	}

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String showFindApartments(Model model) {
		ApartmentCriteria criteria = new ApartmentCriteria();
		model.addAttribute("apartment_criteria", criteria);
		return "main";
	}

	@RequestMapping(value = "/show_result")
	public String processFindingApartments(@ModelAttribute("apartment_criteria") ApartmentCriteria criteria, Model model) {

		List<Apartment> apartments = apartmentService.findAvailableApartmentByCriteria(criteria);
		model.addAttribute("apartments", apartments);
		model.addAttribute("page_name", "apartments by criteria");

		return "apartments";
	}

}
