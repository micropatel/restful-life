package com.sti.rest.heartbeat;

public class Message {
	private String devMessage;
	
	private MessageCode code;

	public String getDevMessage() {
		return devMessage;
	}

	public void setDevMessage(String devMessage) {
		this.devMessage = devMessage;
	}

	public MessageCode getCode() {
		return code;
	}

	public void setCode(MessageCode code) {
		this.code = code;
	}
}
