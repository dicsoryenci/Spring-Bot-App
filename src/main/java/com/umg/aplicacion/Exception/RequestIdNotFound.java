package com.umg.aplicacion.Exception;

public class RequestIdNotFound extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8874257613582937567L;
	
	public RequestIdNotFound() {
		super("Request o Id no encontrado");
	}
	
	public RequestIdNotFound(String message) {
		super(message);
	}

}
