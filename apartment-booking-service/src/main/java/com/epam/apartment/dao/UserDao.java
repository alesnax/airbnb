package com.epam.apartment.dao;

import com.epam.apartment.model.PasswordResetToken;
import com.epam.apartment.model.User;

public interface UserDao {

	void insertNewUser(User user, String password);

	boolean changePswd(int id, String newPswd);

	User editProfile(User user);

	User findByEmail(String email);

	void savePaswordResetToken(PasswordResetToken resetPassToken);

	PasswordResetToken findPasswordResetToken(String token);

	void restorePswd(long id, String newPassword);

	User findById(long id);
}
