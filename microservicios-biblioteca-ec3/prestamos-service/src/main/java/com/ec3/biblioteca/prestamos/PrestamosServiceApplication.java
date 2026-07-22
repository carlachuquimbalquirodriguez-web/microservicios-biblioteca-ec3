package com.ec3.biblioteca.prestamos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class PrestamosServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PrestamosServiceApplication.class, args);
    }
}
