package com.sti.rest.auth;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sti.services.CoreServices;
import com.sti.services.account.Account;
import com.sti.services.account.Credentials;
import com.sti.services.account.CredentialsQuery;

@Component
public class AuthServices {
	
	@Autowired(required=true)
	private CoreServices coreServices;

	public Account getAuthenticatedAccount() {
//		final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		final CredentialsQuery credsQuery = new CredentialsQuery();
		
//		if (principal instanceof UserDetails) {
//		  String username = ((UserDetails)principal).getUsername();
//		  credsQuery.setUsername(username);
//		} else {
//		  String username = principal.toString();
//		  credsQuery.setUsername(username);
//		}
		
		credsQuery.setUsername("test");
		
//		Validate.isTrue(StringUtils.isNotBlank(credsQuery.getUsername()), 
//						"Wierd, there is no username specified for the current authenticated user!");
		
		final Credentials creds = this.coreServices.findCredentials(credsQuery);
		final Account account = creds.getAccount();
		Validate.notNull(account, "unable to retrieve current authenticated account!");
		
		return account;
	}
	
	public boolean isAuthorized(AuthRequest authRequest) {
		Validate.notNull(authRequest, "authRequest is null!");
		final Account currentAccount = this.getAuthenticatedAccount();
		return currentAccount.getId() == authRequest.getOwner();
	}
}
