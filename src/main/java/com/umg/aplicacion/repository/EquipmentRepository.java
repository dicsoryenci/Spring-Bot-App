package com.umg.aplicacion.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.umg.aplicacion.entity.Equipment;;

@Repository
public interface EquipmentRepository extends CrudRepository<Equipment, Long> {
	
	public Optional<Equipment> findByip(String ip);

}
