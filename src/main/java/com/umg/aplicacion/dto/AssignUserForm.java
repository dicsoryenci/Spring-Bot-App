package com.umg.aplicacion.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.umg.aplicacion.entity.User;

public class AssignUserForm {
	@NotNull 
	Long id;
	
	//@NotBlank(message="Debe seleccionar un operator user")
	private User operatorUser;
	
	public AssignUserForm() { }
	public AssignUserForm(Long id) {this.id = id;}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getOperatorUser() {
		return operatorUser;
	}

	public void setOperatorUser(User operatorUser) {
		this.operatorUser = operatorUser;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((operatorUser == null) ? 0 : operatorUser.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AssignUserForm other = (AssignUserForm) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (operatorUser == null) {
			if (other.operatorUser != null)
				return false;
		} else if (!operatorUser.equals(other.operatorUser))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AssignUserForm [id=" + id + ", operatorUser=" + operatorUser + "]";
	}

}
