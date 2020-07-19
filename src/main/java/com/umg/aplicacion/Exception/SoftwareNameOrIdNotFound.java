package com.umg.aplicacion.Exception;

public class SoftwareNameOrIdNotFound extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6660017291763216234L;
	
	public SoftwareNameOrIdNotFound() {
		super("Software o Id no encontrado");
	}
	
	public SoftwareNameOrIdNotFound(String message) {
		super(message);
	}

}
