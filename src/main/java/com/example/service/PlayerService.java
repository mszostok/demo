package com.example.service;

import com.example.rest.PlayerRestConsumer;
import com.example.rest.dto.PlayerSettings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpStatusCodeException;

@Slf4j
@Service
public class PlayerService {

  private final PlayerRestConsumer playerClient;

  @Autowired
  public PlayerService(PlayerRestConsumer playerClient) {
    Assert.notNull(playerClient, "playerClient must not be null");
    this.playerClient = playerClient;
  }

  public void updatePlayerSettings(final PlayerSettings settings) {
    try {
      playerClient.updatePlayerSettings(settings);
    } catch (final HttpStatusCodeException ex) {
      log.error("while updating user settings: status code: {}, message: {} ", ex.getStatusCode(), ex.getResponseBodyAsString());
    } catch (final Exception ex) {
      log.error("while updating user settings: {}", ex);
    }
  }
}
