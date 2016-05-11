package com.sti.rest.auth;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sti.services.CoreServices;
import com.sti.services.account.Account;

@RestController
public class AccountServices {
	
	@Autowired(required=true)
	private CoreServices coreServices;
	
	@RequestMapping(method = RequestMethod.POST, value="/test/accounts")
	public Account createAccount(
			@RequestBody NewAccountRequest request
		) {
		Validate.isTrue(StringUtils.isNotBlank(request.getUsername()), "username is blank!");
		Validate.isTrue(StringUtils.isNotBlank(request.getPassword()), "password is blank!");
		final Account account = this.coreServices.createAccount(request.getUsername(), request.getPassword());
		return account;
	}
}
