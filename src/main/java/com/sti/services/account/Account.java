package com.sti.services.account;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.apache.commons.lang.Validate;
import org.hibernate.annotations.GenericGenerator;

import com.sti.services.contact.Contact;

@Entity
public class Account {
	@Id
	@GenericGenerator(name="account_id_gen",strategy="increment")
	@GeneratedValue(generator="account_id_gen")
	private long id;
	
	@Column(nullable=false)
	private String authority;
	
	private Calendar created;
	
	@OneToOne(mappedBy="account")
	private Credentials credentials;
	
	@OneToMany(mappedBy="owner")
	private Collection<Contact> contacts;

	public long getId() {
		return id;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}
	
	public void addContact(Contact contact) {
		Validate.notNull(contact, "contact is null!");
		if(this.contacts == null) {
			this.contacts = new ArrayList<>();
		}
		
		this.contacts.add(contact);
		contact.setOwner(this);
	}

	public Collection<Contact> getContacts() {
		return contacts;
	}

	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
}
