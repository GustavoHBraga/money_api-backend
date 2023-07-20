package com.moneyapi.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moneyapi.event.ResourceCreatedEvent;
import com.moneyapi.exceptionHandler.moneyApiExceptionHandler.Error;
import com.moneyapi.model.Expense;
import com.moneyapi.repository.ExpenseRepository;
import com.moneyapi.repository.filter.ExpenseFilter;
import com.moneyapi.service.ExpenseService;
import com.moneyapi.service.exception.PersonNotActiveOrNoExists;

@RestController
@RequestMapping("/expense")
public class ExpenseResource {

	@Autowired
	private ExpenseRepository expenseRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private ExpenseService expenseService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	public Page<Expense> filter(ExpenseFilter expenseFilter, Pageable pageable){
		return expenseRepository.filter(expenseFilter, pageable);
	}
	
	@GetMapping("/{cod}")
	public ResponseEntity<Expense> findbycod(@PathVariable Long cod){
		Expense expenseFilter = expenseRepository.findById(cod).orElse(null);
		return expenseFilter != null ? ResponseEntity.ok(expenseFilter) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Expense> createExpense(@Valid @RequestBody Expense expense, HttpServletResponse response){
		Expense expenseSave = expenseService.save(expense);
		publisher.publishEvent(new ResourceCreatedEvent(this, response, expense.getCod()));
		return ResponseEntity.status(HttpStatus.CREATED).body(expenseSave);
	}
	
	@ExceptionHandler({PersonNotActiveOrNoExists.class})
	public ResponseEntity<Object> PersonNotActiveOrNoExists(PersonNotActiveOrNoExists ex) {
		
		String userMessage = messageSource.getMessage("person-notActive_or_notExist", null, LocaleContextHolder.getLocale());
		String devMessage = ex.toString();
		
		List<Error> erros = Arrays.asList(new Error(userMessage, devMessage));
		return ResponseEntity.badRequest().body(erros);
	}
}
