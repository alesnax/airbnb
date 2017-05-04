package com.epam.apartment.web;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.epam.apartment.dto.LoginDto;
import com.epam.apartment.dto.UserDto;
import com.epam.apartment.exception.EmailExistsException;
import com.epam.apartment.model.User;
import com.epam.apartment.service.UserService;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showAuthorisationForm(final Model model) {
		final LoginDto loginDto = new LoginDto();
		model.addAttribute("loginDto", loginDto);
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String processAuthorisation(@ModelAttribute("loginDto") @Valid final LoginDto loginDto, BindingResult result, Errors errors, HttpSession session) {
		User authorisated = null;
		if (result.hasErrors()) {
			return "login";
		} else {
			authorisated = userService.authoriseUser(loginDto);
		}

		if (authorisated == null) {
			result.rejectValue("password", "login.wrong_pass_or_email");
		}
		if (result.hasErrors()) {
			return "login";
		} else {
			session.setAttribute("user", authorisated);
			return "redirect:/user/profile";
		}
	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String showRegistrationForm(final Model model) {
		final UserDto accountDto = new UserDto();
		model.addAttribute("account", accountDto);
		return "registration";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registerUserAccount(@ModelAttribute("account") @Valid final UserDto accountDto, BindingResult result, Errors errors, HttpSession session) {
		User registered = null;
		if (result.hasErrors()) {
			return "registration";
		} else {
			registered = createUserAccount(accountDto, result);
		}

		if (registered == null) {
			result.rejectValue("email", "email.email_exists");
		}
		if (result.hasErrors()) {
			return "registration";
		} else {
			session.setAttribute("user", registered);
			return "redirect:/user/profile";
		}
	}

	@RequestMapping(value = "/profile")
	public String showUserProfile() {
		return "profile";
	}

	@RequestMapping(value = "/logout")
	public String logOut(HttpSession session) {
		session.invalidate();
		return "welcome";
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
