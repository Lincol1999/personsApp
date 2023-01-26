package com.person.personapp.repositories;

import com.person.personapp.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByRoleName(String roleName); //Buscamos el rol por el nombre si existe
    boolean existsByRoleName(String roleName); //Si existe un rol por el roleName, sino lo creamos

}
