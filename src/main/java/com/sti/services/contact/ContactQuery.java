package com.sti.services.contact;

import org.apache.commons.lang.StringUtils;

public class ContactQuery {
	
	private long owner;
	
	private long id;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String address;

	public long getOwner() {
		return owner;
	}

	public void setOwner(long owner) {
		this.owner = owner;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public boolean hasAnyQueryFields() {
		return StringUtils.isNotBlank(this.firstName)
				|| StringUtils.isNotBlank(this.lastName)
				|| StringUtils.isNotBlank(this.email)
				|| StringUtils.isNotBlank(this.address);
	}

	@Override
	public String toString() {
		return String.format("owenr %d, id %d", owner, id);
	}
}
