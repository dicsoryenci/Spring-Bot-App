package com.umg.aplicacion.service;

import com.umg.aplicacion.Exception.EquipmentIpOrIdNotFound;
import com.umg.aplicacion.entity.Equipment;

public interface EquipmentService {
	
	public Iterable<Equipment> getAllEquipments();

	public Equipment createEquipment(Equipment equipment) throws Exception;
	
	public Equipment getEquipmentById(Long id) throws Exception;
	
	public Equipment updateEquipment(Equipment equipment) throws Exception;
	
	public void deleteEquipment(Long id) throws EquipmentIpOrIdNotFound;

}
