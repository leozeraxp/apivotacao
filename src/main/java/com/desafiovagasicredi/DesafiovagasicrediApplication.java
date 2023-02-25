package com.desafiovagasicredi;

import com.desafiovagasicredi.security.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class DesafiovagasicrediApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafiovagasicrediApplication.class, args);
	}

}
