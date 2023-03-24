package com.mahov.cars;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition
public class CarServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(CarServiceApplication.class, args);
  }
  @Bean
  public AtomicBoolean isLotteryGoingNow(){
    return new AtomicBoolean(false);
  }
}
