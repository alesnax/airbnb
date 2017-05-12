package com.epam.apartment.web;

import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.epam.apartment.dto.ChangePasswordDto;
import com.epam.apartment.dto.EditedUserDto;
import com.epam.apartment.dto.LoginDto;
import com.epam.apartment.dto.UserDto;
import com.epam.apartment.exception.EmailExistsException;
import com.epam.apartment.model.PasswordResetToken;
import com.epam.apartment.model.User;
import com.epam.apartment.service.MailService;
import com.epam.apartment.service.UserService;

@Controller
@SessionAttributes("user")
public class UserController {

	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private MailService mailService;

	@Autowired
	private MessageSource messageSource;

	@GetMapping(value = "/user/forgot_pass")
	public String showRestorePasswordForm() {
		return "restorePassword";
	}

	@PostMapping(value = "/user/forgot_pass")
	public String processPasswordRestoring(final HttpServletRequest request, @RequestParam("email") String email, Model model) {
		User user = userService.findUserByEmail(email);

		if (user == null) {
			model.addAttribute("email_error", "email.email_not_exists");
			return "restorePassword";
		}

		String token = UUID.randomUUID().toString();
		userService.createPasswordResetTokenForUser(user, token);

		String subject = "Alesnax airbnb reset password";
		String message = prepareMessage(getAppUrl(request), request.getLocale(), token, user);
		mailService.sendEmail(subject, message, user.getEmail());

		return "redirect:/user/restorePassSend";
	}

	@GetMapping(value = "/user/restorePassSend")
	public String showRestorePasswordSuccessPage() {
		return "restorePassSend";
	}

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
	 * @param model
	 *            stores user (adds in session scope)
	 * @return login page or redirecting to user profile
	 */
	@PostMapping(value = "/login")
	public String processAuthorisation(@ModelAttribute("loginDto") @Valid final LoginDto loginDto, BindingResult result, Model model) {
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
	 * @param model
	 *            stores user (adds to session scope)
	 * @return registration page or redirecting to user profile
	 */
	@PostMapping(value = "/registration")
	public Callable<String> registerUserAccount(@ModelAttribute("account") @Valid final UserDto accountDto, BindingResult result, Model model) {
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
	@RequestMapping(value = "/user/profile")
	public String showUserProfile() {
		return "profile";
	}

	/**
	 * Shows edit profile page and add model attribute editedUser.
	 * 
	 * @param model
	 * @return edit_profile page name
	 */
	@GetMapping(value = "/user/edit")
	public String editProfile(final Model model) {
		final EditedUserDto editedUser = new EditedUserDto();
		model.addAttribute("editedUser", editedUser);
		return "editProfile";
	}

	/**
	 * Process profile editing.
	 * 
	 * @param editedUser
	 * @param result
	 *            binds result with errors
	 * @param errors
	 *            will be shown if validation is failed
	 * @param model
	 * @return
	 */
	@PostMapping(value = "/user/edit")
	public String processEditProfile(@ModelAttribute("editedUser") @Valid final EditedUserDto editedUser, BindingResult result, Model model) {
		User user = null;
		if (result.hasErrors()) {
			return "editProfile";
		} else {
			user = editUserAccount(editedUser, result);
		}
		if (user == null) {
			result.rejectValue("email", "email.email_exists");
		}
		if (result.hasErrors()) {
			return "editProfile";
		} else {
			model.addAttribute(user);
			return "redirect:/user/edit";
		}
	}

	/**
	 * 
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/user/edit/pass")
	public String changePassword(final Model model) {
		final ChangePasswordDto changePassword = new ChangePasswordDto();
		model.addAttribute("changePassword", changePassword);
		return "editPassword";
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
	@PostMapping(value = "/user/edit/pass")
	public String processChangePassword(@ModelAttribute("changePassword") @Valid final ChangePasswordDto changePassword, BindingResult result, HttpSession session) {
		boolean changed = false;
		if (result.hasErrors()) {
			return "editPassword";
		} else {
			changed = userService.changePswd(changePassword.getId(), changePassword.getOldPassword(), changePassword.getNewPassword());
		}
		if (!changed) {
			result.rejectValue("oldPassword", "edit_profile.wrong_old_password");
		}
		if (result.hasErrors()) {
			return "editPassword";
		} else {
			session.setAttribute("successChanged", "Password successfully changed");
			logger.info("User " + changePassword.getId() + " successfully changed password!");
			return "redirect:/user/edit/pass";
		}
	}

	@GetMapping(value = "/user/changePassword")
	public String changePassword(final HttpServletRequest request, final Model model, @RequestParam("id") final long id, @RequestParam("token") final String token) {
		// final Locale locale = request.getLocale();

		final PasswordResetToken passToken = userService.getPasswordResetToken(token);
		if ((passToken == null) || (passToken.getUser().getId() != id)) {
			final String message = "invalid token";
			model.addAttribute("message", message);
			return "redirect:/login";
		}

		final Calendar cal = Calendar.getInstance();
		if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			model.addAttribute("message", "time for token has been expired");
			return "redirect:/login";
		}

		model.addAttribute("user", passToken.getUser());
		return "redirect:/user/updatePassword";
	}

	/**
	 * 
	 * @return
	 */
	@GetMapping(value = "/user/updatePassword")
	public String showUpdatePasswordForm() {
		return "updatePassword";
	}

	/**
	 * 
	 * @param id
	 * @param newPassword
	 * @param matchingPassword
	 * @return
	 */
	@PostMapping(value = "/user/updatePassword")
	public String processUpdatingPassword(@RequestParam final long id, @RequestParam final String newPassword, @RequestParam String matchingPassword) {

		userService.restorePswd(id, newPassword);

		return "redirect:/user/profile";
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

	private String prepareMessage(final String contextPath, final Locale locale, final String token, final User user) {
		final String url = contextPath + "/user/changePassword?id=" + user.getId() + "&token=" + token;
		final String message = messageSource.getMessage("message.resetPassword", null, locale);
		String result = message + " \r\n" + url;
		return result;
	}

	private String getAppUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

}
