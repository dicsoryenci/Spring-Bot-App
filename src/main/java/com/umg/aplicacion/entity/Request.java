package com.umg.aplicacion.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Request implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1733485224288726760L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name="native", strategy="native")
	private Long id;
	
	@Column
	private char status;
	
	@Column
	private Date deliveryDate;
	
	@Column
	private Date applicationDate;
	
	@Column
	private Date assignmentDate;
	
	@JoinColumn(name="id_operator_user")
	@OneToOne(cascade=CascadeType.MERGE)
	private User operatorUser;
	
	@JoinColumn(name="id_user_request")
	@OneToOne(cascade=CascadeType.MERGE)
	private User userRequest;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Date getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	public Date getAssignmentDate() {
		return assignmentDate;
	}

	public void setAssignmentDate(Date assignmentDate) {
		this.assignmentDate = assignmentDate;
	}

	public User getOperatorUser() {
		return operatorUser;
	}

	public void setOperatorUser(User operatorUser) {
		this.operatorUser = operatorUser;
	}

	public User getUserRequest() {
		return userRequest;
	}

	public void setUserRequest(User userRequest) {
		this.userRequest = userRequest;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((applicationDate == null) ? 0 : applicationDate.hashCode());
		result = prime * result + ((assignmentDate == null) ? 0 : assignmentDate.hashCode());
		result = prime * result + ((deliveryDate == null) ? 0 : deliveryDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((operatorUser == null) ? 0 : operatorUser.hashCode());
		result = prime * result + status;
		result = prime * result + ((userRequest == null) ? 0 : userRequest.hashCode());
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
		Request other = (Request) obj;
		if (applicationDate == null) {
			if (other.applicationDate != null)
				return false;
		} else if (!applicationDate.equals(other.applicationDate))
			return false;
		if (assignmentDate == null) {
			if (other.assignmentDate != null)
				return false;
		} else if (!assignmentDate.equals(other.assignmentDate))
			return false;
		if (deliveryDate == null) {
			if (other.deliveryDate != null)
				return false;
		} else if (!deliveryDate.equals(other.deliveryDate))
			return false;
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
		if (status != other.status)
			return false;
		if (userRequest == null) {
			if (other.userRequest != null)
				return false;
		} else if (!userRequest.equals(other.userRequest))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Request [id=" + id + ", status=" + status + ", deliveryDate=" + deliveryDate + ", applicationDate="
				+ applicationDate + ", assignmentDate=" + assignmentDate + ", operatorUser=" + operatorUser
				+ ", userRequest=" + userRequest + "]";
	}

}
