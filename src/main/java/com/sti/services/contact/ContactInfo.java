package com.sti.services.contact;

import org.apache.commons.lang.Validate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ContactInfo {
	private long id;
	
	@JsonProperty("first_name")
	private String firstName;
	
	@JsonProperty("last_name")
	private String lastName;
	
	private String email;
	
	@JsonProperty("cellphone_number")
	private String cellphoneNumber;
	
	@JsonProperty("home_number")
	private String homeNumber;
	
	private String address;
	
	private long owner;
	
	public ContactInfo(){}
	
	public ContactInfo(Contact contact) {
		Validate.notNull(contact, "contact is null!");
		this.id = contact.getId();
		this.firstName = contact.getFirstName();
		this.lastName = contact.getLastName();
		this.email = contact.getEmail();
		this.cellphoneNumber = contact.getCellphoneNumber();
		this.homeNumber = contact.getHomeNumber();
		this.address = contact.getAddress();
		this.owner = contact.getOwner().getId();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCellphoneNumber() {
		return cellphoneNumber;
	}

	public void setCellphoneNumber(String cellphoneNumber) {
		this.cellphoneNumber = cellphoneNumber;
	}

	public String getHomeNumber() {
		return homeNumber;
	}

	public void setHomeNumber(String homeNumber) {
		this.homeNumber = homeNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getOwner() {
		return owner;
	}

	public void setOwner(long owner) {
		this.owner = owner;
	}
	
}
