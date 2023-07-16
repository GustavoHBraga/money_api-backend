package com.moneyapi.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.moneyapi.model.InformationAdress;
import com.moneyapi.model.Person;
import com.moneyapi.repository.PersonRepository;
import com.moneyapi.service.PersonService;

public class PersonResourceTest {

	@Mock
	private PersonRepository personRepository;
	
	@Mock
	private ApplicationEventPublisher publisher;
	
	@Mock
	private PersonService personService;
	
	@InjectMocks
    private PersonResource personResource;
	
	@BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
	
	@Test
	public void testListAll() {
		List<Person> people = factoryPeople();
		
		when(personRepository.findAll()).thenReturn(people);
		ResponseEntity<?> response = personResource.listAll();
		verify(personRepository, times(1)).findAll();
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(people, response.getBody());		
	}
	
	@Test
	public void testListAllEmpty() {
		
		when(personRepository.findAll()).thenReturn(new ArrayList<>());
		ResponseEntity<?> response = personResource.listAll();
		verify(personRepository, times(1)).findAll();
		assertEquals(HttpStatus.OK, response.getStatusCode());
				
	}
	
	@Test
	public void testFindBycod() {
		List<Person> people = factoryPeople();
		Person person = people.get(0);
		
		when(personRepository.findById(1L)).thenReturn(Optional.of(person));
		ResponseEntity<?> response = personResource.findOne(1L);
		verify(personRepository,times(1)).findById(1L);
		
		assertEquals(person,response.getBody());
	}
	
	@Test
	public void testFindBycodNofound() {
		List<Person> people = factoryPeople();
		Person person = people.get(0);
		
		when(personRepository.findById(1L)).thenReturn(Optional.of(person));
		
		ResponseEntity<?> response = personResource.findOne(2L);
		
		verify(personRepository,times(1)).findById(2L);
		
		assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
	}
	
	@Test
	public void testCreate() {
		List<Person> people = factoryPeople();
		Person personNew = people.get(0);
		
		when(personRepository.save(any(Person.class))).thenReturn(personNew);
		ResponseEntity<?> response = personResource.create(personNew, mock(HttpServletResponse.class));
		verify(personRepository,times(1)).save(personNew);
		
		assertEquals(HttpStatus.CREATED,response.getStatusCode());
		assertEquals(personNew,response.getBody());
		
	}
	
	@Test
	public void testUpdate() {
		List<Person> people = factoryPeople();
		Person person = people.get(0);
		Person personUpdate = people.get(1);
		
		when(personService.update(person.getCod(),personUpdate)).thenReturn(personUpdate);
		ResponseEntity<?> response = personResource.update(person.getCod(),personUpdate);
		verify(personService,times(1)).update(person.getCod(),personUpdate);
		
		assertEquals(HttpStatus.OK,response.getStatusCode());
		assertEquals(personUpdate,response.getBody());
		
	}
	
	@Test
	public void testUpdateActive() {
		
		List<Person> people = factoryPeople();
		Person person = people.get(0);
		Person personUpdate = people.get(1);
		boolean newActive = false;
		
		when(personService.updateActive(person.getCod(),newActive)).thenReturn(personUpdate);
		ResponseEntity<?> response = personResource.updateActive(person.getCod(),newActive);
		verify(personService,times(1)).updateActive(person.getCod(), newActive);
		
		assertEquals(HttpStatus.OK,response.getStatusCode());
		assertEquals(personUpdate,response.getBody());
		
	}
	@Test
	public void testUpdateAddress() {
		
		List<Person> people = factoryPeople();
		Person person = people.get(0);
		Person personUpdate = people.get(1);
		InformationAdress newAddress = people.get(1).getInformationAdress();
		
		when(personService.updateAddress(person.getCod(),newAddress)).thenReturn(personUpdate);
		ResponseEntity<?> response = personResource.updateAdress(person.getCod(),newAddress);
		verify(personService,times(1)).updateAddress(person.getCod(), newAddress);
		
		assertEquals(HttpStatus.OK,response.getStatusCode());
		assertEquals(personUpdate,response.getBody());
		
	}
	
	@Test
	public void testDeletePerson() {
		personResource.deleteperson(1L);
		verify(personRepository, times(1)).deleteById(1L);
		
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
		person_2.setCod(1L);
		person_2.setName("Gustavo");
		person_2.setActive(false); 
		InformationAdress address_2 = new InformationAdress();
		address_2.setAddress("Bahia dos alvaros");
		address_2.setCep("06220250");
		address_2.setCity("Piaui");
		address_2.setState("Piaui");
		person_2.setInformationAdress(address_2);
		
		people.add(person_1);
		people.add(person_2);
		
		return people;
		
	}
	
}
