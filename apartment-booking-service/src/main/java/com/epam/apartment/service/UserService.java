package com.epam.apartment.service;

import com.epam.apartment.dto.EditedUserDto;
import com.epam.apartment.dto.UserDto;
import com.epam.apartment.exception.EmailExistsException;
import com.epam.apartment.model.User;

public interface UserService {

	User registerNewUser(UserDto user) throws EmailExistsException;

	User authoriseUser(String email, String password);

	boolean changePswd(int id, String oldPswd, String newPswd);

	boolean restorePswd(String email, String pswd, String copyPswd) throws ServiceException;

	User editProfile(EditedUserDto editedUser) throws EmailExistsException;
}
