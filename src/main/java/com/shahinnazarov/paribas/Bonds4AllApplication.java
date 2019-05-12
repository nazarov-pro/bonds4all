package com.shahinnazarov.paribas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class Bonds4AllApplication {

    public static void main(String[] args) {
        SpringApplication.run(Bonds4AllApplication.class, args);
    }
}
