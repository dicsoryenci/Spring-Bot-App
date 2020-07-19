package com.umg.aplicacion.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.umg.aplicacion.entity.Equipment;
import com.umg.aplicacion.entity.Software;

@Repository
public interface SoftwareRepository extends CrudRepository<Software, Long> {
	
	public Optional<Software> findByname(String name);
}
