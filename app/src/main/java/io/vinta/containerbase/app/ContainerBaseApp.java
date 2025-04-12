package io.vinta.containerbase.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "io.vinta.containerbase")
public class ContainerBaseApp {

	public static void main(String[] args) {
		SpringApplication.run(ContainerBaseApp.class, args);
	}

}
