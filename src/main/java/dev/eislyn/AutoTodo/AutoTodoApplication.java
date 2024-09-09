package dev.eislyn.AutoTodo;

import dev.eislyn.AutoTodo.config.RsaKeyProperties;
import dev.eislyn.AutoTodo.domain.entities.UserEntity;
import dev.eislyn.AutoTodo.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class AutoTodoApplication {
	public static void main(String[] args) {
		SpringApplication.run(AutoTodoApplication.class, args);
	}

//	@Bean
//	CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//		return args -> {
//			userRepository.save(new UserEntity("user", passwordEncoder.encode("password"), "ROLE_USER"));
//			userRepository.save(new UserEntity("admin", passwordEncoder.encode("password"), "ROLE_ADMIN"));
//		};
//	}
}
