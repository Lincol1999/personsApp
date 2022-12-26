package com.person.personapp.services;

import com.person.personapp.entities.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    List<Person> getPersons();

    Optional<Person> getPersonById(Integer id);

    Person createPerson(Person person);

    Person updatePerson(Person person);

    boolean deletePersonById(Integer id);

    List<Person> getActivePersons(boolean isActive);

}
