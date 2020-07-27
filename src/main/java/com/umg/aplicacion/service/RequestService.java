package com.umg.aplicacion.service;

import com.umg.aplicacion.Exception.RequestIdNotFound;
import com.umg.aplicacion.entity.Request;

public interface RequestService {

	public Iterable<Request> getAllRequests();

	public Request createRequest(Request request) throws Exception;
	
	public Request getRequestById(Long id) throws Exception;
	
	public Request updateRequest(Request request) throws Exception;
	
	public void deleteRequest(Long id) throws RequestIdNotFound;
	
}
