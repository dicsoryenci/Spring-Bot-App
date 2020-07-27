package com.umg.aplicacion.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.umg.aplicacion.repository.UserRepository;

@Component
public class UserConverter implements Converter<String, User> {

	@Autowired
	UserRepository userRepository;

    @Override
    public User convert(String id) {
        return userRepository.findById(Long.valueOf(id)).orElse(null);
    }
}
