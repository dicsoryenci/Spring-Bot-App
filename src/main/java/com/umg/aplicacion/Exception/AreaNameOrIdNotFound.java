package com.umg.aplicacion.Exception;

public class AreaNameOrIdNotFound extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4755868509176464915L;
	
	public AreaNameOrIdNotFound() {
		super("Area o Id no encontrado");
	}
	
	public AreaNameOrIdNotFound(String message) {
		super(message);
	}


}
