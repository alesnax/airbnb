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

import com.epam.apartment.dto.ChangePasswordDto;
import com.epam.apartment.dto.EditedUserDto;
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

	/**
	 * Show user authorization page, add model attribute loginDto.
	 * 
	 * @param model
	 * @return login page logic name
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showAuthorisationForm(final Model model) {
		final LoginDto loginDto = new LoginDto();
		model.addAttribute("loginDto", loginDto);
		return "login";
	}

	/**
	 * Process user authorization.
	 * 
	 * @param loginDto
	 * @param result
	 * @param errors
	 * @param session
	 * @return login page or redirecting to user profile
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String processAuthorisation(@ModelAttribute("loginDto") @Valid final LoginDto loginDto, BindingResult result, Errors errors, HttpSession session) {
		User authorisated = null;
		if (result.hasErrors()) {
			return "login";
		} else {
			authorisated = userService.authoriseUser(loginDto.getEmail(), loginDto.getPassword());
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

	/**
	 * Show user registration page, add model attribute userDto.
	 * 
	 * @param model
	 * @return registration page logic name
	 */
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String showRegistrationForm(final Model model) {
		final UserDto accountDto = new UserDto();
		model.addAttribute("account", accountDto);
		return "registration";
	}

	/**
	 * Process user registration.
	 * 
	 * @param accountDto
	 * @param result
	 * @param errors
	 * @param session
	 * @return registration page or redirecting to user profile
	 */
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

	/**
	 * Shows user profile page.
	 * 
	 * @return profile page name
	 */
	@RequestMapping(value = "/profile")
	public String showUserProfile() {
		return "profile";
	}

	/**
	 * Shows edit profile page and add model attribute editedUser.
	 * 
	 * @param model
	 * @return edit_profile page name
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editProfile(final Model model) {
		final EditedUserDto editedUser = new EditedUserDto();
		model.addAttribute("editedUser", editedUser);
		return "edit_profile";
	}

	/**
	 * Process profile editing.
	 * 
	 * @param editedUser
	 * @param result
	 * @param errors
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String processEditProfile(@ModelAttribute("editedUser") @Valid final EditedUserDto editedUser, BindingResult result, Errors errors, HttpSession session) {
		User user = null;
		if (result.hasErrors()) {
			return "edit_profile";
		} else {
			user = editUserAccount(editedUser, result);
		}
		if (user == null) {
			result.rejectValue("email", "email.email_exists");
		}
		if (result.hasErrors()) {
			return "edit_profile";
		} else {
			session.setAttribute("user", editedUser);
			return "redirect:/user/edit";
		}
	}

	/**
	 * 
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/pass", method = RequestMethod.GET)
	public String changePassword(final Model model) {
		final ChangePasswordDto changePassword = new ChangePasswordDto();
		model.addAttribute("changePassword", changePassword);
		return "edit_password";
	}

	/**
	 * 
	 * @param changePassword
	 * @param model
	 * @param result
	 * @param errors
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/edit/pass", method = RequestMethod.POST)
	public String processChangePassword(@ModelAttribute("changePassword") @Valid final ChangePasswordDto changePassword, BindingResult result, Errors errors, HttpSession session) {
		boolean changed = false;
		if (result.hasErrors()) {
			return "edit_password";
		} else {
			changed = userService.changePswd(changePassword.getId(), changePassword.getOldPassword(), changePassword.getNewPassword());
		}
		if (!changed) {
			result.rejectValue("oldPassword", "edit_profile.wrong_old_password");
		}
		if (result.hasErrors()) {
			return "edit_password";
		} else {
			session.setAttribute("successChanged", "Password successfully changed");
			// set success message attr later
			return "redirect:/user/edit/pass";
		}
	}

	/**
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/logout")
	public String logOut(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	/**
	 * 
	 * @param accountDto
	 * @param result
	 * @return
	 */
	private User createUserAccount(UserDto accountDto, BindingResult result) {
		User registered = null;
		try {
			registered = userService.registerNewUser(accountDto);
		} catch (EmailExistsException e) {
			return null;
		}
		return registered;
	}

	/**
	 * 
	 * @param userDto
	 * @param result
	 * @return
	 */
	private User editUserAccount(EditedUserDto userDto, BindingResult result) {
		User edited = null;
		try {
			edited = userService.editProfile(userDto);
		} catch (EmailExistsException e) {
			return null;
		}
		return edited;
	}

}
