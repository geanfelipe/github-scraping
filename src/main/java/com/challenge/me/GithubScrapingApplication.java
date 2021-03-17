
package com.challenge.me;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class GithubScrapingApplication {

	public static void main(final String[] args) {
		SpringApplication.run(GithubScrapingApplication.class, args);
	}

}
