package com.compass.av4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class Av4Application {

	public static void main(String[] args) {
		SpringApplication.run(Av4Application.class, args);
	}

}
