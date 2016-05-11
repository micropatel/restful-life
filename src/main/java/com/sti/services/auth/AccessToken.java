package com.sti.services.auth;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.sti.services.account.Account;

@Entity
public class AccessToken {
	@Id
	@GenericGenerator(name="access_token_id_gen",strategy="increment")
	@GeneratedValue(generator="access_token_id_gen")
	private long id;
	
	@Column(nullable=false)
	private String token;
	
	@ManyToOne
	@JoinColumn(name="princial_id")
	private Account principal;
	
	@Column(nullable=false)
	private Calendar created;
	
	@Column(nullable=false)
	private int age;
	
	@Type(type="yes_no")
	private boolean enabled;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Account getPrincipal() {
		return principal;
	}

	public void setPrincipal(Account principal) {
		this.principal = principal;
	}

	public boolean isActive() {
		if(this.enabled) {
			if(this.age == 0) {//always active
				return true;
			} 
			
			//let's find out if the age is good
			
			
			return new DateTime().isAfter(this.getExpirationDate());
		}
		
		return false;
				
	}

	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public DateTime getExpirationDate() {
		final DateTime expiryDate = new DateTime(this.created);
		return expiryDate.plusHours(this.age);
	}

}
