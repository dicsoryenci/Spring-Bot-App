package com.umg.aplicacion.service;

import com.umg.aplicacion.Exception.UsernameOrIdNotFound;
import com.umg.aplicacion.dto.ChangePasswordForm;
import com.umg.aplicacion.entity.User;

public interface UserService {
	
	public Iterable<User> getAllUsers();

	public User createUser(User user) throws Exception;
	
	public User getUserById(Long id) throws Exception;
	
	public User updateUser(User user) throws Exception;
	
	public void deleteUser(Long id) throws UsernameOrIdNotFound;
	
	public User changePassword(ChangePasswordForm form) throws Exception;
}
