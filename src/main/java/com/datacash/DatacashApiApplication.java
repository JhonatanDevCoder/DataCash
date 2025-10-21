package com.datacash; // Debe estar en el paquete raíz

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // La anotación más importante
public class DatacashApiApplication {

    // Este es el método "main" que Spring Boot está buscando
    public static void main(String[] args) {
        SpringApplication.run(DatacashApiApplication.class, args);
    }

}