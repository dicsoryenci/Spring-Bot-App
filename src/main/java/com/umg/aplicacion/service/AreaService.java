package com.umg.aplicacion.service;

import com.umg.aplicacion.Exception.AreaNameOrIdNotFound;
import com.umg.aplicacion.Exception.UsernameOrIdNotFound;
import com.umg.aplicacion.entity.Area;

public interface AreaService {
	
	public Iterable<Area> getAllAreas();

	public Area createArea(Area area) throws Exception;
	
	public Area getAreaById(Long id) throws Exception;
	
	public Area updateArea(Area user) throws Exception;
	
	public void deleteArea(Long id) throws AreaNameOrIdNotFound;

}
