package com.umg.aplicacion.Exception;

public class UsernameOrIdNotFound extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7340833064933153775L;

	public UsernameOrIdNotFound() {
		super("Usuario o Id no encontrado");
	}
	
	public UsernameOrIdNotFound(String message) {
		super(message);
	}

}
