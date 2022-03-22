package com.example.springws;

import com.example.springws.dao.CompteRepo;
import com.example.springws.entites.Compte;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class SpringWsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWsApplication.class, args);
    }
    @Bean
    CommandLineRunner start(CompteRepo compteRepo) throws Exception {
        return args ->{
            compteRepo.save(new Compte(null,70000,new Date(),"Active"));
            compteRepo.save(new Compte(null,70000,new Date(),"Active"));
            compteRepo.save(new Compte(null,70000,new Date(),"Blocked"));
        };
    }
}
