package com.umg.aplicacion.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.umg.aplicacion.Exception.CustomeFieldValidationException;
import com.umg.aplicacion.Exception.UsernameOrIdNotFound;
import com.umg.aplicacion.dto.ChangePasswordForm;
import com.umg.aplicacion.entity.Role;
import com.umg.aplicacion.entity.User;
import com.umg.aplicacion.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository repository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public Iterable<User> getAllUsers() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}
	
	@Override
	public Iterable<User> getAllUsersByRoles(Role role) {
		// TODO Auto-generated method stub
		return repository.findByRoles(role);
	}
	
	private boolean checkUsernameAvailable(User user) throws Exception {
		Optional<User> userFound = repository.findByUsername(user.getUsername());
		if(userFound.isPresent()) {
			throw new CustomeFieldValidationException("Username no disponible", "username");			
		} 
		return true;
	}
	
	private boolean checkPasswordValid(User user) throws Exception {
		if(user.getConfirmPassword() == null || user.getConfirmPassword().isEmpty()) {
			throw new CustomeFieldValidationException("Confirm Password es Obligatorio", "confirmPassword");
		}
		
		if(!user.getPassword().equals(user.getConfirmPassword())) {
			throw new CustomeFieldValidationException("Password y Confirm Password no son iguales", "password");
		}
		return true;
	}

	@Override
	public User createUser(User user) throws Exception {
		if(checkUsernameAvailable(user) && checkPasswordValid(user)) {
			
			String encodePassword = bCryptPasswordEncoder.encode(user.getPassword());
			user.setPassword(encodePassword);
			
			user = repository.save(user);
		}
		return user;
	}

	@Override
	public User getUserById(Long id) throws UsernameOrIdNotFound {
		// TODO Auto-generated method stub
		//Retorna un optional o sino encuentra lanza una exception
		return repository.findById(id).orElseThrow(() -> new UsernameOrIdNotFound("El Id del usuario no existe."));
	}

	@Override
	public User updateUser(User fromUser) throws Exception {
		// TODO Auto-generated method stub
		User toUser = getUserById(fromUser.getId());
		mapUser(fromUser, toUser);
		//Utiliza el mismo método para actualizar, consulta si existe y luego hace le merge, sino realiza el salvado
		return repository.save(toUser);
	}
	
	
	/**
	 * Map everythin but the password.
	 * @param from
	 * @param to
	 */
	protected void mapUser(User from,User to) {
		to.setUsername(from.getUsername());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setEmail(from.getEmail());
		to.setRoles(from.getRoles());
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public void deleteUser(Long id) throws UsernameOrIdNotFound {
		// TODO Auto-generated method stub
		User user = getUserById(id);
		
		repository.delete(user);
		
	}

	@Override
	public User changePassword(ChangePasswordForm form) throws Exception {
		// TODO Auto-generated method stub
		User user = getUserById(form.getId());
		
		if(!isLoggedUserADMIN() && !user.getPassword().contentEquals(form.getCurrentPassword())) {
			throw new Exception("Current Password invalido.");
		}
		
		if(user.getPassword().equals(form.getNewPassword())) {
			throw new Exception("Nuevo debe de ser diferente al password actual.");
		}
		
		if(!form.getNewPassword().equals(form.getConfirmPassword())) {
			throw new Exception("Nuevo Password y Current Password no coinciden.");
		}
		
		String encodePassword = bCryptPasswordEncoder.encode(form.getNewPassword());
		user.setPassword(encodePassword);
		return repository.save(user);
		
	}
	
	private boolean isLoggedUserADMIN() {
		//Obtener el usuario logeado
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		UserDetails loggedUser = null;
		Object roles = null;

		//Verificar que ese objeto traido de sesion es el usuario
		if (principal instanceof UserDetails) {
			loggedUser = (UserDetails) principal;

			roles = loggedUser.getAuthorities().stream()
					.filter(x -> "ROLE_ADMIN".equals(x.getAuthority())).findFirst()
					.orElse(null); 
		}
		return roles != null ? true : false;
	}
	
	public User getLoggedUser() throws Exception {
		//Obtener el usuario logeado
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		UserDetails loggedUser = null;

		//Verificar que ese objeto traido de sesion es el usuario
		if (principal instanceof UserDetails) {
			loggedUser = (UserDetails) principal;
		}
		
		User myUser = repository
				.findByUsername(loggedUser.getUsername()).orElseThrow(() -> new Exception("Problemas obteniendo usuario de sesion."));
		
		return myUser;
	}
	
	

}
