package com.example.services.implementations;

import com.example.dtos.PersonDto;
import com.example.entities.Person;
import com.example.repositories.PersonRepository;
import com.example.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by clint on 9/5/16.
 */

@Service
public class PersonServiceimpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Person findById(long id) {
        return personRepository.findOne(id);
    }

    @Override
    public void savePerson(Person person) {
        personRepository.save(person);
    }

    @Override
    public void updatePerson(Person person) {
        personRepository.save(person);
    }

    @Override
    public void deletePersonById(long id) {
        personRepository.delete(id);
    }

    @Override
    public List<Person> findAllPersons() {
        return personRepository.findAll();
    }

    @Override
    public void deleteAllPersons() {
        personRepository.deleteAll();
    }

    @Override
    public boolean isPersonExist(PersonDto person) {
        return personRepository.exists(person.getId());
    }
}
