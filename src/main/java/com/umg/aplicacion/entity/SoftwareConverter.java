package com.umg.aplicacion.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.umg.aplicacion.repository.SoftwareRepository;

@Component
public class SoftwareConverter implements Converter<String, Software> {
	
	@Autowired
	SoftwareRepository softwareRepository;

    @Override
    public Software convert(String id) {
        return softwareRepository.findById(Long.valueOf(id)).orElse(null);
    }
}
