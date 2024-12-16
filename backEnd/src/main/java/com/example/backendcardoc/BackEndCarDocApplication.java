package com.example.backendcardoc;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


@SpringBootApplication
@ServletComponentScan
public class BackEndCarDocApplication {

	public static void main(String[] args) {

		SpringApplication.run(BackEndCarDocApplication.class, args);
	}

}
