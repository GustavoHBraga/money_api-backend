package com.moneyapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moneyapi.model.Expense;
import com.moneyapi.repository.expense.ExpenseRepositoryQuery;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long>, ExpenseRepositoryQuery{

}
