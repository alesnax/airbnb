package com.epam.apartment.domain;

import java.time.LocalDate;

public class Booking extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private User user;
	private Apartment apartment;
	private LocalDate start;
	private LocalDate end;

	public Booking() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Apartment getApartment() {
		return apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}

	public LocalDate getStart() {
		return start;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	public LocalDate getEnd() {
		return end;
	}

	public void setEnd(LocalDate end) {
		this.end = end;
	}
}
