package com.moneyapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moneyapi.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long>{

}
