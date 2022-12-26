package com.person.personapp.controllers;

import com.person.personapp.entities.Person;
import com.person.personapp.services.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;



@Slf4j
@RestController
@RequestMapping("/api/persons/v1")

public class PersonController {

    private static final String ACTIVE = "Active";
    private static final String INACTIVE = "Inactive";

    @Autowired
    private PersonService personService;

    @GetMapping(value = "")
    ResponseEntity<List<Person>> getPersons (){
        try{
            List<Person> persons = personService.getPersons();

            //Si la lista persona esta vacia
            if (persons.isEmpty()){
                log.warn("Person no content");
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            //Persona encontrada
            log.info("Person found");
            return new ResponseEntity<>(persons, HttpStatus.OK);
        }catch(RuntimeException e){
                log.error("Error while getting persons");
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value= "/{id}")
    ResponseEntity<Person> getPersonById (@PathVariable Integer id){

        try {
            Optional<Person> person = personService.getPersonById(id);

            //El optionar cuenta con un funcion llamada isPresent, si esta presente
            if (person.isPresent()){
                log.info("Person {} found", id);
                return new ResponseEntity<>(person.get(), HttpStatus.OK);
            }else{
                log.warn("Person {} no found", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch(RuntimeException e){
            log.error("Error while getting person {}", id);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value="")
    ResponseEntity<Person> createPerson(@RequestBody Person person){
        try {
            Person newPerson = personService.createPerson(person);
            log.info("Person created");
            return new ResponseEntity<>(newPerson, HttpStatus.CREATED);
        }catch(RuntimeException e){
            log.error("Error while creating person");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value="")
    ResponseEntity<Person> updatePerson(@RequestBody Person person){

        try {
            Person updatePerson = personService.updatePerson(person);
            log.info("Person {} update", person.getId());
            return new ResponseEntity<>(updatePerson, HttpStatus.OK);
        }catch(RuntimeException e){
            log.error("Error while updating person {}", person.getId());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value="/{id}")
    //Recibe boolean
    ResponseEntity<Boolean> deletePerson( @PathVariable Integer id){

        try {
            boolean deleted = personService.deletePersonById(id);
            log.info("Person {id} deleted", id);
            return new ResponseEntity<>(deleted, HttpStatus.NO_CONTENT); //Acepta el bool
        }catch(RuntimeException e){
            log.error("Error while deleting person {}", id);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/actives/{isActive}")
    ResponseEntity<List<Person>> getActivePersons(@PathVariable boolean isActive){

        try {

            List<Person> activePerson = personService.getActivePersons(isActive);
            if (activePerson.isEmpty()){
                log.warn("{} person not found", isActive ? ACTIVE : INACTIVE);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            log.info("{} person found", isActive ? ACTIVE : INACTIVE);
            return new ResponseEntity<>(activePerson, HttpStatus.OK);

        }catch(RuntimeException e){
            log.error("Error while getting {} person", isActive ? ACTIVE : INACTIVE);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
