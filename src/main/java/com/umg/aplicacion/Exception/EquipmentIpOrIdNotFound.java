package com.umg.aplicacion.Exception;

public class EquipmentIpOrIdNotFound extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7940125483245753441L;
	
	public EquipmentIpOrIdNotFound() {
		super("Equipment o Id no encontrado");
	}
	
	public EquipmentIpOrIdNotFound(String message) {
		super(message);
	}


}
