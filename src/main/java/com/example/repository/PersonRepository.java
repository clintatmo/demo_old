package com.example.repository;

import com.example.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by clint on 9/5/16.
 */

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{
}
