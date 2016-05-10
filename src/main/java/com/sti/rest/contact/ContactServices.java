package com.sti.rest.contact;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sti.rest.auth.AuthRequest;
import com.sti.rest.auth.AuthServices;
import com.sti.services.CoreServices;
import com.sti.services.account.Account;
import com.sti.services.contact.Contact;
import com.sti.services.contact.ContactInfo;
import com.sti.services.contact.ContactQuery;

/**
 * Contact REST Services that allows an account holder to search, update and delete contacts, Authorization of the request is carried out.
 * As of this implementation the authenticated account must match the account specified in the api
 * 
 * @author ash
 *
 */
@RestController
public class ContactServices {

	@Autowired(required=true)
	private CoreServices coreServices;
	
	@Autowired(required=true)
	private AuthServices authServices;
	
	/**
	 * find a specific contact matching id
	 * 
	 * @param owner account id that owns the contact
	 * @param id the contact id
	 * @return the contact matching the id or an error if there is no contact with this id or the the authenticated account isn't authorized 
	 * to access request information
	 */
	@RequestMapping(method=RequestMethod.GET, value="/v1/accounts/{owner}/contacts/{id}")
	public ContactInfo find(@PathVariable("owner") long owner, @PathVariable("id") long id) {
		//authorize, can be done more elegantly through spring security
		final AuthRequest authRequest = new AuthRequest();
		authRequest.setOwner(owner);
		Validate.isTrue(this.authServices.isAuthorized(authRequest), "you are not authorized to access information for account" + owner);		

		final ContactQuery query = new ContactQuery();
		query.setOwner(owner);
		query.setId(id);
		final ContactInfo contact = this.coreServices.findContact(query);
		Validate.notNull(contact, "unable to find contact with id " + id);
		return contact;
	}
	
	/**
	 * Search a list of contacts that matches the query parameters, if no query parameters are specified, all contacts will be returned.
	 * 
	 * The params search is based on string matching of contains. for examle if the contact email is "me@home.com" and the email field contains home
	 * the contact with the me@home.com should be returned.
	 * 
	 * if no matches, an empty array will be returned
	 * 
	 * @param owner account that owns the contacts
	 * @param firstName first name pattern
	 * @param lastName last name pattern
	 * @param email email pattern
	 * @param address address pattern
	 * 
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET, value = "/v1/accounts/{owner}/contacts")
	public Collection<ContactInfo> search(@PathVariable("owner")long owner
			, @RequestParam(value="firstName", required=false) String firstName
			, @RequestParam(value="lastName", required=false) String lastName
			, @RequestParam(value="email", required=false) String email
			, @RequestParam(value="address", required=false) String address
			){
		//authorize, can be done more elegantly through spring security or aop
		final AuthRequest authRequest = new AuthRequest();
		authRequest.setOwner(owner);
		Validate.isTrue(this.authServices.isAuthorized(authRequest), "you are not authorized to access information for account" + owner);
		
		final ContactQuery query = new ContactQuery();
		query.setOwner(owner);
		if(StringUtils.isNotBlank(firstName)) {
			query.setFirstName(firstName);
		}
		
		if(StringUtils.isNotBlank(lastName)) {
			query.setLastName(lastName);
		}
		
		if(StringUtils.isNotBlank(email)) {
			query.setEmail(email);
		}
		
		if(StringUtils.isNotBlank(address)) {
			query.setAddress(address);
		}

		final Collection<Contact> contacts = this.coreServices.findContacts(query);
		
		final Collection<ContactInfo> result = new ArrayList<>();
		for(Contact contact : contacts) {
			result.add(new ContactInfo(contact));
		}
		
		return result;
	}
	
	/**
	 * Create an account based on the information in the request body
	 * 
	 * throws a runtime error if an error has occured during creation.
	 * 
	 * @param owner account that requested the contact creation
	 * @param contactInfo information about the contact
	 * @return the contact that was created 
	 */
	@RequestMapping(method = RequestMethod.POST, value="/v1/accounts/{owner}/contacts")
	public ContactInfo createContact(@PathVariable("owner") long owner, @RequestBody ContactInfo contactInfo) {
		//verify request
		Validate.notNull(contactInfo, "contactInfo is null!");
		
		//authorize, can be done more elegantly through spring security
		final AuthRequest authRequest = new AuthRequest();
		authRequest.setOwner(owner);
		Validate.isTrue(this.authServices.isAuthorized(authRequest), "you are not authorized to access information for account" + owner);
		
		//never modify request params
		final ContactInfo newContact = new ContactInfo();
		newContact.setEmail(contactInfo.getEmail());
		newContact.setFirstName(contactInfo.getFirstName());
		newContact.setLastName(contactInfo.getLastName());
		newContact.setCellphoneNumber(contactInfo.getCellphoneNumber());
		newContact.setHomeNumber(contactInfo.getHomeNumber());
		newContact.setAddress(contactInfo.getAddress());
		final Account currentAccount = this.authServices.getAuthenticatedAccount();
		newContact.setOwner(currentAccount.getId());
		
		final ContactInfo savedContact = this.coreServices.createContact(newContact);

		//return what we just saved
		return savedContact;
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/v1/accounts/{owner}/contacts/{id}")
	public ContactInfo createContact(@PathVariable("owner") long owner
			, @PathVariable("id") long id
			, @RequestBody ContactInfo contactInfo
		) {
		//verify request
		Validate.notNull(contactInfo, "contactInfo is null!");
		
		//authorize, can be done more elegantly through spring security
		final AuthRequest authRequest = new AuthRequest();
		authRequest.setOwner(owner);
		Validate.isTrue(this.authServices.isAuthorized(authRequest), "you are not authorized to access information for account" + owner);
		
		//never modify request params
		final ContactInfo newContact = new ContactInfo();
		newContact.setId(id);
		newContact.setEmail(contactInfo.getEmail());
		newContact.setFirstName(contactInfo.getFirstName());
		newContact.setLastName(contactInfo.getLastName());
		newContact.setCellphoneNumber(contactInfo.getCellphoneNumber());
		newContact.setHomeNumber(contactInfo.getHomeNumber());
		newContact.setAddress(contactInfo.getAddress());
		final Account currentAccount = this.authServices.getAuthenticatedAccount();
		newContact.setOwner(currentAccount.getId());
		
		final ContactInfo savedContact = this.coreServices.updateContact(newContact);

		//return what we just saved
		return savedContact;
	}
}
