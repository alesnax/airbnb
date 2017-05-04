package com.epam.apartment.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.epam.apartment.validation.ValidEmail;
import com.epam.apartment.validation.ValidPassword;

public class LoginDto {

	@ValidEmail(message = "{email.rule}")
	@NotNull(message = "{email.null}")
	@Size(min = 2, max = 60, message = "{email.size}")
	private String email;

	@NotNull(message = "{password.null}")
	@Size(min = 8, max = 40, message = "{password.size}")
	@ValidPassword(message = "{password.rule}")
	private String password;

	public LoginDto() {

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}
}
