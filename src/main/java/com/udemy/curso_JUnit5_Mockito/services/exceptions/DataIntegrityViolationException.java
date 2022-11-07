package com.udemy.curso_JUnit5_Mockito.services.exceptions;

public class DataIntegrityViolationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DataIntegrityViolationException(String message) {
		super(message);
	}
}
