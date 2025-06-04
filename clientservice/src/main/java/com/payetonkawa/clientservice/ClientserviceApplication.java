package com.payetonkawa.clientservice;

import com.payetonkawa.clientservice.repository.ClientRepository;
import com.payetonkawa.clientservice.util.CsvLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ClientserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientserviceApplication.class, args);
	}
	@Bean
	CommandLineRunner run(ClientRepository repository) {
		return args -> {
			var clients = CsvLoader.loadClientsFromCsv();
			repository.saveAll(clients);
		};
	}

}
