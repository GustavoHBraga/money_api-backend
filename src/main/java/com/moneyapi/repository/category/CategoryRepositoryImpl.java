package com.moneyapi.repository.category;

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

import com.moneyapi.model.Category;
import com.moneyapi.repository.filter.CategoryFilter;

public class CategoryRepositoryImpl implements CategoryRepositoryQuery {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Page<Category> filter(CategoryFilter categoryFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Category> criteria = builder.createQuery(Category.class);
		Root<Category> root = criteria.from(Category.class);
		Predicate[] predicates = createConstraints(categoryFilter, builder, root);
		
		criteria.where(predicates);
		
		TypedQuery<Category> query = manager.createQuery(criteria);
		
		createConstraintsPage(query,pageable);
		return new PageImpl<>(query.getResultList(), pageable, total(categoryFilter));
	}
	
	private Predicate[] createConstraints (CategoryFilter categoryFilter, CriteriaBuilder builder,Root<Category> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(!ObjectUtils.isEmpty(categoryFilter.getName())) {
			predicates.add(builder.like(
					builder.lower(root.get("name")), "%" + categoryFilter.getName().toLowerCase() + "%"));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private void createConstraintsPage(TypedQuery<Category> query, Pageable pageable) {
		int currentPage = pageable.getPageNumber();
		int totalRegisterByPage = pageable.getPageSize();
		int firstRegisterByPage = currentPage * totalRegisterByPage;
		
		query.setFirstResult(firstRegisterByPage);
		query.setMaxResults(totalRegisterByPage);
	}
	
	private Long total(CategoryFilter categoryFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Category> root = criteria.from(Category.class);
		
		Predicate[] predicates = createConstraints(categoryFilter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}
}
