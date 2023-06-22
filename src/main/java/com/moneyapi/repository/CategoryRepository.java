package com.moneyapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moneyapi.model.Category;
/**
 * @author Gustavo
 * @version 1.0
 */
public interface CategoryRepository extends JpaRepository<Category, Long>{

}
