package com.epam.apartment.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
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
import com.epam.apartment.domain.User;
import com.epam.apartment.service.impl.UserServiceImpl;

public class UserServiceTest {

	@Mock
	private UserDao userDao;

	@InjectMocks
	private UserServiceImpl userService;

	private User user = null;
	private String pswd = null;
	private String copyPswd = null;
	private String unmatchingPswd = null;
	private String email = null;
	private String wrongEmail = null;

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		user = getUser();
		pswd = "testpass12345";
		copyPswd = "testpass12345";
		unmatchingPswd = "anotherPswd12345";
		email = "alesnax@gmail.com";
		wrongEmail = "alesnax@gmail.com";
	}

	@Test
	public void registerNewUserSuccessTest() throws ServiceException {
		doNothing().when(userDao).registerNewUser(any(User.class), anyString());
		userService.registerNewUser(user, pswd.toCharArray(), copyPswd.toCharArray());
		verify(userDao, times(1)).registerNewUser(any(User.class), anyString());
	}

	@Test(expectedExceptions = ServiceException.class)
	public void registerNewUserUnmatchingPswdsTest() throws ServiceException {
		userService.registerNewUser(user, pswd.toCharArray(), unmatchingPswd.toCharArray());
	}

	@Test(expectedExceptions = DuplicateKeyException.class)
	public void registerNewUserThrowDuplicatedKeyExceptionTest() throws ServiceException {
		doThrow(DuplicateKeyException.class).when(userDao).registerNewUser(any(User.class), anyString());
		userService.registerNewUser(user, pswd.toCharArray(), copyPswd.toCharArray());
		verify(userDao, times(1)).registerNewUser(any(User.class), anyString());
	}

	@Test
	public void authoriseUserSuccessTest() {
		when(userDao.authoriseUser(email, pswd)).thenReturn(user);
		Assert.assertEquals(userService.authoriseUser(email, pswd.toCharArray()), user);
	}

	@Test
	public void aithoriseUserUnexistedEmailTest() {
		when(userDao.authoriseUser(anyString(), anyString())).thenReturn(null);
		Assert.assertEquals(userService.authoriseUser(wrongEmail, pswd.toCharArray()), null);
	}

	@Test
	public void changePswdSuccessTest() {
		when(userDao.changePswd(anyInt(), anyString(), anyString())).thenReturn(true);
		Assert.assertEquals(userService.changePswd(1, pswd.toCharArray(), copyPswd.toCharArray()), true);
	}

	@Test
	public void restorePswdMatchingPswdsTest() throws ServiceException {
		when(userDao.restorePswd(anyString(), anyString())).thenReturn(true);
		Assert.assertEquals(userService.restorePswd(email, pswd.toCharArray(), copyPswd.toCharArray()), true);
	}

	@Test(expectedExceptions = ServiceException.class)
	public void restorePswdUnmatchingPswdsTest() throws ServiceException {
		userService.restorePswd(email, pswd.toCharArray(), unmatchingPswd.toCharArray());
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

	private User getUser() {
		User user = new User();
		user.setId(1);
		user.setName("Ales");
		user.setSurname("Nax");
		user.setEmail("alesnax@gmail.com");
		user.setBirthday(LocalDate.of(1991, 3, 13));
		return user;
	}

}
