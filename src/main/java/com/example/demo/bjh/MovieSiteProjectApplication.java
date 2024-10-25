package com.example.demo.bjh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing //JPA 리스너 기능 활성화 시키기
public class MovieSiteProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieSiteProjectApplication.class, args);
	}

}
