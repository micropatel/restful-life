package com.sti.services.contact;

import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContactDAO {
	
	@Autowired(required=true)
	private SessionFactory sessionFactory;
	
	public Contact findUnique(ContactQuery query) {
		Validate.notNull(query, "query is null!");
		final Session session = this.sessionFactory.getCurrentSession();
		return (Contact) session.createQuery("from Contact c where c.id = :id and c.owner.id = :owner")
								.setParameter("owner", query.getOwner())
								.setParameter("id", query.getId())
								.uniqueResult();
	}
	
	public void save(Contact contact) {
		Validate.notNull(contact, "contact is null!");
		final Session session = this.sessionFactory.getCurrentSession();
		session.save(contact);
	}

	public Collection<Contact> find(ContactQuery query) {
		Validate.notNull(query, "query is null!");
		Validate.notNull(query, "query is null!");
		final Session session = this.sessionFactory.getCurrentSession();
		
		final Criteria criteria =  session.createCriteria(Contact.class)
									.createAlias("owner", "owner")
									.add(Restrictions.eq("owner.id", query.getOwner()));
		
		if(StringUtils.isNotBlank(query.getEmail())) {
			criteria.add(Restrictions.ilike("email", formatForLike(query.getEmail())));
		}
		
		if(StringUtils.isNotBlank(query.getAddress())) {
			criteria.add(Restrictions.ilike("address", formatForLike(query.getAddress())));
		}
		
		if(StringUtils.isNotBlank(query.getFirstName())) {
			criteria.add(Restrictions.ilike("firstName", formatForLike(query.getFirstName())));
		}
		
		if(StringUtils.isNotBlank(query.getLastName())) {
			criteria.add(Restrictions.ilike("lastName", formatForLike(query.getLastName())));
		}
		
		@SuppressWarnings("unchecked")
		final Collection<Contact> contacts = (Collection<Contact>) criteria.list();
		
		if(contacts == null) {
			return Collections.emptyList();
		}
		
		return contacts;
	}
	
	private static final String formatForLike(String v) {
		return "%"+v+"%";
	}
}
