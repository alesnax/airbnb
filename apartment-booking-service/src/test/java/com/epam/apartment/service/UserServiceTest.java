package com.epam.apartment.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DuplicateKeyException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.epam.apartment.dao.UserDao;
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

	@Test
	public void registerNewUserSuccessTest() throws EmailExistsException {
		when(userDao.registerNewUser(any(UserDto.class))).thenReturn(user);
		User registeredUser = userService.registerNewUser(userDto);
		verify(userDao, times(1)).registerNewUser(any(UserDto.class));
		Assert.assertEquals(userService.registerNewUser(userDto), registeredUser);
	}

	@Test(expectedExceptions = EmailExistsException.class)
	public void registerNewUserUnmatchingPswdsTest() throws EmailExistsException {
		when(userDao.registerNewUser(any(UserDto.class))).thenThrow(EmailExistsException.class);
		userService.registerNewUser(userDto);
	}

	@Test
	public void authoriseUserSuccessTest() {
		when(userDao.authoriseUser(anyString(), anyString())).thenReturn(user);
		Assert.assertEquals(userService.authoriseUser(loginDto), user);
	}

	@Test
	public void aithoriseUserUnexistedEmailTest() {
		when(userDao.authoriseUser(anyString(), anyString())).thenReturn(null);
		Assert.assertEquals(userService.authoriseUser(loginDto), null);
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
	public void editProfileSuccessTest() {
		when(userDao.editProfile(any(User.class))).thenReturn(user);
		Assert.assertEquals(userService.editProfile(user), user);
	}

	@Test(expectedExceptions = DuplicateKeyException.class)
	public void editProfileDuplicatedKeyExceptionTest() {
		when(userDao.editProfile(any(User.class))).thenThrow(DuplicateKeyException.class);
		userService.editProfile(user);
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
