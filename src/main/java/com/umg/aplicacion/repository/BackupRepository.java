package com.umg.aplicacion.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.umg.aplicacion.entity.Backup;


public interface BackupRepository extends CrudRepository<Backup, Long> {
	public Optional<Backup> findBybackupDescription(String description);
}
