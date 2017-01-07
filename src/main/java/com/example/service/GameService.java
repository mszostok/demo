package com.example.service;

import com.example.model.GameDesc;
import com.example.rest.GameRestConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class GameService {

  private final GameRestConsumer gameClient;

  @Autowired
  public GameService(GameRestConsumer gameClient) {
    Assert.notNull(gameClient, "gameClient must not be null");
    this.gameClient = gameClient;
  }


  public Optional<List<GameDesc>> getAllGames() {
    try {
      return Optional.of(gameClient.fetchGamesList());
    } catch (final HttpStatusCodeException ex) {
      log.error("Unexpected status code was returned while fetching games from server: {}", ex);
    } catch (final Exception ex) {
      log.error("while fetching games: {}", ex);
    }

    return Optional.empty();
  }
}
