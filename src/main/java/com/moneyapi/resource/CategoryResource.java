package com.moneyapi.resource;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.moneyapi.event.ResourceCreatedEvent;
import com.moneyapi.model.Category;
import com.moneyapi.repository.CategoryRepository;
import com.moneyapi.repository.filter.CategoryFilter;


@RestController
@RequestMapping("/category")
public class CategoryResource {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public Page<Category> filter(CategoryFilter categoryFilter, Pageable pageable){
		return categoryRepository.filter(categoryFilter, pageable);
	}
	
	@GetMapping("/{cod}")
	public ResponseEntity<Category> findByCod(@PathVariable Long cod) {
		Category categoryFilter = categoryRepository.findById(cod).orElse(null);
		return categoryFilter != null ? ResponseEntity.ok(categoryFilter) : ResponseEntity.notFound().build() ;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Category> create(@Valid @RequestBody Category category,HttpServletResponse response) {
		
		Category categorySave = categoryRepository.save(category);
		
		// Set Location in Header ex: Location = "http://serverIP:port/people/15"
		publisher.publishEvent(new ResourceCreatedEvent(this, response, categorySave.getCod()));
		
		// and finally return Object in Json status code 201(created)
		return ResponseEntity.status(HttpStatus.CREATED).body(categorySave);
	}
	
	@DeleteMapping("/{cod}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCategory(@PathVariable Long cod) {
		
		categoryRepository.deleteById(cod);
	}
	
}
