package com.microservice.user.exception;

public class ResourceNotFoundException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException() {
		super("resource not found on server !!");
	}
	
	public ResourceNotFoundException(String message) {
		super(message);
	}

}
