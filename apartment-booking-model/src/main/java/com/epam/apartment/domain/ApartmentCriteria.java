package com.epam.apartment.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ApartmentCriteria extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String country;
	private String city;
	private Integer guestNumber;
	private BigDecimal maxPrice;
	private LocalDate arrivalDate;
	private LocalDate leavingDate;
	
	public ApartmentCriteria(){
		
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getGuestNumber() {
		return guestNumber;
	}

	public void setGuestNumber(Integer guestNumber) {
		this.guestNumber = guestNumber;
	}

	public BigDecimal getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
	}

	public LocalDate getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(LocalDate arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public LocalDate getLeavingDate() {
		return leavingDate;
	}

	public void setLeavingDate(LocalDate leavingDate) {
		this.leavingDate = leavingDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
