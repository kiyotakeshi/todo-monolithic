package com.kiyotakeshi.todo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TodoApplication {

	private static final Logger log = LoggerFactory.getLogger(TodoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TodoApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(TodoRepository repository){
		return (args -> {
			repository.save(new Todo("wash dishes","white", "housework"));
			repository.save(new Todo("throw garbage", "white","housework"));
			repository.save(new Todo("go to office","black", "job"));
			repository.save(new Todo("turn on documents", "black","job"));
			repository.save(new Todo("make a presentation","black", "job"));

			log.info("Todo found with findAll()");
			log.info("------------------------------");
			for (Todo customer: repository.findAll()){
				log.info(customer.toString());
			}
			log.info("");

			Todo customer = repository.findById(1L);
			log.info("Todo found with findById(1L):");
			log.info("------------------------------");
			log.info(customer.toString());
			log.info("");

			log.info("Todo found with findByActivityName('housework'):");
			repository.findByActivityName("housework").forEach(housework -> {
				log.info(housework.toString());
			});
			log.info("");
		});
	}
}
