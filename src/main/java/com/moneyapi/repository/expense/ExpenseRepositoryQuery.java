package com.moneyapi.repository.expense;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.moneyapi.model.Expense;
import com.moneyapi.repository.filter.ExpenseFilter;

public interface ExpenseRepositoryQuery {
	
	public Page<Expense> filter(ExpenseFilter expensefilter, Pageable pageable);

}
