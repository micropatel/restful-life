package com.sti.rest.heartbeat;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sti.services.status.Status;
import com.sti.services.status.StatusServices;

@RestController
public class HeartBeatController {
	
	@Autowired(required=true)
	private StatusServices statusServices;

	@RequestMapping(value="/status", method=RequestMethod.GET)
	public Message getStatus() {
		final Status status = this.statusServices.getCurrentStatus();
		Validate.notNull(status, "status is null!");
		
		
		Validate.isTrue(status.isConnected(), "unable to retrieve a valid database connection, check logs for more errors!");
		
		final Message result = new Message();
		result.setCode(MessageCode.Ok);
		result.setDevMessage("services are up and kicking!");
		
		return result;
	}
}
