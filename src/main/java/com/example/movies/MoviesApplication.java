package com.example.movies;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication()
public class MoviesApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(
                MoviesApplication.class,
                SwaggerConfig.class
        ).run(args);
    }

}
