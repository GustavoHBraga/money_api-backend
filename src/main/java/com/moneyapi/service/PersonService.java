package com.moneyapi.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.moneyapi.model.InformationAdress;
import com.moneyapi.model.Person;
import com.moneyapi.repository.PersonRepository;

@Service
public class PersonService {
	
	@Autowired
	private PersonRepository personRepository;
	
	public Person update(Long cod, Person people) {
		
		Person peopleSave = sourcePerson(cod);
		BeanUtils.copyProperties(people, peopleSave,"cod");
		return this.personRepository.save(peopleSave);
	}
	
	public Person updateActive(Long cod, boolean active) {
		
		Person peopleSave = sourcePerson(cod);
		peopleSave.setActive(active);
		return this.personRepository.save(peopleSave);
	}
	
	public Person updateAdress(Long cod, InformationAdress adress) {
			
		Person peopleSave = sourcePerson(cod);
		peopleSave.setInformationAdress(adress);
		return this.personRepository.save(peopleSave);
	}
	
	public Person sourcePerson(Long cod) {
		return this.personRepository.findById(cod)
				.orElseThrow(() -> new EmptyResultDataAccessException(1)); 
	}
}
