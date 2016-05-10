package com.sti.services.status;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StatusDAO {
	
	@Autowired(required=true)
	private SessionFactory sessionFactory;

	public Status getCurrent() {
		final Session currentSession = this.sessionFactory.getCurrentSession();
		
		final Status result = new Status();
		result.setConnected(currentSession.isConnected());

		return result;
	}
}
