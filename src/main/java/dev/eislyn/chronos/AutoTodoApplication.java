package dev.eislyn.chronos;

import dev.eislyn.chronos.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableConfigurationProperties(RsaKeyProperties.class)
@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages = "dev.eislyn.AutoTodo")
public class AutoTodoApplication {
	public static void main(String[] args) {
		SpringApplication.run(AutoTodoApplication.class, args);
	}
}
