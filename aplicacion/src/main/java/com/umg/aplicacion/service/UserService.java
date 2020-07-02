package com.umg.aplicacion.service;

import com.umg.aplicacion.entity.User;

public interface UserService {
	
	public Iterable<User> getAllUsers();

	public User createUser(User user) throws Exception;

}
