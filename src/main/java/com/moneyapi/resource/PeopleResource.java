package com.moneyapi.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.moneyapi.event.ResourceCreatedEvent;
import com.moneyapi.model.People;
import com.moneyapi.repository.PeopleRepository;


@RestController
@RequestMapping("/people")
public class PeopleResource {

	@Autowired
	private PeopleRepository peopleRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public ResponseEntity<?> listAll(){
		List<People> people = peopleRepository.findAll();
		return !people.isEmpty() ? ResponseEntity.ok(people) : ResponseEntity.ok().build();
	}
	
	@GetMapping("/{cod}")
	public ResponseEntity<People> findOne(@PathVariable Long cod){
		People peopleFilter = peopleRepository.findById(cod).orElse(null);
		return peopleFilter != null ? ResponseEntity.ok(peopleFilter) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<People> create(@Valid @RequestBody People people, HttpServletResponse response){
		People peopleSave = peopleRepository.save(people);
		
		// Set Location in Header ex: Location = "http://serverIP:port/people/15"
		publisher.publishEvent(new ResourceCreatedEvent(this, response, peopleSave.getCod()));
		
		// and finally return Object in Json status code 201(created)
		return ResponseEntity.status(HttpStatus.CREATED).body(peopleSave);
	}
	
}
