package com.umg.aplicacion.service;

import com.umg.aplicacion.Exception.BackupDescriptionOrIdNotFound;
import com.umg.aplicacion.entity.Backup;

public interface BackupService {

	public Iterable<Backup> getAllBackups();

	public Backup createBackup(Backup backup) throws Exception;
	
	public Backup getBackupById(Long id) throws Exception;
	
	public Backup updateBackup(Backup backup) throws Exception;
	
	public void deleteBackup(Long id) throws BackupDescriptionOrIdNotFound;

}
