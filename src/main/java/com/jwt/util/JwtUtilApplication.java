package com.jwt.util;

import com.jwt.util.utils.KeyUtility;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JwtUtilApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtUtilApplication.class, args);
		//KeyUtility.getSecretKey();
	}

}
