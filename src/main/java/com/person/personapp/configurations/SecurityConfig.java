package com.person.personapp.configurations;

import com.person.personapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity //Da la segurdad a nivel de url o servicios
@EnableMethodSecurity

public class SecurityConfig {

    @Autowired
    private UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    //Esta funcion intercepta cada peticion http
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception {
        return http
                .csrf().disable() //csrf lo ponemos en disable ya que dejará pasar los ataques por medio del internet (token, etc)
                //Cada solicitud que ingresa pasara por el authorizeHttpRequests, todas los http (auth)
                .authorizeHttpRequests( auth -> auth
                        .mvcMatchers("/api/users/v1").permitAll() //Permite pasar la url (/api/users/v1) no necesite autenticación.
                        .anyRequest().authenticated() //Todo lo demas si deben ser autenticados, excepto la url (/api/users/v1)
                )

                .userDetailsService(userService)
                .headers( headers -> headers.frameOptions().sameOrigin()) //
                .httpBasic(withDefaults()) //La seguridad basica del http deberan ser por defecto.
                .build();
    }
}
