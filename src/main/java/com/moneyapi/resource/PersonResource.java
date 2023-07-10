package com.moneyapi.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.moneyapi.event.ResourceCreatedEvent;
import com.moneyapi.model.InformationAdress;
import com.moneyapi.model.Person;
import com.moneyapi.repository.PersonRepository;
import com.moneyapi.service.PersonService;


@RestController
@RequestMapping("/person")
public class PersonResource {

	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private PersonService personService;
	
	@GetMapping
	public ResponseEntity<?> listAll(){
		List<Person> person = personRepository.findAll();
		return !person.isEmpty() ? ResponseEntity.ok(person) : ResponseEntity.ok().build();
	}
	
	@GetMapping("/{cod}")
	public ResponseEntity<Person> findOne(@PathVariable Long cod){
		Person personFilter = personRepository.findById(cod).orElse(null);
		return personFilter != null ? ResponseEntity.ok(personFilter) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Person> create(@Valid @RequestBody Person person, HttpServletResponse response){
		Person personSave = personRepository.save(person);
		
		// Set Location in Header ex: Location = "http://serverIP:port/person/15"
		publisher.publishEvent(new ResourceCreatedEvent(this, response, personSave.getCod()));
		
		// and finally return Object in Json status code 201(created)
		return ResponseEntity.status(HttpStatus.CREATED).body(personSave);
	}
	
	@DeleteMapping("/{cod}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteperson(@PathVariable Long cod) {
		personRepository.deleteById(cod);
	}
	
	@PutMapping("/{cod}")
	public ResponseEntity<Person> update(@PathVariable Long cod, @Valid @RequestBody Person person ){
		Person personSave = personService.update(cod, person);
		return ResponseEntity.ok(personSave);
	}
	
	@PutMapping("/{cod}/active")
	public ResponseEntity<Person> updateActive(@PathVariable Long cod,@RequestBody boolean active ){
		Person personSave = personService.updateActive(cod, active);
		return ResponseEntity.ok(personSave);
	}
	
	@PutMapping("/{cod}/adress")
	public ResponseEntity<Person> updateAdress(@PathVariable Long cod, @Valid @RequestBody InformationAdress adress ){
		Person personSave = personService.updateAdress(cod, adress);
		return ResponseEntity.ok(personSave);
	}
}
