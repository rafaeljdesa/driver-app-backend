package br.com.driverapp.racingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class RacingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RacingServiceApplication.class, args);
	}

}
