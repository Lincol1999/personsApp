package com.person.personapp.services.impls;

import com.person.personapp.entities.Person;
import com.person.personapp.repositories.PersonRespository;
import com.person.personapp.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService{

    @Autowired
    private PersonRespository repository;

    @Override
    public List<Person> getPersons() {
        return repository.findAll();
    }

    @Override
    public Optional<Person> getPersonById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Person createPerson(Person person) {
        return repository.save(person);
    }

    @Override
    public Person updatePerson(Person person) {
        //Si existe una persona con el id = true
        boolean exits = repository.existsById(person.getId());
        //Si existe persona, lo guarda de lo contrario retorna null
        return exits ? repository.save(person) : null;
    }

    @Override
    public boolean deletePersonById(Integer id) {
        boolean deleted = false;
        try {
            boolean exits = repository.existsById(id);
            if(exits) repository.deleteById(id);
            deleted = true;
        }catch (RuntimeException e) {
            e.printStackTrace();
        }
        return deleted;
    }

    @Override
    public List<Person> getActivePersons(boolean isActive) {
        return repository.findByActive(isActive);
    }
}
