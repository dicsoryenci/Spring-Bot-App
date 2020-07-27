package com.umg.aplicacion.service;

import com.umg.aplicacion.Exception.RequestDetailIdNotFound;
import com.umg.aplicacion.entity.RequestDetail;

public interface RequestDetailService {
	public Iterable<RequestDetail> getAllRequestDetails();

	public RequestDetail createRequestDetail(RequestDetail requestDetail) throws Exception;
	
	public RequestDetail getRequestDetailById(Long id) throws Exception;
	
	public RequestDetail updateRequestDetail(RequestDetail requestDetail) throws Exception;
	
	public void deleteRequestDetail(Long id) throws RequestDetailIdNotFound;
}
