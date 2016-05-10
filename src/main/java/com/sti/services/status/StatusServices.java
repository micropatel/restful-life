package com.sti.services.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class StatusServices {
	
	@Autowired(required=true)
	private StatusDAO statusDAO;
	
	@Transactional
	public Status getCurrentStatus() {
		return this.statusDAO.getCurrent();
	}
}
