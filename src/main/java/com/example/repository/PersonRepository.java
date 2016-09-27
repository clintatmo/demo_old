package com.example.repository;

import com.example.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/* * * @author Clint Atmosoerodjo #commander *  */

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{
}
