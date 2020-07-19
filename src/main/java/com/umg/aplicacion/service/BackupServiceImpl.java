package com.umg.aplicacion.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.umg.aplicacion.Exception.BackupDescriptionOrIdNotFound;
import com.umg.aplicacion.Exception.CustomeFieldValidationException;
import com.umg.aplicacion.entity.Backup;
import com.umg.aplicacion.repository.BackupRepository;

@Service
public class BackupServiceImpl implements BackupService {
	@Autowired
	BackupRepository repository;
	
	@Override
	public Iterable<Backup> getAllBackups() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}
	
	private boolean checkBackupDescriptionAvailable(Backup backup) throws Exception {
		Optional<Backup> backupFound = repository.findBybackupDescription(backup.getBackupDescription());
		if(backupFound.isPresent()) {
			throw new CustomeFieldValidationException("Description de Backup no disponible", "name");			
		} 
		return true;
	}

	@Override
	public Backup createBackup(Backup backup) throws Exception {
		if(checkBackupDescriptionAvailable(backup)) {
			
			backup = repository.save(backup);
		}
		return backup;
	}

	@Override
	public Backup getBackupById(Long id) throws BackupDescriptionOrIdNotFound {
		// TODO Auto-generated method stub
		//Retorna un optional o sino encuentra lanza una exception
		return repository.findById(id).orElseThrow(() -> new BackupDescriptionOrIdNotFound("El Id del backup no existe."));
	}

	@Override
	public Backup updateBackup(Backup fromBackup) throws Exception {
		// TODO Auto-generated method stub
		Backup toBackup = getBackupById(fromBackup.getId());
		mapBackup(fromBackup, toBackup);
		//Utiliza el mismo método para actualizar, consulta si existe y luego hace le merge, sino realiza el salvado
		return repository.save(toBackup);
	}
	
	
	/**
	 * Map everythin but the password.
	 * @param from
	 * @param to
	 */
	protected void mapBackup(Backup from,Backup to) {
		to.setBackupDescription(from.getBackupDescription());;
		to.setBackupLevel(from.getBackupLevel());
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public void deleteBackup(Long id) throws BackupDescriptionOrIdNotFound {
		// TODO Auto-generated method stub
		Backup backup = getBackupById(id);
		
		repository.delete(backup);
		
	}

}
