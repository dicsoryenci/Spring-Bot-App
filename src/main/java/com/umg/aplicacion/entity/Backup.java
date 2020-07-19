package com.umg.aplicacion.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Backup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7056063903230297711L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name="native", strategy="native")
	private Long id;
	
	@Column
	@NotBlank
	private String backupDescription;
	
	@Column
	@NotBlank
	private String backupLevel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBackupDescription() {
		return backupDescription;
	}

	public void setBackupDescription(String backupDescription) {
		this.backupDescription = backupDescription;
	}

	public String getBackupLevel() {
		return backupLevel;
	}

	public void setBackupLevel(String backupLevel) {
		this.backupLevel = backupLevel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((backupDescription == null) ? 0 : backupDescription.hashCode());
		result = prime * result + ((backupLevel == null) ? 0 : backupLevel.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Backup other = (Backup) obj;
		if (backupDescription == null) {
			if (other.backupDescription != null)
				return false;
		} else if (!backupDescription.equals(other.backupDescription))
			return false;
		if (backupLevel == null) {
			if (other.backupLevel != null)
				return false;
		} else if (!backupLevel.equals(other.backupLevel))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Backup [id=" + id + ", backupDescription=" + backupDescription + ", backupLevel=" + backupLevel + "]";
	}
	
}
