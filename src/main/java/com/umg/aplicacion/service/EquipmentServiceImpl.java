package com.umg.aplicacion.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.umg.aplicacion.Exception.AreaNameOrIdNotFound;
import com.umg.aplicacion.Exception.CustomeFieldValidationException;
import com.umg.aplicacion.Exception.EquipmentIpOrIdNotFound;
import com.umg.aplicacion.entity.Area;
import com.umg.aplicacion.entity.Equipment;
import com.umg.aplicacion.repository.AreaRepository;
import com.umg.aplicacion.repository.EquipmentRepository;

@Service
public class EquipmentServiceImpl implements EquipmentService {
	@Autowired
	EquipmentRepository repository;
	
	
	@Override
	public Iterable<Equipment> getAllEquipments() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}
	
	private boolean checkEquipmentIpAvailable(Equipment equipment) throws Exception {
		Optional<Equipment> equipmentFound = repository.findByip(equipment.getIp());
		if(equipmentFound.isPresent()) {
			throw new CustomeFieldValidationException("Ip de Equipo no disponible", "name");			
		} 
		return true;
	}

	@Override
	public Equipment createEquipment(Equipment equipment) throws Exception {
		if(checkEquipmentIpAvailable(equipment)) {
			
			equipment = repository.save(equipment);
		}
		return equipment;
	}

	@Override
	public Equipment getEquipmentById(Long id) throws EquipmentIpOrIdNotFound {
		// TODO Auto-generated method stub
		//Retorna un optional o sino encuentra lanza una exception
		return repository.findById(id).orElseThrow(() -> new EquipmentIpOrIdNotFound("El Id del Equipment no existe."));
	}

	@Override
	public Equipment updateEquipment(Equipment fromEquipment) throws Exception {
		// TODO Auto-generated method stub
		Equipment toEquipment = getEquipmentById(fromEquipment.getId());
		mapEquipment(fromEquipment, toEquipment);
		//Utiliza el mismo método para actualizar, consulta si existe y luego hace le merge, sino realiza el salvado
		return repository.save(toEquipment);
	}
	
	
	/**
	 * Map everythin but the password.
	 * @param from
	 * @param to
	 */
	protected void mapEquipment(Equipment from,Equipment to) {
		to.setIp(to.getIp());
		to.setOperatingSystem(from.getOperatingSystem());
		to.setArea(from.getArea());
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public void deleteEquipment(Long id) throws EquipmentIpOrIdNotFound {
		// TODO Auto-generated method stub
		Equipment equipment = getEquipmentById(id);
		
		repository.delete(equipment);
		
	}
}
