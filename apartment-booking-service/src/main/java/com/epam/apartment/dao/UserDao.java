package com.epam.apartment.dao;

import com.epam.apartment.model.User;

public interface UserDao {

	User registerNewUser(User user, String password);

	User authoriseUser(String email, String pswd);

	boolean changePswd(int id, String oldPswd, String newPswd);

	boolean restorePswd(String email, String newPswd);

	User editProfile(User user);

	User findByEmail(String email);
}
