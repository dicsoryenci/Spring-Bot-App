package com.umg.aplicacion.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.umg.aplicacion.repository.BackupRepository;

@Component
public class BackupConverter implements Converter<String, Backup>  {
	
	@Autowired
	BackupRepository backupRepository;

    @Override
    public Backup convert(String id) {
        return backupRepository.findById(Long.valueOf(id)).orElse(null);
    }
}
