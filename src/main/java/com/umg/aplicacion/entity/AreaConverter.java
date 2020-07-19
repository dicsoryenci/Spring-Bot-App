package com.umg.aplicacion.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.umg.aplicacion.repository.AreaRepository;

@Component
public class AreaConverter implements Converter<String, Area> {
	
	@Autowired
	AreaRepository areaRepository;

    @Override
    public Area convert(String id) {
        return areaRepository.findById(Long.valueOf(id)).orElse(null);
    }
    
}
