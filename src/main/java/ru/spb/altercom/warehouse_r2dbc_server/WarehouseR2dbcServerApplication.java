package ru.spb.altercom.warehouse_r2dbc_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
public class WarehouseR2dbcServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WarehouseR2dbcServerApplication.class, args);
    }

}
