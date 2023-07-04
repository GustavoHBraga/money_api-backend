package com.moneyapi.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "people")
public class People {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cod;
	
	@NotNull
	@Size(min = 3, max = 50)
	private String name;
	
	@NotNull
	private Boolean isActive;
	
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public InformationAdress getInformationAdress() {
		return informationAdress;
	}

	public void setInformationAdress(InformationAdress informationAdress) {
		this.informationAdress = informationAdress;
	}

	@Override
	public String toString() {
		return "People [cod=" + cod + ", name=" + name + ", isActive=" + isActive + ", informationAdress="
				+ informationAdress + "]";
	}
	
}
