 package com.epam.apartment.dao;

import com.epam.apartment.model.User;

public interface UserDao {

	void registerNewUser(User user, String pswd);
	
	User authoriseUser(String email, String pswd);
	
	boolean changePswd(int id, String oldPswd, String newPswd);
	
	boolean restorePswd(String email, String newPswd);
	
	User editProfile(User editedUser);
}
