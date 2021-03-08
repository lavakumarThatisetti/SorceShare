package com.sorceshare.aifeedservice;

import com.sorceshare.aifeedservice.repository.ArticleRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories(basePackageClasses = ArticleRepository.class)
public class AiFeedServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiFeedServiceApplication.class, args);
	}

}
