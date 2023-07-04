package com.moneyapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moneyapi.model.People;

public interface PeopleRepository extends JpaRepository<People,Long>{

}
