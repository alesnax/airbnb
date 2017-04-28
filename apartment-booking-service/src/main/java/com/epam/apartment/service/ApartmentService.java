package com.epam.apartment.service;

import java.time.LocalDate;
import java.util.List;

import com.epam.apartment.model.Apartment;
import com.epam.apartment.model.ApartmentCriteria;

public interface ApartmentService {

	List<Apartment> findAvailableApartments(LocalDate arrivalDate, LocalDate leavingDate) throws ServiceException;

	List<Apartment> findConcreteApartments(String name);

	List<Apartment> findAvailableApartmentByCriteria(ApartmentCriteria criteria);

}
