package com.epam.apartment.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.epam.apartment.validation.ValidEmail;

public class EditedUserDto {

	private int id;

	@NotNull(message = "{name.null}")
	@Size(min = 2, max = 60, message = "{name.size}")
	private String name;

	@NotNull(message = "{surname.null}")
	@Size(min = 2, max = 60, message = "{surname.size}")
	private String surname;

	@ValidEmail(message = "{email.rule}")
	@NotNull(message = "{email.null}")
	@Size(min = 2, max = 60, message = "{email.size}")
	private String email;

	private LocalDate birthday;

	public EditedUserDto() {
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

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

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

}
