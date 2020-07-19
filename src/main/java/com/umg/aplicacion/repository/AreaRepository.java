package com.umg.aplicacion.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.umg.aplicacion.entity.Area;

@Repository
public interface AreaRepository extends CrudRepository<Area, Long> {
	
	public Optional<Area> findByname(String name);
}
