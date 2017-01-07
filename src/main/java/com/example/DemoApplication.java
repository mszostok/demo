package com.example;

import com.example.rest.dto.PlayerSettings;
import com.example.service.GameService;
import com.example.service.PlayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  @Bean
  public CommandLineRunner run(final GameService gameService, final PlayerService playerService) throws Exception {
    return args -> {
      gameService.getAllGames().ifPresent(games -> {
        System.out.println("### List of all available games ###");
        games.forEach(System.out::println);
      });

      playerService.updatePlayerSettings(new PlayerSettings("PL"));
      System.exit(0);
    };
  }
}
