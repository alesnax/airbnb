package com.epam.apartment.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.epam.apartment.validation.PasswordMatches;
import com.epam.apartment.validation.ValidPassword;

@PasswordMatches(message = "{password.unmatching}")
public class ChangePasswordDto {

	private int id;

	@NotNull(message = "{password.null}")
	@Size(min = 8, max = 40, message = "{password.size}")
	@ValidPassword(message = "{password.rule}")
	private transient String oldPassword;

	@NotNull(message = "{password.null}")
	@Size(min = 8, max = 40, message = "{password.size}")
	@ValidPassword(message = "{password.rule}")
	private transient String newPassword;

	@NotNull(message = "{password.null}")
	@Size(min = 8, max = 40, message = "{password.size}")
	private transient String matchingPassword;

	public ChangePasswordDto() {

	}

	public void setId(final int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(final String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(final String newPassword) {
		this.newPassword = newPassword;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(final String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}
}
