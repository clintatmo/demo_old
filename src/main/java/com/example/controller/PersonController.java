package com.example.controller;

import com.example.dto.PersonDto;
import com.example.model.Person;
import com.example.service.PersonService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/* * * @author Clint Atmosoerodjo #commander *  */

@RestController
@RequestMapping(value = "/api/person")
@Api
public class PersonController {

    @Autowired
    private PersonService personService;

    //-------------------Retrieve All Persons--------------------------------------------------------

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Person >> listAllPersons() {
        List<Person> Persons = personService.findAllPersons();
        if(Persons.isEmpty()){
            return new ResponseEntity<List<Person>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Person>>(Persons, HttpStatus.OK);
    }


    //-------------------Retrieve Single Person--------------------------------------------------------

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> getPerson(@PathVariable("id") long id) {
        System.out.println("Fetching Person with id " + id);
        Person Person = personService.findById(id);
        if (Person == null) {
            System.out.println("Person with id " + id + " not found");
            return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Person>(Person, HttpStatus.OK);
    }



    //-------------------Create a Person--------------------------------------------------------

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Void> createPerson(@RequestBody PersonDto person, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating Person with email " + person.getLastname());

        if (personService.isPersonExist(person)) {
            System.out.println("A Person with email " + person.getLastname() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        Person person1 = new Person();
        person1.setFirstname(person.getFirstname());
        person1.setLastname(person.getLastname());
        person1.setBirthday(person.getBirthday());
        personService.savePerson(person1);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/Person/{id}").buildAndExpand(person.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }


    //------------------- Update a Person --------------------------------------------------------

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Person> updatePerson(@PathVariable("id") long id, @RequestBody Person person) {
        System.out.println("Updating Person " + id);

        Person currentPerson = personService.findById(id);

        if (currentPerson==null) {
            System.out.println("Person with id " + id + " not found");
            return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
        }

        currentPerson.setLastname(person.getLastname());

        personService.updatePerson(currentPerson);
        return new ResponseEntity<Person>(currentPerson, HttpStatus.OK);
    }

    //------------------- Delete a Person --------------------------------------------------------

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Person> deletePerson(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting Person with id " + id);

        Person Person = personService.findById(id);
        if (Person == null) {
            System.out.println("Unable to delete. Person with id " + id + " not found");
            return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
        }

        personService.deletePersonById(id);
        return new ResponseEntity<Person>(HttpStatus.NO_CONTENT);
    }


    //------------------- Delete All Persons --------------------------------------------------------

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<Person> deleteAllPersons() {
        System.out.println("Deleting All Persons");

        personService.deleteAllPersons();
        return new ResponseEntity<Person>(HttpStatus.NO_CONTENT);
    }
}
