package com.sti.rest.auth;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sti.services.CoreServices;
import com.sti.services.account.Account;
import com.sti.services.account.AccountQuery;

@Component
public class AuthServices {
	
	@Autowired(required=true)
	private CoreServices coreServices;
	
	@Autowired(required=true)
	private AuthContextManager authContextManager;

	public Account getAuthenticatedAccount() {
		final AuthContext authContext = this.authContextManager.getAuthContext();
		Validate.notNull(authContext, "authContext is null!");
		final AccountQuery accountQuery = new AccountQuery();
		accountQuery.setId(authContext.getPrincipal());
		
		final Account account = this.coreServices.findAccount(accountQuery);
		Validate.notNull(account, "unable to retrieve current authenticated account!");
		
		return account;
	}
	
	public boolean isAuthorized(AuthRequest authRequest) {
		Validate.notNull(authRequest, "authRequest is null!");
		final Account currentAccount = this.getAuthenticatedAccount();
		return currentAccount.getId() == authRequest.getOwner();
	}
}
