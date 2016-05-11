package com.sti.rest.auth;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Required;

public class ClientDetailsManager {
	private Map<String, String> clientDetails;

	@Required
	public void setClientDetails(Map<String, String> clientDetails) {
		this.clientDetails = clientDetails;
	}
	
	public boolean isValidClient(String id, String secret) {
		Validate.isTrue(StringUtils.isNotBlank(id), "id is blank!");
		Validate.isTrue(StringUtils.isNotBlank(secret), "secret is blank!");
		
		return this.clientDetails.containsKey(id)
				&& this.clientDetails.get(id).equalsIgnoreCase(secret);
	}
}
