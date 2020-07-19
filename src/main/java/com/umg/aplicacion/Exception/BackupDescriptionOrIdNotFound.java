package com.umg.aplicacion.Exception;

public class BackupDescriptionOrIdNotFound extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8015415231754042201L;
	
	public BackupDescriptionOrIdNotFound() {
		super("Equipment o Id no encontrado");
	}
	
	public BackupDescriptionOrIdNotFound(String message) {
		super(message);
	}

}
