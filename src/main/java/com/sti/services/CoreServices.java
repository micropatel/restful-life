package com.sti.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sti.services.account.Account;
import com.sti.services.account.AccountDAO;
import com.sti.services.account.AccountQuery;
import com.sti.services.account.Credentials;
import com.sti.services.account.CredentialsDAO;
import com.sti.services.account.CredentialsQuery;
import com.sti.services.auth.AccessToken;
import com.sti.services.auth.AccessTokenConfig;
import com.sti.services.auth.AccessTokenDAO;
import com.sti.services.auth.TokenInfo;
import com.sti.services.contact.Contact;
import com.sti.services.contact.ContactDAO;
import com.sti.services.contact.ContactInfo;
import com.sti.services.contact.ContactQuery;

@Component
public class CoreServices {
	
	@Autowired(required=true)
	private ContactDAO contactDAO;
	
	@Autowired(required=true)
	private AccountDAO accountDAO;
	
	@Autowired(required=true)
	private CredentialsDAO credentialsDAO;
	
	@Autowired(required=true)
	private AccessTokenDAO accessTokenDAO;
	
	@Autowired(required=true)
	private AccessTokenConfig accessTokenConfig;
		
	@Transactional
	public ContactInfo findContact(ContactQuery query) {		
		final Contact contact = this.contactDAO.findUnique(query);
		Validate.notNull(contact, "unable to find contact using query info " + query);
		return new ContactInfo(contact);
	}

	@Transactional
	public Collection<Contact> findContacts(ContactQuery query) {
		final Collection<Contact> contacts = this.contactDAO.find(query);
		
		if(contacts == null) {
			return Collections.emptyList();
		}
		
		return contacts;
	}
	
	@Transactional
	public Account findAccount(AccountQuery query) {
		final Account account = this.accountDAO.findById(query.getId());
		Validate.notNull(account, "Unable to find account with id " + query.getId());
		return account;
	}
	
	@Transactional
	public ContactInfo createContact(ContactInfo info) {
		Validate.notNull(info, "info is null!");
		final Contact newContact = new Contact(info);
		final Account account = this.accountDAO.findById(info.getOwner());
		newContact.setOwner(account);
		this.contactDAO.save(newContact);
		return new ContactInfo(newContact);
	}
	
	/**
	 * create a list of contacts for a given account, current implementation can be improved by using a bulk update
	 * @param contacts
	 */
	public Collection<ContactInfo> createContacts(Collection<ContactInfo> contacts) {
		Validate.isTrue(contacts != null && !contacts.isEmpty(), "contact is null or empty!");
		
		final Collection<ContactInfo> result = new ArrayList<>();
		
		for(ContactInfo info : contacts) {
			result.add(this.createContact(info));
		}
		
		return result;
	}
	
	@Transactional
	public ContactInfo updateContact(ContactInfo info) {
		Validate.notNull(info, "info is null!");
		final ContactQuery query = new ContactQuery();
		query.setId(info.getId());
		query.setOwner(info.getOwner());
		final Contact contact = this.contactDAO.findUnique(query);
		Validate.notNull(contact, "unable to find contact matching query " + query);
		if(StringUtils.isNotBlank(info.getLastName())) {
			contact.setLastName(info.getLastName());
		}
		
		if(StringUtils.isNotBlank(info.getFirstName())) {
			contact.setFirstName(info.getFirstName());
		}
		
		if(StringUtils.isNotBlank(info.getCellphoneNumber())) {
			contact.setCellphoneNumber(info.getCellphoneNumber());
		}
		
		if(StringUtils.isNotBlank(info.getHomeNumber())) {
			contact.setHomeNumber(info.getHomeNumber());
		}
		
		if(StringUtils.isNotBlank(info.getAddress())) {
			contact.setAddress(info.getAddress());
		}
		
		if(StringUtils.isNotBlank(info.getEmail())) {
			contact.setEmail(info.getEmail());
		}
		
		this.contactDAO.save(contact);
		
		return new ContactInfo(contact);
	}
	
	@Transactional
	public Credentials findCredentials(CredentialsQuery query) {
		Validate.notNull(query, "query is null!");
		final Credentials credentials = this.credentialsDAO.find(query);
		return credentials;
	}
	
	@Transactional
	public TokenInfo findAccessToken(String token) {
		Validate.isTrue(StringUtils.isNotBlank(token), "token is blank!");
		final AccessToken accessToken = this.accessTokenDAO.find(token);
		final TokenInfo info = new TokenInfo();
		info.setActive(accessToken.isActive());
		info.setToken(accessToken.getToken());
		info.setPrincipal(accessToken.getPrincipal().getId());
		
		return info;
	}

	@Transactional
	public AccessToken createAccessToken(String username) {
		Validate.isTrue(StringUtils.isNotBlank(username), "username is blank!");
		final CredentialsQuery credsQuery = new CredentialsQuery();
		credsQuery.setUsername(username);
		final Credentials credentials = this.findCredentials(credsQuery);
		Validate.notNull(credentials, "unable to find credentials matching username " + username);
		final AccessToken newAccessToken = new AccessToken();
		newAccessToken.setToken(UUID.randomUUID().toString());
		newAccessToken.setPrincipal(credentials.getAccount());
		newAccessToken.setCreated(Calendar.getInstance());
		newAccessToken.setEnabled(true);
		newAccessToken.setAge(this.accessTokenConfig.getAge());
		this.accessTokenDAO.save(newAccessToken);
		
		return newAccessToken;
	}
	
	@Transactional
	public Account createAccount(String username, String password) {
		Validate.isTrue(StringUtils.isNotBlank(username), "username is blank!");
		Validate.isTrue(StringUtils.isNotBlank(password), "password is blank!");
		final Account newAccount = new Account();
		newAccount.setCreated(Calendar.getInstance());
		
		final Credentials newCredentials = new Credentials();
		newCredentials.setAccount(newAccount);
		newCredentials.setUsername(username);
		newCredentials.setPassword(password);//should use encryption here such as jasypt
		
		this.accountDAO.save(newAccount);
		this.credentialsDAO.save(newCredentials);
		return newCredentials.getAccount();
	}
}
