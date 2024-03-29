package com.example.service;

import com.example.dto.PersonDto;
import com.example.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;

/* * * @author Clint Atmosoerodjo #commander *  */

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
