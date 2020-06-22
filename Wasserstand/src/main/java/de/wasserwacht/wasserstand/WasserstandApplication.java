package de.wasserwacht.wasserstand;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import de.wasserwacht.wasserstand.python.PythonService;
import de.wasserwacht.wasserstand.python.PythonServiceFactory;

@SpringBootApplication
@EnableScheduling
@ComponentScan
public class WasserstandApplication {

	public static void main(String[] args) {
		SpringApplication.run(WasserstandApplication.class, args);
	}
	
	@Bean(name="pythonServiceFactory")
	public PythonServiceFactory psf() {
		return new PythonServiceFactory();
	}
	
	@Bean(name="PythonServiceP")
	public PythonService Python() throws Exception {
		return psf().getObject();
	}

}
