package com.epam.apartment.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.epam.apartment.dao.ApartmentDao;
import com.epam.apartment.domain.Apartment;
import com.epam.apartment.domain.ApartmentCriteria;
import com.epam.apartment.service.ApartmentService;
import com.epam.apartment.service.ServiceException;

@Service
@Scope(value = "singleton")
public class ApartmentServiceImpl implements ApartmentService {

	@Autowired
	private ApartmentDao apartmentDao;

	@Override
	public List<Apartment> findAvailableApartments(LocalDate arrivalDate, LocalDate leavingDate) throws ServiceException {
		List<Apartment> apartments = null;
		if (!validateDates(arrivalDate, leavingDate)) {
			throw new ServiceException("Validation error: arrival after leaving!");
		} else {
			apartments = apartmentDao.findAvailableApartments(arrivalDate, leavingDate);
		}
		return apartments;
	}

	@Override
	public List<Apartment> findConcreteApartments(String name) {
		List<Apartment> apartments = apartmentDao.findConcreteApartment(name);
		return apartments;
	}

	@Override
	public List<Apartment> findAvailableApartmentByCriteria(ApartmentCriteria criteria) {
		List<Apartment> apartments = apartmentDao.findAvailableApartmentByCriteria(criteria);
		return apartments;
	}

	private boolean validateDates(LocalDate arrivalDate, LocalDate leavingDate) {
		if (arrivalDate.isBefore(LocalDate.now())) {
			return false;
		}
		if (arrivalDate.isAfter(leavingDate)) {
			return false;
		}
		return true;
	}
}
