package com.epam.apartment.dao;

import static org.h2.engine.Constants.UTF8;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

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

	private static final String JDBC_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
	private static final String USER = "TEST";
	private static final String PASSWORD = "1212011";
	private static final String CREATE_ALL_TABLES_SCRIPT = "classpath:dbscripts\\create_all_tables.sql";
	private static final String DROP_ALL_TABLES_SCRIPT = "classpath:dbscripts\\drop_all_tables.sql";

	@Autowired
	private ApartmentDao apartmentDao;

	@Override
	protected IDataSet getDataSet() throws Exception {
		@SuppressWarnings("deprecation")
		IDataSet[] datasets = new IDataSet[] {
				new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("datasets\\type_apartments.xml")),
				new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("datasets\\locations.xml")),
				new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("datasets\\users.xml")),
				new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("datasets\\apartments.xml")),
				new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("datasets\\bookings.xml")) };
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
	public void findAvailableApartmentsForDatesWithBookingsTest(){
		List<Apartment> apartments = apartmentDao.findAvailableApartments(LocalDate.of(2017, 4, 5), LocalDate.of(2017, 4, 17));
		Assert.assertEquals(apartments.size(), 20);
	}
	
	@Test 
	public void findAvailableApartmentsForDatesWithoutBookingsTest(){
		List<Apartment> apartments = apartmentDao.findAvailableApartments(LocalDate.of(2017, 3, 10), LocalDate.of(2017, 3, 17));
		Assert.assertEquals(apartments.size(), 22);
	}
	
	@Test
	public void findConcreteApartmentByExistedNameTest(){
		List<Apartment> apartments = apartmentDao.findConcreteApartment("city");
		Assert.assertEquals(apartments.size(), 2);
	}
	
	@Test
	public void findConcreteApartmentByUnexistedNameTest(){
		List<Apartment> apartments = apartmentDao.findConcreteApartment("bla");
		Assert.assertEquals(apartments.size(), 0);
	}
	
	@Test
	public void findApartmentByCriteriaCityCountryTest(){
		ApartmentCriteria criteria = new ApartmentCriteria();
		criteria.setCity("Lviv");
		criteria.setCountry("Ukraine");
		
		List<Apartment> apartments = apartmentDao.findAvailableApartmentByCriteria(criteria);
		Assert.assertEquals(apartments.size(), 5);
	}
	
	
	
	
}
