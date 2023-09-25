package br.edu.ifal.mpng.finview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("br.edu.ifal.mpng.finview.dominio.transacao")
public class FinviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinviewApplication.class, args);
	}

}
