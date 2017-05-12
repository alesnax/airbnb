package com.epam.apartment.service;

import com.epam.apartment.dto.EditedUserDto;
import com.epam.apartment.dto.UserDto;
import com.epam.apartment.exception.EmailExistsException;
import com.epam.apartment.model.PasswordResetToken;
import com.epam.apartment.model.User;

public interface UserService {

	User registerNewUser(UserDto user) throws EmailExistsException;

	User authoriseUser(String email, String password);

	boolean changePswd(int id, String oldPswd, String newPswd);

	User editProfile(EditedUserDto editedUser) throws EmailExistsException;

	User findUserByEmail(String email);

	void createPasswordResetTokenForUser(User user, String token);

	PasswordResetToken getPasswordResetToken(String token);

	void restorePswd(long id, String newPassword);
}
