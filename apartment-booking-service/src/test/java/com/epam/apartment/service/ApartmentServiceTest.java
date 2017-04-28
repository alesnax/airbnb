package com.epam.apartment.service;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;


import com.epam.apartment.dao.ApartmentDao;
import com.epam.apartment.model.Apartment;
import com.epam.apartment.model.ApartmentCriteria;
import com.epam.apartment.service.impl.ApartmentServiceImpl;



public class ApartmentServiceTest {

	@Mock
	private ApartmentDao apartmentDao;

	@InjectMocks
	private ApartmentServiceImpl apartmentService;

	private List<Apartment> apartments = null;
	private LocalDate arrivalDate = null;
	private LocalDate leavingDate = null;
	private ApartmentCriteria criteria = null;

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		apartments = createApartments();
		arrivalDate = LocalDate.of(2017, 5, 10);
		leavingDate = LocalDate.of(2017, 5, 17);
		criteria = new ApartmentCriteria();
		criteria.setCity("Lviv");
		criteria.setCountry("Ukraine");
		criteria.setMaxPrice(new BigDecimal(200));
	}

	

	@Test
	public void findAvailableApartmentsSuccessTest() throws ServiceException {
		when(apartmentDao.findAvailableApartments(any(LocalDate.class), any(LocalDate.class))).thenReturn(apartments);
		Assert.assertEquals(apartmentService.findAvailableApartments(arrivalDate, leavingDate), apartments);
	}

	@Test(expectedExceptions = ServiceException.class)
	public void findAvailableApartmentsLeavingBeforeArrivingTest() throws ServiceException {
		apartmentService.findAvailableApartments(leavingDate, arrivalDate);
	}

	@Test
	public void findConcreteApartmentsTwoFoundTest() {
		when(apartmentDao.findConcreteApartment(anyString())).thenReturn(apartments);
		Assert.assertEquals(apartmentService.findConcreteApartments("apartment"), apartments);
	}

	@Test
	public void findApartmentsByCriteria(){
		when(apartmentDao.findAvailableApartmentByCriteria(any(ApartmentCriteria.class))).thenReturn(apartments);
		Assert.assertEquals(apartmentService.findAvailableApartmentByCriteria(criteria), apartments);
	}
	
	
	private List<Apartment> createApartments() {
		List<Apartment> apartments = new ArrayList<>();
		Apartment apartment1 = new Apartment();
		apartment1.setName("apartment1");
		apartments.add(apartment1);
		
		Apartment apartment2 = new Apartment();
		apartment2.setName("apartment2");
		apartments.add(apartment2);
		
		return apartments;
	}
}
