package com.person.personapp.services;

import com.person.personapp.entities.User;
import com.person.personapp.models.UserModel;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService{
    User register(UserModel model); //Registramos un usuario, por ende creamos un modelo. Solo queremos los atributos ya que al crear no vamos agregar el rol.

}
