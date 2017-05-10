package com.epam.apartment.web;

import java.util.concurrent.Callable;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.epam.apartment.dto.ChangePasswordDto;
import com.epam.apartment.dto.EditedUserDto;
import com.epam.apartment.dto.LoginDto;
import com.epam.apartment.dto.UserDto;
import com.epam.apartment.exception.EmailExistsException;
import com.epam.apartment.model.User;
import com.epam.apartment.service.UserService;

@Controller
@RequestMapping(value = "/user")
@SessionAttributes("user")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * Show user authorization page, add model attribute loginDto.
	 * 
	 * @param model
	 *            stores loginDto
	 * @return login page logic name
	 */
	@GetMapping(value = "/login")
	public String showAuthorisationForm(final Model model) {
		final LoginDto loginDto = new LoginDto();
		model.addAttribute("loginDto", loginDto);
		return "login";
	}

	/**
	 * Process user authorization.
	 * 
	 * @param loginDto
	 *            contain email and password data
	 * @param result
	 *            binds result with errors
	 * @param errors
	 *            will be shown if validation is failed
	 * @param model
	 *            stores user (adds in session scope)
	 * @return login page or redirecting to user profile
	 */
	@PostMapping(value = "/login")
	public String processAuthorisation(@ModelAttribute("loginDto") @Valid final LoginDto loginDto, BindingResult result, Errors errors, Model model) {
		User user = null;
		if (result.hasErrors()) {
			return "login";
		} else {
			user = userService.authoriseUser(loginDto.getEmail(), loginDto.getPassword());
		}

		if (user == null) {
			result.rejectValue("password", "login.wrong_pass_or_email");
			return "login";
		} else {
			model.addAttribute(user);
			return "redirect:/user/profile";
		}
	}

	/**
	 * Show user registration page, add model attribute userDto.
	 * 
	 * @param model
	 *            stores account dto
	 * @return registration page logic name
	 */
	@GetMapping(value = "/registration")
	public String showRegistrationForm(final Model model) {
		final UserDto accountDto = new UserDto();
		model.addAttribute("account", accountDto);
		return "registration";
	}

	/**
	 * Process user registration.
	 * 
	 * @param accountDto
	 *            contain information about new user
	 * @param result
	 *            binds result with errors
	 * @param errors
	 *            will be shown if validation is failed
	 * @param model
	 *            stores user (adds to session scope)
	 * @return registration page or redirecting to user profile
	 */
	@PostMapping(value = "/registration")
	public Callable<String> registerUserAccount(@ModelAttribute("account") @Valid final UserDto accountDto, BindingResult result, Errors errors, Model model) {
		return new Callable<String>() {

			@Override
			public String call() throws Exception {
				User user = null;
				if (result.hasErrors()) {
					return "registration";
				} else {
					user = createUserAccount(accountDto, result);
				}

				if (user == null) {
					result.rejectValue("email", "email.email_exists");
				}
				if (result.hasErrors()) {
					return "registration";
				} else {
					model.addAttribute(user);
					return "redirect:/user/profile";
				}
			}
		};
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
	@GetMapping(value = "/edit")
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
	 *            binds result with errors
	 * @param errors
	 *            will be shown if validation is failed
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String processEditProfile(@ModelAttribute("editedUser") @Valid final EditedUserDto editedUser, BindingResult result, Errors errors, Model model) {
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
			model.addAttribute(user);
			// session.setAttribute("user", user);
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
	 * @param result
	 *            binds result with errors
	 * @param errors
	 *            will be shown if validation is failed
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
	public String logOut(SessionStatus status) {
		status.setComplete();
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
