package com.sorceshare.cronjobapi;

import com.sorceshare.cronjobapi.config.CronProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({CronProperties.class})
public class CronJobApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CronJobApiApplication.class, args);
	}

}
