package com.sti.rest.auth;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sti.services.CoreServices;
import com.sti.services.account.Credentials;
import com.sti.services.account.CredentialsQuery;
import com.sti.services.auth.AccessToken;

@RestController
public class AuthenticationController {
	
	@Autowired(required=true)
	private ClientDetailsManager clientDetailsManager;
	
	@Autowired(required=true)
	private CoreServices coreServices;

	@RequestMapping("/auth/token")
	public TokenDetails authenticate(
			@RequestParam(value="client_id", required=true) String clientId,
			@RequestParam(value="client_secret", required=false) String clientSecret,
			@RequestParam(value="username", required=true) String username,
			@RequestParam(value="password", required=true) String password
		) {
		if(this.clientDetailsManager.isValidClient(clientId, clientSecret)) {
			//client creds are ok let's check the user creds 
			final CredentialsQuery credsQuery = new CredentialsQuery();
			credsQuery.setUsername(username);
			credsQuery.setPassword(password);
			final Credentials credentials = this.coreServices.findCredentials(credsQuery);
			Validate.notNull(credentials, "Invalid username and/or password!");
			final AccessToken accessToken = this.coreServices.createAccessToken(credentials.getUsername());
			final TokenDetails tokenDetails = new TokenDetails(accessToken);
			return tokenDetails;
		} else {
			throw new RuntimeException("Invalid client credentials specified!");
		}
	}
}
