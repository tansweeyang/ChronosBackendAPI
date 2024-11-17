package dev.eislyn.chronos;

import dev.eislyn.chronos.config.RsaKeyProperties;
import dev.eislyn.chronos.controller.AuthController;
import dev.eislyn.chronos.controller.HomeController;
import dev.eislyn.chronos.controller.TaskController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableConfigurationProperties(RsaKeyProperties.class)
@EnableScheduling
@SpringBootApplication
//@ComponentScan(basePackages = "dev.eislyn.chronos")
@Import({AuthController.class, TaskController.class, HomeController.class})
public class AutoTodoApplication {
	public static void main(String[] args) {
		SpringApplication.run(AutoTodoApplication.class, args);
	}
}
