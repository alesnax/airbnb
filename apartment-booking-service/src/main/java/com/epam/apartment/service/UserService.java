package com.epam.apartment.service;

import com.epam.apartment.domain.User;

public interface UserService {

	void registerNewUser(User user, char[] pswd, char[] copyPswd) throws ServiceException;;
	
	User authoriseUser(String email, char[] pswd);
	
	boolean changePswd(int id, char[] oldPswd, char[] newPswd);

	boolean restorePswd(String email, char[] pswd, char[] copyPswd) throws ServiceException;
	
	User editProfile(User editedUser);
}
