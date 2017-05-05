package com.epam.apartment.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.epam.apartment.dao.UserDao;
import com.epam.apartment.dto.EditedUserDto;
import com.epam.apartment.dto.LoginDto;
import com.epam.apartment.dto.UserDto;
import com.epam.apartment.exception.EmailExistsException;
import com.epam.apartment.model.User;
import com.epam.apartment.service.impl.UserServiceImpl;

public class UserServiceTest {

	@Mock
	private UserDao userDao;

	@InjectMocks
	private UserServiceImpl userService;

	private UserDto userDto = null;
	private User user = null;
	private LoginDto loginDto = null;
	private String pswd = null;
	private String copyPswd = null;
	private String email = null;
	private String wrongEmail = null;

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		userDto = getUserDto();
		user = getUser();
		loginDto = getLoginDto();
		pswd = "testpass12345";
		copyPswd = "testpass12345";
		email = "alesnax@gmail.com";
		wrongEmail = "alesnax@gmail.com";
	}

	/*
	 * @Test public void registerNewUserSuccessTest() throws
	 * EmailExistsException { when(userDao.registerNewUser(user,
	 * pswd)).thenReturn(user); User returnedUser =
	 * userService.registerNewUser(userDto); Assert.assertEquals(returnedUser,
	 * user); }
	 */

	@Test
	public void authoriseUserSuccessTest() {
		when(userDao.authoriseUser(anyString(), anyString())).thenReturn(user);
		Assert.assertEquals(userService.authoriseUser(email, pswd), user);
	}

	@Test
	public void aithoriseUserUnexistedEmailTest() {
		when(userDao.authoriseUser(anyString(), anyString())).thenReturn(null);
		Assert.assertEquals(userService.authoriseUser(email, pswd), null);
	}

	@Test
	public void changePswdSuccessTest() {
		when(userDao.changePswd(anyInt(), anyString(), anyString())).thenReturn(true);
		Assert.assertEquals(userService.changePswd(1, pswd, copyPswd), true);
	}

	@Test
	public void restorePswdMatchingPswdsTest() throws ServiceException {
		when(userDao.restorePswd(anyString(), anyString())).thenReturn(true);
		Assert.assertEquals(userService.restorePswd(email, pswd, copyPswd), true);
	}

	@Test
	public void editProfileSuccessTest() throws EmailExistsException {
		when(userDao.editProfile(any(User.class))).thenReturn(user);
		Assert.assertEquals(userService.editProfile(new EditedUserDto()), user);
	}

	@Test(expectedExceptions = EmailExistsException.class)
	public void editProfileDuplicatedKeyExceptionTest() throws EmailExistsException {
		when(userDao.editProfile(any(User.class))).thenThrow(EmailExistsException.class);
		userService.editProfile(new EditedUserDto());
	}

	private UserDto getUserDto() {
		UserDto user = new UserDto();
		user.setName("Ales");
		user.setSurname("Nax");
		user.setEmail("alesnax@gmail.com");
		user.setBirthday(LocalDate.of(1991, 3, 13));
		return user;
	}

	private User getUser() {
		User user = new User();
		user.setId(1);
		user.setName("Ales");
		user.setSurname("Nax");
		user.setEmail("alesnax@gmail.com");
		user.setBirthday(LocalDate.of(1991, 3, 13));
		return user;
	}

	private LoginDto getLoginDto() {
		LoginDto loginDto = new LoginDto();
		loginDto.setEmail("newalesnax@gmail.com");
		loginDto.setPassword("12345678N&asd");
		return loginDto;
	}

}
