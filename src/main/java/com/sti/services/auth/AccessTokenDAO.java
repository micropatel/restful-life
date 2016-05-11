package com.sti.services.auth;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccessTokenDAO {
	
	@Autowired(required=true)
	private SessionFactory sessionFactory;
	
	public AccessToken find(String token) {
		Validate.isTrue(StringUtils.isNotBlank(token), "token is blank!");
		final Session session = this.sessionFactory.getCurrentSession();
		final AccessToken result = (AccessToken)
									session.createCriteria(AccessToken.class)
										.add(Restrictions.eq("token", token))
										.add(Restrictions.eq("enabled", true))
										.uniqueResult();
		
		return result;
	}
	
	public void save(AccessToken accessToken) {
		Validate.notNull(accessToken, "accessToken is blank!");
		final Session session = this.sessionFactory.getCurrentSession();
		session.save(accessToken);
	}
}
