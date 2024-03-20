package com.elmiraouy.jwtsecurity;

import com.elmiraouy.jwtsecurity.entities.AppRole;
import com.elmiraouy.jwtsecurity.repository.AppRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JwtSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtSecurityApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(AppRoleRepository appRoleRepository){
		return args -> {
			AppRole appRole=AppRole.builder().roleName("SUPPORT").build();
			
		};
	}

}
