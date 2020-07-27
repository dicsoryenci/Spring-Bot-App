package com.umg.aplicacion.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.umg.aplicacion.entity.RequestDetail;
import com.umg.aplicacion.entity.Software;

@Repository
public interface RequestDetailRepository extends CrudRepository<RequestDetail, Long> {
	public Optional<RequestDetail> findBySoftware(Software software);
}
