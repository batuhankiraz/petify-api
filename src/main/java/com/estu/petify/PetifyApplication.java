package com.estu.petify;

import com.estu.petify.petifycore.service.PetifyConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@SpringBootApplication
public class PetifyApplication {

	@Autowired
	PetifyConfigurationService petifyConfigurationService;
	@Autowired
	private PasswordEncoder petifyPasswordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(PetifyApplication.class, args);
	}

	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList(petifyConfigurationService.getValue("petify.app.ui.source.url")));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
				"Accept", "Authorization", "Origin, Accept", "X-Requested-With",
				"Access-Control-Request-Method", "Access-Control-Request-Headers"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
				"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
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
