package com.sti.rest.auth;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthContextManager {
	
	@Autowired(required=true)
	private HttpServletRequest request;
	
	public AuthContext getAuthContext() {
		return (AuthContext) request.getAttribute(AuthContext.class.getName());
	}
	
	public void setAuthContext(AuthContext authContext) {
		Validate.notNull(authContext, "authContext is null!");
		request.setAttribute(AuthContext.class.getName(), authContext);
	}
	
	public boolean hasAuthContext() {
		return this.getAuthContext() != null;
	}
}
