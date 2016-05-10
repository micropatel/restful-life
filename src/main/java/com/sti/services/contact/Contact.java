package com.sti.services.contact;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang.Validate;
import org.hibernate.annotations.GenericGenerator;

import com.sti.services.account.Account;

@Entity
public class Contact {
	@Id
	@GenericGenerator(name="contact_id_gen",strategy="increment")
	@GeneratedValue(generator="contact_id_gen")
	private long id;
		
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String cellphoneNumber;
	
	private String homeNumber;
	
	private String address;
	
	@ManyToOne
	@JoinColumn(name="owned_by", nullable=false)
	private Account owner;
	
	public Contact(){}
			
	public Contact(ContactInfo info) {
		Validate.notNull(info, "info is null!");
		this.firstName = info.getFirstName();
		this.lastName = info.getLastName();
		this.email = info.getEmail();
		this.cellphoneNumber = info.getCellphoneNumber();
		this.homeNumber = info.getHomeNumber();
		this.address = info.getAddress();
	}
	
	public long getId() {
		return id;
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

	public Account getOwner() {
		return owner;
	}

	public void setOwner(Account owner) {
		this.owner = owner;
	}
}
