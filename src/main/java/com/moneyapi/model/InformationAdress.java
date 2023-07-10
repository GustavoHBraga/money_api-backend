package com.moneyapi.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;


@Embeddable
public class InformationAdress {

	@NotNull
	private String address;
	
	@NotNull
	private int number;
	
	private String complement;
	
	@NotNull
	private String district;
	
	@NotNull
	private String cep;
	
	@NotNull
	private String city;
	
	@NotNull
	private String state;

	public String getAddress() {
		return address;
	}

	public void setAddress(String adress) {
		this.address = adress;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
