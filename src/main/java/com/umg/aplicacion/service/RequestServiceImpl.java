package com.umg.aplicacion.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.umg.aplicacion.Exception.BackupDescriptionOrIdNotFound;
import com.umg.aplicacion.Exception.CustomeFieldValidationException;
import com.umg.aplicacion.Exception.RequestIdNotFound;
import com.umg.aplicacion.entity.Request;
import com.umg.aplicacion.repository.RequestRepository;

@Service
public class RequestServiceImpl implements RequestService {

	@Autowired
	RequestRepository repository;
	
	@Override
	public Iterable<Request> getAllRequests() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}
	
	@Override
	public Request createRequest(Request request) throws Exception {
		request = repository.save(request);
		return request;
	}

	@Override
	public Request getRequestById(Long id) throws RequestIdNotFound {
		// TODO Auto-generated method stub
		//Retorna un optional o sino encuentra lanza una exception
		return repository.findById(id).orElseThrow(() -> new RequestIdNotFound("El Id de la Request no existe."));
	}

	@Override
	public Request updateRequest(Request fromRequest) throws Exception {
		// TODO Auto-generated method stub
		Request toRequest = getRequestById(fromRequest.getId());
		mapRequest(fromRequest, toRequest);
		//Utiliza el mismo método para actualizar, consulta si existe y luego hace le merge, sino realiza el salvado
		return repository.save(toRequest);
	}
	
	
	/**
	 * Map everythin but the password.
	 * @param from
	 * @param to
	 */
	protected void mapRequest(Request from,Request to) {
		to.setDeliveryDate(from.getDeliveryDate());
		to.setApplicationDate(from.getApplicationDate());
		to.setOperatorUser(from.getOperatorUser());
		to.setUserRequest(from.getUserRequest());
		to.setStatus(from.getStatus());
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public void deleteRequest(Long id) throws RequestIdNotFound {
		// TODO Auto-generated method stub
		Request request = getRequestById(id);
		
		repository.delete(request);
		
	}

}
