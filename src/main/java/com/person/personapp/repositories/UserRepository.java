package com.person.personapp.repositories;


import com.person.personapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);
//    boolean existsByUserName(String username);
//    boolean existsByEmail(String email);
}
