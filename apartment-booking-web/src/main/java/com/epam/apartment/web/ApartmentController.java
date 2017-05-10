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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.epam.apartment.exception.ApartmentNotFoundException;
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

	@GetMapping(value = "/main")
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

	@RequestMapping(value = "/info/{id}")
	public String showApartmentinfo(@PathVariable("id") int id, Model model) {

		Apartment apartment = apartmentService.findApartmentById(id);
		if (apartment == null) {
			throw new ApartmentNotFoundException();
		}
		model.addAttribute(apartment);

		return "apartment";
	}

	@RequestMapping(value = "/find/{location}")
	public String findNearestApartments(@MatrixVariable(name = "city", pathVar = "location") String city, Model model) {
		List<Apartment> apartments = apartmentService.findAvailableApartmentByLocation(city);
		model.addAttribute("apartments", apartments);
		model.addAttribute("page_name", "apartments by nearest location");

		return "apartments";
	}

}
