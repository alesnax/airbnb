package com.epam.apartment.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.epam.apartment.dto.UserDto;
import com.epam.apartment.exception.EmailExistsException;
import com.epam.apartment.model.User;
import com.epam.apartment.service.ServiceException;
import com.epam.apartment.service.UserService;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showAuthorisationForm() {

		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String processAuthorisation(String email, String pswd) {
		User user = userService.authoriseUser(email, pswd);
		return null;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String showRegistrationForm(final HttpServletRequest request, final Model model) {
		// LOGGER.debug("Rendering registration page.");
		final UserDto accountDto = new UserDto();
		model.addAttribute("user", accountDto);
		return "registration";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid UserDto accountDto, BindingResult result, WebRequest request, Errors errors) throws ServiceException {

		if (result.hasErrors()) {
			return new ModelAndView("registration"/* , "user", accountDto */);
		}

		User registered = new User();
		if (!result.hasErrors()) {
			registered = createUserAccount(accountDto, result);
		}
		if (registered == null) {
			result.rejectValue("email", "message.regError");
		}
		if (result.hasErrors()) {
			return new ModelAndView("registration", "user", accountDto);
		} else {
			return new ModelAndView("successRegister", "user", accountDto);
		}
	}

	private User createUserAccount(UserDto accountDto, BindingResult result) {
		User registered = null;
		try {
			registered = userService.registerNewUser(accountDto);
		} catch (EmailExistsException e) {
			return null;
		}
		return registered;
	}

}
