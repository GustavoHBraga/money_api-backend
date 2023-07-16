package com.moneyapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moneyapi.model.Expense;
import com.moneyapi.model.Person;
import com.moneyapi.repository.ExpenseRepository;
import com.moneyapi.repository.PersonRepository;
import com.moneyapi.service.exception.PersonNotActiveOrNoExists;

@Service
public class ExpenseService {
	
	@Autowired
	private ExpenseRepository expenseRepository;
	
	@Autowired
	private PersonRepository personRepository;
	
	public Expense save(Expense expense) {
		
		// check if person exists or not active
		Person persoCheck = personRepository.findById(expense.getPerson().getCod()).orElse(null);
		
		if(persoCheck == null || persoCheck.isNotActive()) {
			throw new PersonNotActiveOrNoExists();
		}
		
		return this.expenseRepository.save(expense);
		
	}
}
