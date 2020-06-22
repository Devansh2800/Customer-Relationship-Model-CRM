package com.luv2code.springdemo.rest;

public class CustomerNotFounException extends RuntimeException {

	public CustomerNotFounException() {
		
	}

	public CustomerNotFounException(String message) {
		super(message);
	}

	public CustomerNotFounException(Throwable cause) {
		super(cause);
	}

	public CustomerNotFounException(String message, Throwable cause) {
		super(message, cause);
	}

	public CustomerNotFounException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
