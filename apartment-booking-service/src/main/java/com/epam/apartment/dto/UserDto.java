package com.epam.apartment.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.epam.apartment.validation.PasswordMatches;
import com.epam.apartment.validation.ValidEmail;
import com.epam.apartment.validation.ValidPassword;

@PasswordMatches(message = "{password.unmatching}")
public class UserDto {

	@NotNull(message = "{name.null}")
	@Size(min = 2, max = 60, message = "{name.size}")
	private String name;

	@NotNull(message = "{surname.null}")
	@Size(min = 2, max = 60, message = "{surname.size}")
	private String surname;

	@NotNull(message = "{password.null}")
	@Size(min = 8, max = 40, message = "{password.size}")
	@ValidPassword(message = "{password.rule}")
	private transient String password;

	@NotNull(message = "{password.null}")
	@Size(min = 8, max = 40, message = "{password.size}")
	private transient String matchingPassword;

	@ValidEmail(message = "{email.rule}")
	@NotNull(message = "{email.null}")
	@Size(min = 2, max = 60, message = "{email.size}")
	private String email;

	private LocalDate birthday;

	public UserDto() {

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	/*
	 * private Integer role;
	 * 
	 * public Integer getRole() { return role; }
	 * 
	 * public void setRole(final Integer role) { this.role = role; }
	 */

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(final String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

}