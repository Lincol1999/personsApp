package com.person.personapp.repositories;

import com.person.personapp.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRespository extends JpaRepository<Person, Integer> {
    List<Person> findByActive(boolean active);
}
