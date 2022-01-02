package com.estu.petify;

import com.estu.petify.petifycore.model.user.UserModel;
import com.estu.petify.petifycore.repository.UserRepository;
import com.estu.petify.petifycore.service.PetifyConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class PetifyApplication {

	@Autowired
	PetifyConfigurationService petifyConfigurationService;
	@Autowired
	private PasswordEncoder petifyPasswordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(PetifyApplication.class, args);
	}
/*
	@Bean
	public CommandLineRunner loadData(UserRepository userRepository){
		return args -> {
			// Initial user - 1

			final UserModel user1 = new UserModel("Testİsim",
					"TestSoyisim", "11/11/1997",
					"123123123132", "test@gmail.com",
					"Male", Instant.now().toString(),
					"Deneme Ev Adresi - Testİsim TestSoyisim");
			user1.setUsername("test@gmail.com");
			user1.setPassword(petifyPasswordEncoder.encode("12345"));
			user1.setActivated(Boolean.TRUE);

			userRepository.save(user1);

		};
	}
 */
}
