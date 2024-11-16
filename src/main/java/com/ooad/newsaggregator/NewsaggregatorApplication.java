package com.ooad.newsaggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@EnableWebMvc
@ComponentScan
public class NewsaggregatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsaggregatorApplication.class, args);
	}

}
