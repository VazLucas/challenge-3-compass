package br.lucas.vaz.msvoting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsVotingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsVotingApplication.class, args);
	}

}
