package com.malt.commission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class CommissionApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommissionApplication.class, args);
    }

}
