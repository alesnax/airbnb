package com.epam.apartment.dao;

import static org.h2.engine.Constants.UTF8;

import java.sql.SQLException;
import java.util.ResourceBundle;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.h2.tools.RunScript;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.epam.apartment.model.User;

public class UserDaoTest extends DaoTest {

	private static final ResourceBundle DB_RESOURCE_BUNDLE = ResourceBundle.getBundle("test-db");

	private static final String JDBC_URL = DB_RESOURCE_BUNDLE.getString("jdbc.url");
	private static final String USER = DB_RESOURCE_BUNDLE.getString("jdbc.username");
	private static final String PASSWORD = DB_RESOURCE_BUNDLE.getString("jdbc.password");

	private static final String CREATE_USERS_SCRIPT = DB_RESOURCE_BUNDLE.getString("scripts.create_users");
	private static final String DROP_USERS_SCRIPT = DB_RESOURCE_BUNDLE.getString("scripts.drop_users");
	private static final String USERS_DATASET = DB_RESOURCE_BUNDLE.getString("datasets.users");

	@Autowired
	private UserDao userDao;

	@Override
	protected IDataSet getDataSet() throws Exception {
		@SuppressWarnings("deprecation")
		IDataSet dataset = new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream(USERS_DATASET));
		return dataset;
	}

	@BeforeTest
	public void createSchema() throws SQLException {
		RunScript.execute(JDBC_URL, USER, PASSWORD, CREATE_USERS_SCRIPT, UTF8, false);
	}

	@AfterTest
	public void dropSchema() throws SQLException {
		RunScript.execute(JDBC_URL, USER, PASSWORD, DROP_USERS_SCRIPT, UTF8, false);
	}

	@Test
	public void authoriseUserSuccessTest() {
		User user = userDao.authoriseUser("alesnax@gmail.com", "whjrgf23hjf5gcd21fj35");
		Assert.assertEquals(user.getEmail(), "alesnax@gmail.com");
	}

	@Test
	public void authoriseUserReturnNullTest() {
		User user = userDao.authoriseUser("alesasgfnax@gmail.com", "whjrgf23hjf5gcd21fj35");
		Assert.assertNull(user);
	}

	@Test
	public void registerNewUserSuccessTest() {
		String password = "asfjklhh@89F";
		User user = new User();
		user.setName("alex");
		user.setSurname("nax");
		user.setEmail("qnewemail@gmail.com");

		userDao.registerNewUser(user, password);
		User authorisedUser = userDao.authoriseUser("qnewemail@gmail.com", password);
		Assert.assertEquals(authorisedUser.getEmail(), "qnewemail@gmail.com");
	}

	@Test(expectedExceptions = DuplicateKeyException.class)
	public void registerNewUserWithSameEmailTest() {
		User user = new User();
		user.setName("alex");
		user.setSurname("nax");
		user.setEmail("qnewemail@gmail.com");
		String password = "asfjklhh@89F";

		userDao.registerNewUser(user, password);
		userDao.registerNewUser(user, password);
	}

	@Test
	public void changePswdSuccessTest() {
		boolean updated = userDao.changePswd(1, "whjrgf23hjf5gcd21fj35", "newpassword12345");
		Assert.assertEquals(updated, true);
	}

	@Test
	public void restorePswdSuccessTest() {
		boolean restored = userDao.restorePswd("alesnax@gmail.com", "newpasswd12345");
		Assert.assertEquals(restored, true);
	}

	@Test
	public void restorePswdWithNotFoundEmailTest() {
		boolean restored = userDao.restorePswd("aaaalesnax@gmail.com", "newpasswd12345");
		Assert.assertEquals(restored, false);
	}

	@Test
	public void editProfileSuccessTest() {
		User user = new User();
		user.setId(1);
		user.setName("alex");
		user.setSurname("nax");
		user.setEmail("alesnax@gmail.com");

		User editedUser = userDao.editProfile(user);
		Assert.assertEquals(editedUser.getEmail(), user.getEmail());
	}

	@Test(expectedExceptions = DuplicateKeyException.class)
	public void editProfileThrowsDuplicatedKeyExceptionTest() {
		User user = new User();
		user.setId(3);
		user.setName("alex");
		user.setSurname("nax");
		user.setEmail("alesnax@gmail.com");

		userDao.editProfile(user);
	}

}