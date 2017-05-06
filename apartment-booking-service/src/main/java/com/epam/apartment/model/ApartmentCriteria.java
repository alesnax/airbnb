package com.epam.apartment.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class ApartmentCriteria extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String country;
	private String city;
	private Integer guestNumber;
	private BigDecimal maxPrice;

	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate arrivalDate;

	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate leavingDate;

	public ApartmentCriteria() {

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arrivalDate == null) ? 0 : arrivalDate.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((guestNumber == null) ? 0 : guestNumber.hashCode());
		result = prime * result + ((leavingDate == null) ? 0 : leavingDate.hashCode());
		result = prime * result + ((maxPrice == null) ? 0 : maxPrice.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApartmentCriteria other = (ApartmentCriteria) obj;
		if (arrivalDate == null) {
			if (other.arrivalDate != null)
				return false;
		} else if (!arrivalDate.equals(other.arrivalDate))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (guestNumber == null) {
			if (other.guestNumber != null)
				return false;
		} else if (!guestNumber.equals(other.guestNumber))
			return false;
		if (leavingDate == null) {
			if (other.leavingDate != null)
				return false;
		} else if (!leavingDate.equals(other.leavingDate))
			return false;
		if (maxPrice == null) {
			if (other.maxPrice != null)
				return false;
		} else if (!maxPrice.equals(other.maxPrice))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ApartmentCriteria [country=");
		builder.append(country);
		builder.append(", city=");
		builder.append(city);
		builder.append(", guestNumber=");
		builder.append(guestNumber);
		builder.append(", maxPrice=");
		builder.append(maxPrice);
		builder.append(", arrivalDate=");
		builder.append(arrivalDate);
		builder.append(", leavingDate=");
		builder.append(leavingDate);
		builder.append("]");
		return builder.toString();
	}

}
