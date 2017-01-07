package com.example.rest;

import com.example.rest.dto.PlayerSettings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Slf4j
@Service
public class PlayerRestConsumer {
  private final RestConsumerWithAuth restConsumer;

  @Autowired
  public PlayerRestConsumer(RestConsumerWithAuth restTemplate) {
    Assert.notNull(restTemplate, "restTemplate must not be null");
    this.restConsumer = restTemplate;
  }

  public void updatePlayerSettings(final PlayerSettings settings) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<PlayerSettings> request = new HttpEntity<>(settings, headers);

    restConsumer.exchange("/players/settings", HttpMethod.PUT, request, null);
  }
}