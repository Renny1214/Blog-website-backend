package com.BlogWebsite.BlogWebsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class BlogWebsiteApplication {
	public static void main(String[] args) {
		SpringApplication.run(BlogWebsiteApplication.class, args);
	}
}
