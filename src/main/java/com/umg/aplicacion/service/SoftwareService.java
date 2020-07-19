package com.umg.aplicacion.service;

import com.umg.aplicacion.Exception.SoftwareNameOrIdNotFound;
import com.umg.aplicacion.entity.Software;

public interface SoftwareService {
	
	public Iterable<Software> getAllSoftwares();

	public Software createSoftware(Software software) throws Exception;
	
	public Software getSoftwareById(Long id) throws Exception;
	
	public Software updateSoftware(Software software) throws Exception;
	
	public void deleteSoftware(Long id) throws SoftwareNameOrIdNotFound;

}
