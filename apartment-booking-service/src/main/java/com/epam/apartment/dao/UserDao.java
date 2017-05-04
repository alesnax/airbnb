package com.epam.apartment.dao;

import com.epam.apartment.dto.EditedUserDto;
import com.epam.apartment.dto.UserDto;
import com.epam.apartment.model.User;

public interface UserDao {

	User registerNewUser(UserDto accountDto);

	User authoriseUser(String email, String pswd);

	boolean changePswd(int id, String oldPswd, String newPswd);

	boolean restorePswd(String email, String newPswd);

	User editProfile(EditedUserDto editedUser);

	User findByEmail(String email);
}
