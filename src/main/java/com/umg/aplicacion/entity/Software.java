package com.umg.aplicacion.entity;

import java.io.Serializable;

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
public class Software implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4376931285298524921L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name="native", strategy="native")
	private Long id;
	
	@Column
	@NotBlank
	private String name;
	
	@Column
	@NotBlank
	private String concurrence;
	
	@Column
	@NotBlank
	private String impact;
	
	@Column
	@NotBlank
	private String provider;
	
	@Column
	@NotBlank
	private String relatedProcess;
	
	@JoinColumn(name="id_Equipment")
	@OneToOne(cascade=CascadeType.MERGE)
	private Equipment equipment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getConcurrence() {
		return concurrence;
	}

	public void setConcurrence(String concurrence) {
		this.concurrence = concurrence;
	}

	public String getImpact() {
		return impact;
	}

	public void setImpact(String impact) {
		this.impact = impact;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getRelatedProcess() {
		return relatedProcess;
	}

	public void setRelatedProcess(String relatedProcess) {
		this.relatedProcess = relatedProcess;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((concurrence == null) ? 0 : concurrence.hashCode());
		result = prime * result + ((equipment == null) ? 0 : equipment.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((impact == null) ? 0 : impact.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((provider == null) ? 0 : provider.hashCode());
		result = prime * result + ((relatedProcess == null) ? 0 : relatedProcess.hashCode());
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
		Software other = (Software) obj;
		if (concurrence == null) {
			if (other.concurrence != null)
				return false;
		} else if (!concurrence.equals(other.concurrence))
			return false;
		if (equipment == null) {
			if (other.equipment != null)
				return false;
		} else if (!equipment.equals(other.equipment))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (impact == null) {
			if (other.impact != null)
				return false;
		} else if (!impact.equals(other.impact))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (provider == null) {
			if (other.provider != null)
				return false;
		} else if (!provider.equals(other.provider))
			return false;
		if (relatedProcess == null) {
			if (other.relatedProcess != null)
				return false;
		} else if (!relatedProcess.equals(other.relatedProcess))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Software [id=" + id + ", name=" + name + ", concurrence=" + concurrence + ", impact=" + impact
				+ ", provider=" + provider + ", relatedProcess=" + relatedProcess + ", equipment=" + equipment + "]";
	}

}
