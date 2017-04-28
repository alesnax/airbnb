package com.epam.apartment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.epam.apartment.dao.UserDao;
import com.epam.apartment.model.User;
import com.epam.apartment.service.ServiceException;
import com.epam.apartment.service.UserService;

@Service
@Scope(value = "singleton")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public void registerNewUser(User user, char[] pswd, char[] copyPswd) throws ServiceException {
		if (validatePswds(pswd, copyPswd)) {
			userDao.registerNewUser(user, encryptPswd(pswd));
		} else {
			throw new ServiceException("Passwords don't match");
		}
	}

	@Override
	public User authoriseUser(String email, char[] pswd) {
		User user = userDao.authoriseUser(email, encryptPswd(pswd));
		return user;
	}

	@Override
	public boolean changePswd(int id, char[] oldPswd, char[] newPswd) {
		return userDao.changePswd(id, encryptPswd(oldPswd), encryptPswd(newPswd));
	}

	@Override
	public boolean restorePswd(String email, char[] pswd, char[] copyPswd) throws ServiceException {
		if (!validatePswds(pswd, copyPswd)) {
			throw new ServiceException("Passwords don't match");
		}
		return userDao.restorePswd(email, encryptPswd(pswd));
	}

	@Override
	public User editProfile(User editedUser) {
		User user = userDao.editProfile(editedUser);
		return user;
	}

	private String encryptPswd(char[] pswd) {
		// temp stub for future implementation of sha1
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < pswd.length; i++) {
			builder.append(pswd[i]);
		}
		System.out.println(pswd);
		return builder.toString();
	}

	private boolean validatePswds(char[] pswd, char[] copyPswd) {
		if (pswd.length != copyPswd.length) {
			return false;
		}
		if (pswd.length <= 5) {
			return false;
		}
		for (int i = 0; i < pswd.length; i++) {
			if (pswd[i] != copyPswd[i]) {
				return false;
			}
		}

		return true;
	}

}
