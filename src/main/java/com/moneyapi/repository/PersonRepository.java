package com.moneyapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moneyapi.model.Person;

public interface PersonRepository extends JpaRepository<Person,Long>{

}
