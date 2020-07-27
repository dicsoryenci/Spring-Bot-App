package com.umg.aplicacion.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class RequestDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5807397705775129276L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name="native", strategy="native")
	private Long id;
	
	@JoinColumn(name="id_request")
	@OneToOne(cascade=CascadeType.MERGE)
	private Request request;
	
	@JoinColumn(name="id_backup")
	@OneToOne(cascade=CascadeType.MERGE)
	private Backup backup;
	
	@JoinColumn(name="id_software")
	@OneToOne(cascade=CascadeType.MERGE)
	private Software software;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public Backup getBackup() {
		return backup;
	}

	public void setBackup(Backup backup) {
		this.backup = backup;
	}

	public Software getSoftware() {
		return software;
	}

	public void setSoftware(Software software) {
		this.software = software;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((backup == null) ? 0 : backup.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((request == null) ? 0 : request.hashCode());
		result = prime * result + ((software == null) ? 0 : software.hashCode());
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
		RequestDetail other = (RequestDetail) obj;
		if (backup == null) {
			if (other.backup != null)
				return false;
		} else if (!backup.equals(other.backup))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (request == null) {
			if (other.request != null)
				return false;
		} else if (!request.equals(other.request))
			return false;
		if (software == null) {
			if (other.software != null)
				return false;
		} else if (!software.equals(other.software))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RequestDatail [id=" + id + ", request=" + request + ", backup=" + backup + ", software=" + software
				+ "]";
	}

}
