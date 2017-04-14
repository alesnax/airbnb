package com.epam.apartment.domain;

public class ApartmentType extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String type;
	private String description;

	public ApartmentType() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
