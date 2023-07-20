package com.moneyapi.repository.expense;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.moneyapi.model.Expense;
import com.moneyapi.repository.filter.ExpenseFilter;


public class ExpenseRepositoryImpl implements ExpenseRepositoryQuery{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Page<Expense> filter(ExpenseFilter expensefilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Expense> criteria = builder.createQuery(Expense.class);
		Root<Expense> root = criteria.from(Expense.class);
		Predicate[] predicates = createConstraints(expensefilter, builder, root);
		
		criteria.where(predicates);
		
		TypedQuery<Expense> query = manager.createQuery(criteria);
		
		createConstraintsPage(query,pageable);
		return new PageImpl<>(query.getResultList(), pageable, total(expensefilter));
	}
	
	private Predicate[] createConstraints (ExpenseFilter expenseFilter, CriteriaBuilder builder,Root<Expense> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(!ObjectUtils.isEmpty(expenseFilter.getDescription())) {
			predicates.add(builder.like(
					builder.lower(root.get("description")), "%" + expenseFilter.getDescription().toLowerCase() + "%"));
		}
		
		if (!ObjectUtils.isEmpty(expenseFilter.getDueDateMin())) {
			predicates.add(
					builder.greaterThanOrEqualTo(root.get("dueDate"),expenseFilter.getDueDateMin()));
		}
		
		if (!ObjectUtils.isEmpty(expenseFilter.getDueDateMax())) {
			predicates.add(
					builder.lessThanOrEqualTo(root.get("dueDate"), expenseFilter.getDueDateMax()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private void createConstraintsPage(TypedQuery<Expense> query, Pageable pageable) {
		int currentPage = pageable.getPageNumber();
		int totalRegisterByPage = pageable.getPageSize();
		int firstRegisterByPage = currentPage * totalRegisterByPage;
		
		query.setFirstResult(firstRegisterByPage);
		query.setMaxResults(totalRegisterByPage);
	}
	
	private Long total(ExpenseFilter expenseFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Expense> root = criteria.from(Expense.class);
		
		Predicate[] predicates = createConstraints(expenseFilter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}
	 
}
