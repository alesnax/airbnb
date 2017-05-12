package com.epam.apartment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.apartment.dao.UserDao;
import com.epam.apartment.dto.EditedUserDto;
import com.epam.apartment.dto.UserDto;
import com.epam.apartment.exception.EmailExistsException;
import com.epam.apartment.model.PasswordResetToken;
import com.epam.apartment.model.User;
import com.epam.apartment.service.UserService;

@Service("userService")
@Scope(value = "singleton")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

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
		String encryptedPassword = passwordEncoder.encode(accountDto.getPassword());

		return userDao.registerNewUser(user, encryptedPassword);
	}

	@Override
	public User authoriseUser(String email, String password) {
		String encryptedPassword = passwordEncoder.encode(password);
		return userDao.authoriseUser(email, encryptedPassword);
	}

	@Override
	public boolean changePswd(int id, String oldPswd, String newPswd) {
		String oldEncryptedPassword = passwordEncoder.encode(oldPswd);
		String newEncryptedPassword = passwordEncoder.encode(newPswd);
		return userDao.changePswd(id, oldEncryptedPassword, newEncryptedPassword);
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

	@Override
	public void restorePswd(long id, String newPassword) {
		String encryptedPassword = passwordEncoder.encode(newPassword);
		userDao.restorePswd(id, encryptedPassword);
	}

	@Override
	public PasswordResetToken getPasswordResetToken(String token) {

		return userDao.findPasswordResetToken(token);
	}

	@Override
	public void createPasswordResetTokenForUser(User user, String token) {
		PasswordResetToken resetPassToken = new PasswordResetToken(token, user);
		userDao.savePaswordResetToken(resetPassToken);
	}

	public User findUserByEmail(String email) {

		return userDao.findByEmail(email);
	}

	private boolean emailExist(String email) {
		User user = userDao.findByEmail(email);
		if (user != null) {
			return true;
		}
		return false;
	}

}
