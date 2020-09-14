package com.dababy.social;

import com.dababy.social.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.crypto.spec.PSource;

@SpringBootApplication
public class SocialApplication {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(SocialApplication.class, args);

	}

}
