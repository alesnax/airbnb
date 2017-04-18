package com.epam.apartment.dao;

import static org.h2.engine.Constants.UTF8;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.h2.tools.RunScript;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.epam.apartment.domain.Apartment;
import com.epam.apartment.domain.ApartmentCriteria;

public class ApartmentDaoTest extends DaoTest {

	private static final ResourceBundle DB_RESOURCE_BUNDLE = ResourceBundle.getBundle("test-db");

	private static final String JDBC_URL = DB_RESOURCE_BUNDLE.getString("jdbc.url");
	private static final String USER = DB_RESOURCE_BUNDLE.getString("jdbc.username");
	private static final String PASSWORD = DB_RESOURCE_BUNDLE.getString("jdbc.password");

	private static final String CREATE_ALL_TABLES_SCRIPT = DB_RESOURCE_BUNDLE.getString("scripts.create_all_tables");
	private static final String DROP_ALL_TABLES_SCRIPT = DB_RESOURCE_BUNDLE.getString("scripts.drop_all_tables");
	private static final String LOCATIONS_DATASET = DB_RESOURCE_BUNDLE.getString("datasets.locations");
	private static final String APARTMENT_TYPES_DATASET = DB_RESOURCE_BUNDLE.getString("datasets.apartment_types");
	private static final String APARTMENTS_DATASET = DB_RESOURCE_BUNDLE.getString("datasets.apartments");
	private static final String BOOKINGS_DATASET = DB_RESOURCE_BUNDLE.getString("datasets.bookings");
	private static final String USERS_DATASET = DB_RESOURCE_BUNDLE.getString("datasets.users");

	@Autowired
	private ApartmentDao apartmentDao;

	@Override
	protected IDataSet getDataSet() throws Exception {
		@SuppressWarnings("deprecation")
		IDataSet[] datasets = new IDataSet[] { new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream(APARTMENT_TYPES_DATASET)),
				new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream(LOCATIONS_DATASET)), new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream(USERS_DATASET)),
				new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream(APARTMENTS_DATASET)),
				new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream(BOOKINGS_DATASET)) };
		return new CompositeDataSet(datasets);
	}

	@BeforeTest
	public void createSchema() throws SQLException {
		RunScript.execute(JDBC_URL, USER, PASSWORD, CREATE_ALL_TABLES_SCRIPT, UTF8, false);
	}

	@AfterTest
	public void dropSchema() throws SQLException {
		RunScript.execute(JDBC_URL, USER, PASSWORD, DROP_ALL_TABLES_SCRIPT, UTF8, false);
	}

	@Test
	public void findAvailableApartmentsForDatesWithBookingsTest() {
		List<Apartment> apartments = apartmentDao.findAvailableApartments(LocalDate.of(2017, 4, 5), LocalDate.of(2017, 4, 17));
		Assert.assertEquals(apartments.size(), 20);
	}

	@Test
	public void findAvailableApartmentsForDatesWithoutBookingsTest() {
		List<Apartment> apartments = apartmentDao.findAvailableApartments(LocalDate.of(2017, 3, 10), LocalDate.of(2017, 3, 17));
		Assert.assertEquals(apartments.size(), 22);
	}

	@Test
	public void findConcreteApartmentByExistedNameTest() {
		List<Apartment> apartments = apartmentDao.findConcreteApartment("city");
		Assert.assertEquals(apartments.size(), 2);
	}

	@Test
	public void findConcreteApartmentByUnexistedNameTest() {
		List<Apartment> apartments = apartmentDao.findConcreteApartment("bla");
		Assert.assertEquals(apartments.size(), 0);
	}

	@Test
	public void findApartmentByCriteriaCityCountryTest() {
		ApartmentCriteria criteria = new ApartmentCriteria();
		criteria.setCity("Lviv");
		criteria.setCountry("Ukraine");

		List<Apartment> apartments = apartmentDao.findAvailableApartmentByCriteria(criteria);
		Assert.assertEquals(apartments.size(), 5);
	}

}
