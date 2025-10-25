package com.genesisnest.gdpt.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.modulith.Modulith;

@SpringBootApplication
@Modulith
@ComponentScan(basePackages = "com.genesisnest.gdpt")
@EntityScan(basePackages = "com.genesisnest.gdpt")
public class GnDeliveryPilotTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(GnDeliveryPilotTestApplication.class, args);
    }
}
