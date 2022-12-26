package com.person.personapp.services;

import com.person.personapp.entities.Person;
import com.person.personapp.repositories.PersonRespository;
import com.person.personapp.services.impls.PersonServiceImpl;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

//Nos dice que nos vamos apoyar de mockito para realizar pruebas en el servicio
@RunWith(MockitoJUnitRunner.class)
public class PersonServiceTest {

    @InjectMocks
    private PersonServiceImpl service;

    @Mock //Permite
    private PersonRespository mockedRepository;

    private List<Person> persons= Arrays.asList(
            new Person(1, "Person 1", 12, true),
            new Person(2, "Person 2", 15, true),
            new Person(3, "Person 3", 23, false)
    );

    private List<Person> activePersons = persons.stream().filter(f -> f.isActive()).collect(Collectors.toList());

    @Test
    public void testSaveProduct_success() {
        Person request = new Person();
        request.setName("Person 1");
        request.setAge(12);
        request.setActive(true);

        Person response = new Person(1, "Person 1", 10, true);

        when(mockedRepository.save(request)).thenReturn(response);
        Person newPerson = service.createPerson(request);

        assertNotNull(newPerson);
        assertEquals(response, newPerson);
        assertEquals(response.getName(), newPerson.getName());

    }

    @Test
    public void testUpdatePerson_success() {
        Person response = new Person(1, "Person 1", 21, true);

        when(mockedRepository.existsById(eq(1))).thenReturn(true);
        when(mockedRepository.save(any(Person.class))).thenReturn(response);

        Person newPerson = service.updatePerson(response);

        assertEquals(response, newPerson);

    }

    @Test
    public void testGetPersonById_success() {
        Person response = new Person(1, "Person 1", 21, true);

        when(mockedRepository.findById(eq(1))).thenReturn(Optional.of(response));
        Optional<Person> person = service.getPersonById(1);

        person.ifPresent((value)-> {
            assertEquals(value, response);
        });
    }

    @Test
    public void testDeletePersonById() {
        when(mockedRepository.existsById(eq(1))).thenReturn(true);
        service.deletePersonById(1);
    }

    @Test
    public void testGetAllPersons() {
        when(mockedRepository.findAll()).thenReturn(persons);
        List<Person> allPersons = service.getPersons();

        assertEquals(persons, allPersons);
        assertEquals(3, allPersons.size());

    }

    @Test
    public void testGetActivePersons() {
        when(mockedRepository.findByActive(eq(true))).thenReturn(activePersons);
        List<Person> persons = service.getActivePersons(true);

        assertEquals(activePersons, persons);
        assertEquals(2, persons.size());
    }

}