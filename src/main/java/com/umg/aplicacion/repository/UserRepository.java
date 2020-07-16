package com.umg.aplicacion.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.umg.aplicacion.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	//public Set<User> findByUsername(String username);
	
	public Optional<User> findByUsername(String username);

}
