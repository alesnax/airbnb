package com.epam.apartment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
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

@Service
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

		userDao.insertNewUser(user, encryptedPassword);
		return userDao.findByEmail(accountDto.getEmail());
	}

	@Override
	public User authoriseUser(String email, String password) {
		User user = userDao.findByEmail(email);
		if (user != null && !passwordEncoder.matches(password, user.getPassword())) {
			user = null;
		}
		user.setPassword(null);
		return user;
	}

	@Override
	public boolean changePswd(int id, String oldPswd, String newPswd) {
		boolean changed = false;
		String newEncryptedPassword = passwordEncoder.encode(newPswd);

		User user = userDao.findById(id);
		if (passwordEncoder.matches(oldPswd, user.getPassword())) {
			userDao.changePswd(id, newEncryptedPassword);
			changed = true;

		}

		return changed;
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

	@Override
	public User findUserByEmail(String email) {
		User user = userDao.findByEmail(email);
		user.setPassword(null);
		return user;
	}

	private boolean emailExist(String email) {
		User user = userDao.findByEmail(email);
		if (user != null) {
			return true;
		}
		return false;
	}
}
