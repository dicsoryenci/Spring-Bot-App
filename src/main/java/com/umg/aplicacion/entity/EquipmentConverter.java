package com.umg.aplicacion.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.umg.aplicacion.repository.EquipmentRepository;

@Component
public class EquipmentConverter implements Converter<String, Equipment>  {
	
	@Autowired
	EquipmentRepository equipmentRepository;

    @Override
    public Equipment convert(String id) {
        return equipmentRepository.findById(Long.valueOf(id)).orElse(null);
    }

}
