package com.moneyapi.resource;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.moneyapi.model.People;
import com.moneyapi.repository.PeopleRepository;


@RestController
@RequestMapping("/people")
public class PeopleResource {

	@Autowired
	private PeopleRepository peoplerepository;
	
	@GetMapping
	public ResponseEntity<?> listAll(){
		List<People> people = peoplerepository.findAll();
		return !people.isEmpty() ? ResponseEntity.ok(people) : ResponseEntity.ok().build();
	}
	
	@GetMapping("/{cod}")
	public ResponseEntity<People> findOne(@PathVariable Long cod){
		People peopleFilter = peoplerepository.findById(cod).orElse(null);
		return peopleFilter != null ? ResponseEntity.ok(peopleFilter) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<People> create(@Valid @RequestBody People people, HttpServletResponse response){
		People peopleSave = peoplerepository.save(people);
		
		// Used in redirection, or when a new resource has been created, receive o current request + new cod generated ex "http://{ipserver}/people/ + 2"
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{cod}")
				.buildAndExpand(peopleSave.getCod())
				.toUri();
		
		// Set Location in Header ex: Location = "http://serverIP:port/people/15"
		response.setHeader("Location", uri.toASCIIString());
		
		// and finally return Object in Json status code 201(created)
		return ResponseEntity.created(uri).body(peopleSave);
	}
	
}
