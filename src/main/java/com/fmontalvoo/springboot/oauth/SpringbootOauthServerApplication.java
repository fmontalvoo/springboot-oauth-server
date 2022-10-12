package com.fmontalvoo.springboot.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class SpringbootOauthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootOauthServerApplication.class, args);
	}

}
