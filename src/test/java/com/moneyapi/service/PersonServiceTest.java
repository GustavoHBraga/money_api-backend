package com.moneyapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;

import com.moneyapi.model.InformationAdress;
import com.moneyapi.model.Person;
import com.moneyapi.repository.PersonRepository;

public class PersonServiceTest {
	
	@Mock
	private PersonRepository personRepository;
	
	@InjectMocks
	private PersonService personService;
	
	@BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
	
	@Test
	public void testUpdate() {
		List<Person> people = factoryPeople();
		Person personCurrent = people.get(0);
		Person personNew = people.get(1);
		
		when(personRepository.findById(personCurrent.getCod())).thenReturn(Optional.of(personCurrent));
		when(personRepository.save(any(Person.class))).thenReturn(personNew);
		
		Person personUpdate = personService.update(personCurrent.getCod(), personNew);
		assertEquals(personNew, personUpdate);
	}
	
	@Test
	public void testUpdateActive() {
		List<Person> people = factoryPeople();
		Person personCurrent = people.get(0);
		Person personNew = personCurrent;
		personNew.setActive(false);
		
		when(personRepository.findById(personCurrent.getCod())).thenReturn(Optional.of(personCurrent));
		when(personRepository.save(any(Person.class))).thenReturn(personNew);
		
		Person personUpdate = personService.updateActive(personCurrent.getCod(), personNew.getActive());
		assertEquals(personNew, personUpdate);
	}
	
	@Test
	public void testUpdateAddress() {
		List<Person> people = factoryPeople();
		Person personCurrent = people.get(0);
		Person personNew = personCurrent;
		
		InformationAdress address = new InformationAdress();
		address.setAddress("Parana");
		address.setCep("055955");
		address.setCity("OSASCO");
		address.setState("Sao Paulo");
		personNew.setInformationAdress(address);
		
		when(personRepository.findById(personCurrent.getCod())).thenReturn(Optional.of(personCurrent));
		when(personRepository.save(any(Person.class))).thenReturn(personNew);
		
		Person personUpdate = personService.updateAdress(personCurrent.getCod(), personNew.getInformationAdress());
		assertEquals(personNew, personUpdate);
	}
	
	@Test
	public void testSourcePerson() {
		List<Person> people = factoryPeople();
		Person person = people.get(0);
		
		when(personRepository.findById(person.getCod())).thenReturn(Optional.of(person));
		
		Person personFind = personService.sourcePerson(person.getCod());
		assertEquals(person, personFind);
		
	}
	@Test
	public void testSourcePersonEmptyResult() {
		List<Person> people = factoryPeople();
		Person person = people.get(0);
		
		when(personRepository.findById(1L)).thenReturn(Optional.of(person));
		
		assertThrows(EmptyResultDataAccessException.class, () -> personService.sourcePerson(2L));
		
	}
	
	public static List<Person> factoryPeople(){
		
		List<Person> people = new ArrayList<>();
		
		Person person_1 = new Person();
		person_1.setCod(1L);
		person_1.setName("Gustavo");
		person_1.setActive(true);
		InformationAdress address_1 = new InformationAdress();
		address_1.setAddress("Rua rio paranapanema");
		address_1.setCep("06220250");
		address_1.setCity("OSASCO");
		address_1.setState("Sao Paulo");
		person_1.setInformationAdress(address_1);
		
		Person person_2 = new Person();
		person_2.setCod(2L);
		person_2.setName("Claudio");
		person_2.setActive(true);
		InformationAdress address_2 = new InformationAdress();
		address_2.setAddress("Rua rio paranapanema");
		address_2.setCep("06220250");
		address_2.setCity("OSASCO");
		address_2.setState("Sao Paulo");
		person_2.setInformationAdress(address_1);
		
		people.add(person_1);
		people.add(person_2);
		return people;
		
	}
}
