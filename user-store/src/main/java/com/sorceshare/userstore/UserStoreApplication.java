package com.sorceshare.userstore;

import com.sorceshare.userstore.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableEurekaClient
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class UserStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserStoreApplication.class, args);
	}

}
