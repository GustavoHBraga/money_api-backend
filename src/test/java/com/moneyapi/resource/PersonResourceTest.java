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

import com.moneyapi.model.Category;
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
		people.add(person_1);
		
		Person person_2 = new Person();
		person_2.setCod(2L);
		person_2.setName("Claudio");
		person_2.setActive(false);
		
		InformationAdress address_2 = new InformationAdress();
		address_2.setAddress("Rua rio paranapanema");
		address_2.setCep("06220250");
		address_2.setCity("OSASCO");
		address_2.setState("Sao Paulo");
		
		person_2.setInformationAdress(address_2);
		people.add(person_2);
		
		when(personRepository.findAll()).thenReturn(people);
		ResponseEntity<?> response = personResource.listAll();
		
		verify(personRepository, times(1)).findAll();
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(people, response.getBody());		
	}
	
	@Test
	public void testFindBycod() {
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
		
	
		when(personRepository.findById(1L)).thenReturn(Optional.of(person_1));
		
		ResponseEntity<?> response = personResource.findOne(1L);
		
		verify(personRepository,times(1)).findById(1L);
		
		assertEquals(person_1,response.getBody());
	}
	
	@Test
	public void testCreate() {
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
		
		when(personRepository.save(any(Person.class))).thenReturn(person_1);
		ResponseEntity<?> response = personResource.create(person_1, mock(HttpServletResponse.class));
		verify(personRepository,times(1)).save(person_1);
		
		assertEquals(HttpStatus.CREATED,response.getStatusCode());
		assertEquals(person_1,response.getBody());
		
	}
	
	@Test
	public void testDeletePerson() {
		personResource.deleteperson(1L);
		verify(personRepository, times(1)).deleteById(1L);
		
	}
}
