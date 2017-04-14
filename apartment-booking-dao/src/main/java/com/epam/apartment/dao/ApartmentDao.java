package com.epam.apartment.dao;

import java.time.LocalDate;
import java.util.List;

import com.epam.apartment.domain.Apartment;
import com.epam.apartment.domain.ApartmentCriteria;

public interface ApartmentDao {

	List<Apartment> findAvailableApartments(LocalDate arrivalDate, LocalDate leavingDate);
	
	List<Apartment> findConcreteApartment(String name);
	
	List<Apartment> findAvailableApartmentByCriteria(ApartmentCriteria criteria);

}
