package com.person.personapp.configurations;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



@Configuration //
@EnableSwagger2
//Extendemos el WebMvc para configurar nuestras capaz de repositories
public class SwaggerConfig extends WebMvcConfigurationSupport {

    @Bean
    public Docket api(){
        //Creamos documentacion de tipo Swagger 2
        return new Docket(DocumentationType.SWAGGER_2)
                .select() //Para obtener la info de todos los controllers
                .apis(RequestHandlerSelectors.basePackage("com.person.personapp.controllers")) //Swagger se encargará de escanear todo los packetes que tengamos en controller
                .paths(paths()) //Buscamos Paths en particular
                .build() //
                .apiInfo(metaData()); //Se le agrega la informacion que se le desea mostrar
    }

    private Predicate<String> paths() {
        //regex -> Te verifica que cumpla con alguna serie de condiciones para ser aceptado.
        return Predicates.or(PathSelectors.regex("/api/persons.*"));
    }

    private ApiInfo metaData(){
        return  new ApiInfoBuilder()
                .title("Person API Documentation")
                .description("Esto es la documentación para Person API")
                .version("1.0")
                .license("")
                .licenseUrl("")
                .contact(new Contact("Lincol Morales Roca", "https://google.com", "lincol_mr_25@gmail.com"))
                .build();
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        //Habilite para swagger-ui la carpeta resources
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
