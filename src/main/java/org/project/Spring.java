package org.project;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;



@SpringBootApplication
public class Spring extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Spring.class, args);
    }

}
