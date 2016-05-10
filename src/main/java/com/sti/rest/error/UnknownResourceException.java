package com.sti.rest.error;

/**
 * Simulated business-logic exception indicating a desired business entity or record cannot be found.
 */
public class UnknownResourceException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5645150317073359488L;

	public UnknownResourceException(String msg) {
        super(msg);
    }
}