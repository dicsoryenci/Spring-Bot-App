package com.umg.aplicacion.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.umg.aplicacion.entity.Request;

@Repository
public interface RequestRepository extends CrudRepository<Request, Long>{

	public Optional<Request> findBydeliveryDate(Date deliveryDate);
}
