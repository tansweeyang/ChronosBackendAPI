package dev.eislyn.chronos_calendar;

import dev.eislyn.chronos_calendar.config.RsaKeyProperties;
import dev.eislyn.chronos_calendar.controller.TaskController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableConfigurationProperties(RsaKeyProperties.class)
@EnableScheduling
@SpringBootApplication
@Import({TaskController.class})
public class AutoTodoApplication {
	public static void main(String[] args) {
		SpringApplication.run(AutoTodoApplication.class, args);
	}
}
