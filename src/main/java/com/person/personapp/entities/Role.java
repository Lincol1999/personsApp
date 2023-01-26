package com.person.personapp.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int roleId;

    private String roleName;

    //Sirve para crear un rol, sino existe.
    public Role(String roleName){
        this.roleName = roleName;
    }
}
