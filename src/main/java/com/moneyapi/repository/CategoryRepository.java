package com.moneyapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moneyapi.model.Category;
import com.moneyapi.repository.category.CategoryRepositoryQuery;
/**
 * @author Gustavo
 * @version 1.0
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryQuery{

}
