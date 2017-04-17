package com.epam.apartment.dao;

import static org.h2.engine.Constants.UTF8;

import java.sql.SQLException;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.h2.tools.RunScript;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.epam.apartment.domain.User;

public class UserDaoTest extends DaoTest {

	private static final String JDBC_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
	private static final String USER = "TEST";
	private static final String PASSWORD = "1212011";
	private static final String CREATE_USERS_SCRIPT = "classpath:dbscripts\\create_users.sql";
	private static final String DROP_USERS_SCRIPT = "classpath:dbscripts\\drop_users.sql";

	@Autowired
	private UserDao userDao;

	@Override
	protected IDataSet getDataSet() throws Exception {
		@SuppressWarnings("deprecation")
		IDataSet dataset = new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("datasets\\users.xml"));
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
		User user = new User();
		user.setName("alex");
		user.setSurname("nax");
		user.setEmail("qnewemail@gmail.com");
		String pswd = "ashftkla";
		
		userDao.registerNewUser(user, pswd);
		User authorisedUser = userDao.authoriseUser("qnewemail@gmail.com", "ashftkla");
		Assert.assertEquals(authorisedUser.getEmail(), "qnewemail@gmail.com");
	}
	
	@Test(expectedExceptions = DuplicateKeyException.class)
	public void registerNewUserWithSameEmailTest() {
		User user = new User();
		user.setName("alex");
		user.setSurname("nax");
		user.setEmail("qnewemail@gmail.com");
		String pswd = "ashftkla";
		
		userDao.registerNewUser(user, pswd);
		userDao.registerNewUser(user, pswd);
	}

	@Test
	public void changePswdSuccessTest(){
		boolean updated = userDao.changePswd(1, "whjrgf23hjf5gcd21fj35", "newpassword12345");
		Assert.assertEquals(updated, true);
	}
	
	@Test
	public void restorePswdSuccessTest(){
		boolean restored = userDao.restorePswd("alesnax@gmail.com", "newpasswd12345");
		Assert.assertEquals(restored, true);
	}
	
	@Test
	public void restorePswdWithNotFoundEmailTest(){
		boolean restored = userDao.restorePswd("aaaalesnax@gmail.com", "newpasswd12345");
		Assert.assertEquals(restored, false);
	}
	
	@Test
	public void editProfileSuccessTest(){
		User user = new User();
		user.setId(1);
		user.setName("alex");
		user.setSurname("nax");
		user.setEmail("alesnax@gmail.com");
		
		User editedUser = userDao.editProfile(user);
		Assert.assertEquals(editedUser.getEmail(), user.getEmail());
	}
	
	@Test(expectedExceptions = DuplicateKeyException.class)
	public void editProfileThrowsDuplicatedKeyExceptionTest(){
		User user = new User();
		user.setId(3);
		user.setName("alex");
		user.setSurname("nax");
		user.setEmail("alesnax@gmail.com");
		
		userDao.editProfile(user);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}