package com.sti.services.account;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CredentialsDAO {

	@Autowired(required=true)
	private SessionFactory sessionFactory;
	
	public Credentials find(CredentialsQuery query) {
		Validate.notNull(query, "query is null!");
		
		final Session session = this.sessionFactory.getCurrentSession();
		final Criteria criteria =  session.createCriteria(Credentials.class);
		
		if(StringUtils.isNotBlank(query.getUsername())) {
			criteria.add(Restrictions.eq("username", query.getUsername()));
		}
		
		if(StringUtils.isNotBlank(query.getPassword())) {
			criteria.add(Restrictions.eq("password", query.getPassword()));
		}
		
		return (Credentials) criteria.uniqueResult();
	}
	

}
