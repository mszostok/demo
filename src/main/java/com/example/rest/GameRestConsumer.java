package com.example.rest;

import com.example.model.GameDesc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static java.util.Arrays.asList;

@Slf4j
@Service
public class GameRestConsumer {
  private final RestConsumerWithAuth restConsumer;

  @Autowired
  public GameRestConsumer(RestConsumerWithAuth restTemplate) {
    Assert.notNull(restTemplate, "restTemplate must not be null");
    this.restConsumer = restTemplate;
  }

  public List<GameDesc> fetchGamesList() {
    log.debug("Get all game list");
    HttpEntity<String> request = new HttpEntity<>(new HttpHeaders());
    return asList(restConsumer.exchange("/games", HttpMethod.GET, request, GameDesc[].class).getBody());
  }
}