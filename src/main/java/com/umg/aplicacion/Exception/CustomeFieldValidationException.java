package com.umg.aplicacion.Exception;

public class CustomeFieldValidationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4243409699187177393L;
	
	private String fieldName;
	
	public CustomeFieldValidationException(String message, String fieldName) {
		super(message);
		this.fieldName = fieldName;
	}
	
	public String getFieldName() {
		return fieldName;
	}
}
