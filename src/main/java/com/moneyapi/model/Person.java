package com.moneyapi.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "person")
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cod;
	
	@NotNull
	@Size(min = 3, max = 50)
	private String name;
	
	@NotNull
	private Boolean active;
	
	@Embedded
	private InformationAdress informationAdress;

	public Long getCod() {
		return cod;
	}

	public void setCod(Long cod) {
		this.cod = cod;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public InformationAdress getInformationAdress() {
		return informationAdress;
	}
	
	
	@JsonIgnore 
	@Transient //hibernate não vai utilizar esse metodo
	public boolean isNotActive() {
		return !this.active;
	}
	public void setInformationAdress(InformationAdress informationAdress) {
		this.informationAdress = informationAdress;
	}

	@Override
	public String toString() {
		return "Person [cod=" + cod + ", name=" + name + ", isActive=" + active + ", informationAdress="
				+ informationAdress + "]";
	}
	
}
