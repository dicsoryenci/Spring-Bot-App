package com.umg.aplicacion.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.umg.aplicacion.Exception.AreaNameOrIdNotFound;
import com.umg.aplicacion.Exception.CustomeFieldValidationException;
import com.umg.aplicacion.entity.Area;
import com.umg.aplicacion.repository.AreaRepository;

@Service
public class AreaServiceImpl implements AreaService {
	@Autowired
	AreaRepository repository;
	
	
	@Override
	public Iterable<Area> getAllAreas() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}
	
	private boolean checkAreaNameAvailable(Area area) throws Exception {
		Optional<Area> userFound = repository.findByname(area.getName());
		if(userFound.isPresent()) {
			throw new CustomeFieldValidationException("Name de Area no disponible", "name");			
		} 
		return true;
	}

	@Override
	public Area createArea(Area area) throws Exception {
		if(checkAreaNameAvailable(area)) {
			
			area = repository.save(area);
		}
		return area;
	}

	@Override
	public Area getAreaById(Long id) throws AreaNameOrIdNotFound {
		// TODO Auto-generated method stub
		//Retorna un optional o sino encuentra lanza una exception
		return repository.findById(id).orElseThrow(() -> new AreaNameOrIdNotFound("El Id del area no existe."));
	}

	@Override
	public Area updateArea(Area fromArea) throws Exception {
		// TODO Auto-generated method stub
		Area toArea = getAreaById(fromArea.getId());
		mapArea(fromArea, toArea);
		//Utiliza el mismo método para actualizar, consulta si existe y luego hace le merge, sino realiza el salvado
		return repository.save(toArea);
	}
	
	
	/**
	 * Map everythin but the password.
	 * @param from
	 * @param to
	 */
	protected void mapArea(Area from,Area to) {
		to.setName(from.getName());
		to.setDescription(from.getDescription());
		to.setState(from.isState());
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public void deleteArea(Long id) throws AreaNameOrIdNotFound {
		// TODO Auto-generated method stub
		Area area = getAreaById(id);
		
		repository.delete(area);
		
	}
}
