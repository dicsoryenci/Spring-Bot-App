package com.umg.aplicacion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.umg.aplicacion.Exception.RequestDetailIdNotFound;
import com.umg.aplicacion.entity.RequestDetail;
import com.umg.aplicacion.repository.RequestDetailRepository;

@Service
public class RequestDetailServiceImpl implements RequestDetailService {
	
	@Autowired
	RequestDetailRepository repository;
	
	@Override
	public Iterable<RequestDetail> getAllRequestDetails() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}
	
	@Override
	public RequestDetail createRequestDetail(RequestDetail requestDetail) throws Exception {
		requestDetail = repository.save(requestDetail);
		return requestDetail;
	}

	@Override
	public RequestDetail getRequestDetailById(Long id) throws RequestDetailIdNotFound {
		// TODO Auto-generated method stub
		//Retorna un optional o sino encuentra lanza una exception
		return repository.findById(id).orElseThrow(() -> new RequestDetailIdNotFound("El Id del Request Detail no existe."));
	}

	@Override
	public RequestDetail updateRequestDetail(RequestDetail fromRequestDetail) throws Exception {
		// TODO Auto-generated method stub
		RequestDetail toRequestDetail = getRequestDetailById(fromRequestDetail.getId());
		mapRequestDetail(fromRequestDetail, toRequestDetail);
		//Utiliza el mismo método para actualizar, consulta si existe y luego hace le merge, sino realiza el salvado
		return repository.save(toRequestDetail);
	}
	
	
	/**
	 * Map everythin but the password.
	 * @param from
	 * @param to
	 */
	protected void mapRequestDetail(RequestDetail from,RequestDetail to) {
		to.setRequest(from.getRequest());
		to.setBackup(from.getBackup());
		to.setSoftware(from.getSoftware());
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public void deleteRequestDetail(Long id) throws RequestDetailIdNotFound {
		// TODO Auto-generated method stub
		RequestDetail requestDetail = getRequestDetailById(id);
		
		repository.delete(requestDetail);
		
	}

}
