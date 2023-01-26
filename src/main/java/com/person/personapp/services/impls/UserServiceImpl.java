package com.person.personapp.services.impls;

import com.person.personapp.entities.Role;
import com.person.personapp.entities.User;
import com.person.personapp.models.UserModalDetails;
import com.person.personapp.models.UserModel;
import com.person.personapp.repositories.RoleRepository;
import com.person.personapp.repositories.UserRepository;
import com.person.personapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    //Creamos un metood para encriptar el password
    private BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public User register(UserModel model) {
        String roleName = "ROLE_USER"; //Todo usuario que se registre tendrá el rol de USER
        Role role = new Role(roleName); //Crea un role
        if(roleRepository.existsByRoleName(roleName)) //Verificamos si existe el rol (ROLE_USER) que estamos pasando por medio del roleName
            role = roleRepository.findByRoleName(roleName).get(); //Actualiza un rol
        //Almacenamos lo que viene del modelo a user
        User user = new User();
        user.setUsername(model.getUsername());
        user.setPassword(passwordEncoder().encode(model.getPassword())); //Aquí encriptamos el password
        user.setEmail(model.getEmail());
        user.setFirstname(model.getFirstname());
        user.setLastname(model.getLastname());
        user.setStatus(model.getStatus());
        user.setRoles(Arrays.asList(role));
        return userRepository.save(user);
    }

    //Este moetodo se crea automaticamente por parte de UserDetailsService, busca los datos del usuario a traves del username (de tipo Details)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username) //Buscamos al usuario
//                .map(user -> new UserModalDetails(user)) //El userModalDetail recibe un user que se obtiene en la linea superior.
                //con el ::new creamos una nueva instancia de UserModalDetails pasando el username
                .map(UserModalDetails::new) //Cada uno de los atributos de la clase usuario, se van a mapear en los atributos de UserModalDetails
                .orElseThrow( () -> new UsernameNotFoundException("Username not found: " + username));
    }
}
