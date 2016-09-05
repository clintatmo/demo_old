package com.example.services;

import com.example.dtos.PersonDto;
import com.example.entities.Person;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by clint on 9/5/16.
 */

@Service
public interface PersonService {
    Person findById(long id);

    void savePerson(Person person);

    void updatePerson(Person person);

    void deletePersonById(long id);

    List<Person> findAllPersons();

    void deleteAllPersons();

    boolean isPersonExist(PersonDto person);
}
