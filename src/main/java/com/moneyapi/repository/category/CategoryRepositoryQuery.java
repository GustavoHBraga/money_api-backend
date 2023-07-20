package com.moneyapi.repository.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.moneyapi.model.Category;
import com.moneyapi.model.Expense;
import com.moneyapi.repository.filter.CategoryFilter;

public interface CategoryRepositoryQuery {

	public Page<Category> filter(CategoryFilter categoryFilter, Pageable pageable);
}
