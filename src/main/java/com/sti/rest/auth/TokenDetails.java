package com.sti.rest.auth;

import java.util.Calendar;
import java.util.Locale;

import org.apache.commons.lang.Validate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sti.services.auth.AccessToken;

public class TokenDetails {
	private String token;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone="GMT")
	private Calendar expirationDate;
	
	public TokenDetails(){}
	
	public TokenDetails(AccessToken accessToken) {
		Validate.notNull(accessToken, "accessToken is null!");
		this.token = accessToken.getToken();
		this.expirationDate = accessToken.getExpirationDate().toCalendar(Locale.CANADA);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Calendar getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Calendar expirationDate) {
		this.expirationDate = expirationDate;
	}
}
