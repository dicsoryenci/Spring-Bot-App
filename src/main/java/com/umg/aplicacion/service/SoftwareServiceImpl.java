package com.umg.aplicacion.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.umg.aplicacion.Exception.CustomeFieldValidationException;
import com.umg.aplicacion.Exception.SoftwareNameOrIdNotFound;
import com.umg.aplicacion.entity.Software;
import com.umg.aplicacion.repository.SoftwareRepository;

@Service
public class SoftwareServiceImpl implements SoftwareService {

	@Autowired
	SoftwareRepository repository;
	
	
	@Override
	public Iterable<Software> getAllSoftwares() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}
	
	private boolean checkSoftwareNameAvailable(Software software) throws Exception {
		Optional<Software> softwareFound = repository.findByname(software.getName());
		if(softwareFound.isPresent()) {
			throw new CustomeFieldValidationException("Name de Software no disponible", "name");			
		} 
		return true;
	}

	@Override
	public Software createSoftware(Software software) throws Exception {
		if(checkSoftwareNameAvailable(software)) {
			
			software = repository.save(software);
		}
		return software;
	}

	@Override
	public Software getSoftwareById(Long id) throws SoftwareNameOrIdNotFound {
		// TODO Auto-generated method stub
		//Retorna un optional o sino encuentra lanza una exception
		return repository.findById(id).orElseThrow(() -> new SoftwareNameOrIdNotFound("El Id del software no existe."));
	}

	@Override
	public Software updateSoftware(Software fromSoftware) throws Exception {
		// TODO Auto-generated method stub
		Software toSoftware = getSoftwareById(fromSoftware.getId());
		mapSoftware(fromSoftware, toSoftware);
		//Utiliza el mismo método para actualizar, consulta si existe y luego hace le merge, sino realiza el salvado
		return repository.save(toSoftware);
	}
	
	
	/**
	 * Map everythin but the password.
	 * @param from
	 * @param to
	 */
	protected void mapSoftware(Software from,Software to) {
		to.setName(from.getName());
		to.setConcurrence(from.getConcurrence());
		to.setImpact(from.getImpact());;
		to.setProvider(from.getProvider());
		to.setRelatedProcess(from.getRelatedProcess());
		to.setEquipment(from.getEquipment());
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public void deleteSoftware(Long id) throws SoftwareNameOrIdNotFound {
		// TODO Auto-generated method stub
		Software software = getSoftwareById(id);
		
		repository.delete(software);
		
	}
}
