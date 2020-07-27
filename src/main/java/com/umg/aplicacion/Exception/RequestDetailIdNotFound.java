package com.umg.aplicacion.Exception;

public class RequestDetailIdNotFound extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1225561952115566221L;
	
	public RequestDetailIdNotFound() {
		super("Request Detail o Id no encontrado");
	}
	
	public RequestDetailIdNotFound(String message) {
		super(message);
	}

}
