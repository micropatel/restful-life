package com.sti.services.account;

import org.apache.commons.lang.Validate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountDAO {
	
	@Autowired(required=true)
	private SessionFactory sessionFactory;
		
	public Account findById(long id) {
		final Session session = this.sessionFactory.getCurrentSession();
		final Account account = (Account) session.get(Account.class, id);
		return account;
	}
	
	public void save(Account account) {
		Validate.notNull(account, "account is null!");
		final Session session = this.sessionFactory.getCurrentSession();
		session.save(account);
	}
}
