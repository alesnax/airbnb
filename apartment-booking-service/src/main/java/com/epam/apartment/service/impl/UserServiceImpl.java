package com.epam.apartment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.apartment.dao.UserDao;
import com.epam.apartment.dto.EditedUserDto;
import com.epam.apartment.dto.UserDto;
import com.epam.apartment.exception.EmailExistsException;
import com.epam.apartment.model.User;
import com.epam.apartment.service.UserService;

@Service("userService")
@Scope(value = "singleton")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Transactional
	@Override
	public User registerNewUser(UserDto accountDto) throws EmailExistsException {

		if (emailExist(accountDto.getEmail())) {
			throw new EmailExistsException("There is an account with that email address: " + accountDto.getEmail());
		}
		User user = new User();
		user.setName(accountDto.getName());
		user.setSurname(accountDto.getSurname());
		user.setEmail(accountDto.getEmail());
		user.setBirthday(accountDto.getBirthday());

		return userDao.registerNewUser(user, accountDto.getPassword());
	}

	@Override
	public User authoriseUser(String email, String password) {
		return userDao.authoriseUser(email, password);
	}

	@Override
	public boolean changePswd(int id, String oldPswd, String newPswd) {
		return userDao.changePswd(id, oldPswd, newPswd);
	}

	@Override
	public boolean restorePswd(String email, String pswd, String copyPswd) {
		return userDao.restorePswd(email, pswd);
	}

	@Transactional
	@Override
	public User editProfile(EditedUserDto editedUser) throws EmailExistsException {
		User updatedUser = null;
		User user = new User();
		user.setId(editedUser.getId());
		user.setName(editedUser.getName());
		user.setSurname(editedUser.getSurname());
		user.setEmail(editedUser.getEmail());
		user.setBirthday(editedUser.getBirthday());
		try {
			updatedUser = userDao.editProfile(user);
		} catch (DuplicateKeyException e) {
			throw new EmailExistsException("There is an account with that email address: " + editedUser.getEmail());
		}
		return updatedUser;
	}

	private boolean emailExist(String email) {
		User user = userDao.findByEmail(email);
		if (user != null) {
			return true;
		}
		return false;
	}

}
