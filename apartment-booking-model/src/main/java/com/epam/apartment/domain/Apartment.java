package com.epam.apartment.domain;

import java.math.BigDecimal;

public class Apartment extends Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private BigDecimal price;
	private int maxGuestNumber;
	private ApartmentType type;
	private Location location;
	
	public Apartment(){
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getMaxGuestNumber() {
		return maxGuestNumber;
	}

	public void setMaxGuestNumber(int maxGuestNumber) {
		this.maxGuestNumber = maxGuestNumber;
	}

	public ApartmentType getType() {
		return type;
	}

	public void setType(ApartmentType type) {
		this.type = type;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	

}
