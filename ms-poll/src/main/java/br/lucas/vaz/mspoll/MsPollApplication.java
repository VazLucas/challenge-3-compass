package br.lucas.vaz.mspoll;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class MsPollApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsPollApplication.class, args);
	}

}
