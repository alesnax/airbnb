package com.epam.apartment.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.epam.apartment.validation.PasswordMatches;
import com.epam.apartment.validation.ValidEmail;
import com.epam.apartment.validation.ValidPassword;

@PasswordMatches(message = "passwords don't match")
public class UserDto {
	@NotNull(message = "empty value")
	@Size(min = 5, max = 16, message = "name wrong length")
	private String name;

	@NotNull(message = "empty value")
	@Size(min = 5, max = 25, message = "surname wrong length")
	private String surname;

	@ValidPassword
	private String password;

	@NotNull(message = "empty value")
	@Size(min = 1)
	private String matchingPassword;

	@ValidEmail()
	@NotNull(message = "empty value")
	@Size(min = 5)
	private String email;

	private LocalDate birthday;

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