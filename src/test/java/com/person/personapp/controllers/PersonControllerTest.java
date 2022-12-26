package com.person.personapp.controllers;


import com.person.personapp.entities.Person;
import com.person.personapp.services.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(PersonController.class) //Sirve para simular que estamos golpeando al servicio.
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    private MockMvc mvc; //Llama al servicio

    @MockBean
    private PersonService personService;

    private List<Person> persons = Arrays.asList(
            new Person(1, "Person 1", 12, true),
            new Person(2, "Person 2", 15, true),
            new Person(3, "Person 3", 23, false)
    );

    private List<Person> activePersons = persons.stream().filter( f -> f.isActive()).collect(Collectors.toList());

    private List<Person> inactivePersons = persons.stream().filter( f -> !f.isActive()).collect(Collectors.toList());

    private static final String BASE_PATH = "/api/persons/v1";

    @BeforeEach
    void setUp(){

    }

    @Test
    void getPersons_success() throws Exception{
        when(personService.getPersons()).thenReturn(persons);
        RequestBuilder request = get(BASE_PATH);

        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void getPerson_noContent() throws Exception{
        when(personService.getPersons()).thenReturn(Arrays.asList());

        RequestBuilder request = get(BASE_PATH);

        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
    }

    @Test
    void getPerson_serverError() throws Exception{
        when(personService.getPersons()).thenThrow(new RuntimeException());
        RequestBuilder request = get(BASE_PATH);
        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getResponse().getStatus());
    }

    @Test
    void getPersonById_success() throws Exception{
        when(personService.getPersonById(1)).thenReturn(Optional.of(persons.get(0)));
        RequestBuilder request = get(BASE_PATH.concat("/1"));
        MvcResult result = mvc.perform(request).andReturn();

        assertNotNull(result);
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

    }

    @Test
    void getPersonById_notFound() throws Exception{
        when(personService.getPersonById(1)).thenReturn((Optional.empty()));
        RequestBuilder request = get(BASE_PATH.concat("/1"));
        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @Test
    void getPersonById_serverError() throws Exception{
        when(personService.getPersonById(1)).thenThrow(new RuntimeException());
        RequestBuilder request = get(BASE_PATH.concat("/1"));
        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getResponse().getStatus());
    }

    @Test
    void createPerson_success() throws Exception{
        String newPersonJson = "{\"name\": \"Ricardo\", \"age\":21, \"active\": true}";

        when(personService.createPerson(any(Person.class))).thenReturn(persons.get(0));
        RequestBuilder request = post(BASE_PATH)
                .accept(MediaType.APPLICATION_JSON).content(newPersonJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());

    }
    @Test
    void createPerson_serverError() throws Exception{
        String newPersonJson = "{\"name\": \"Ricardo\", \"age\":21, \"active\": true}";

        when(personService.createPerson(any(Person.class))).thenThrow(new RuntimeException());
        RequestBuilder request = post(BASE_PATH)
                .accept(MediaType.APPLICATION_JSON).content(newPersonJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getResponse().getStatus());
    }

    @Test
    void testUpdatePerson_success() throws Exception{
        String newPersonJson = "{\"id\":1,\"name\":\"Ricardo\",\"age\":21,\"active\": true}";

        when(personService.updatePerson(any(Person.class))).thenReturn(persons.get(0));
        RequestBuilder request = put(BASE_PATH)
                .accept(MediaType.APPLICATION_JSON).content(newPersonJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void updatePerson_serverError() throws Exception{
        String newPersonJson = "{\"id\":1,\"name\":\"Ricardo\",\"age\":21,\"active\": true}";

        when(personService.updatePerson(any(Person.class))).thenThrow(new RuntimeException());
        RequestBuilder request = put(BASE_PATH)
                .accept(MediaType.APPLICATION_JSON).content(newPersonJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getResponse().getStatus());
    }

    @Test
    void deletePerson_success() throws Exception {
        when(personService.deletePersonById(1)).thenReturn(true);
        RequestBuilder request = delete(BASE_PATH.concat("/1"));
        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());

    }

    @Test
    void deletePerson_serverError() throws Exception {
        when(personService.deletePersonById(1)).thenThrow(new RuntimeException());
        RequestBuilder request = delete(BASE_PATH.concat("/1"));
        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getResponse().getStatus());
    }

    @Test
    void getActivePersons_success() throws Exception {
        when(personService.getActivePersons(true)).thenReturn(activePersons);
        RequestBuilder request = get(BASE_PATH.concat("/actives/true"));
        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void getInactivePersons_success() throws Exception {
        when(personService.getActivePersons(false)).thenReturn(inactivePersons);
        RequestBuilder request = get(BASE_PATH.concat("/actives/false"));
        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void getActivePersons_notFound() throws Exception {
        when(personService.getActivePersons(true)).thenReturn(Arrays.asList());
        RequestBuilder request = get(BASE_PATH.concat("/actives/true"));
        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
    }

    @Test
    void getInactivePersons_notFound() throws Exception {
        when(personService.getActivePersons(false)).thenReturn(Arrays.asList());
        RequestBuilder request = get(BASE_PATH.concat("/actives/false"));
        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
    }

    @Test
    void getActivePersons_serverError() throws Exception {
        when(personService.getActivePersons(true)).thenThrow(new RuntimeException());
        RequestBuilder request = get(BASE_PATH.concat("/actives/true"));
        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getResponse().getStatus());
    }

    @Test
    void getInactivePersons_serverError() throws Exception {
        when(personService.getActivePersons(false)).thenThrow(new RuntimeException());
        RequestBuilder request = get(BASE_PATH.concat("/actives/false"));
        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getResponse().getStatus());
    }














































}