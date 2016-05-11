package com.sti.rest.auth;

public class AuthContext {
	
	private long principal;
	
	public AuthContext(long principal) {
		this.principal = principal;
	}

	public long getPrincipal() {
		return principal;
	}

	public void setPrincipal(long principal) {
		this.principal = principal;
	}
}
