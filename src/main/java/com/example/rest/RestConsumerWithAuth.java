package com.example.rest;

import com.example.config.AppConfig;
import com.example.model.TokenMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class RestConsumerWithAuth {
  private final RestTemplate restTemplate;
  private final String basePath;
  private final String loginEndpoint;
  private final String user;
  private final String password;

  private String token;

  @Autowired
  public RestConsumerWithAuth(RestTemplateBuilder builder, AppConfig appConfig) {
    Assert.notNull(builder, "builder must not be null");
    Assert.notNull(appConfig, "appConfig must not be null");

    this.restTemplate = builder.build();
    this.user = appConfig.getS1SRemoteServer().getUser();
    this.password = appConfig.getS1SRemoteServer().getPassword();
    this.basePath = appConfig.getS1SRemoteServer().getBasePathUrl();
    this.loginEndpoint = appConfig.getS1SRemoteServer().getBasePathUrl() + "/auth/login";
  }

  public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity,
                                        Class<T> responseType, Object... uriVariables) throws RestClientException {
    try {
      return executeExchangeWithAuth(url, method, requestEntity, responseType, uriVariables);
    } catch (final HttpStatusCodeException ex) {
      if (ex.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
        refreshToken();

        return executeExchangeWithAuth(url, method, requestEntity, responseType, uriVariables);
      }

      throw ex;
    }
  }

  private void refreshToken() {
    log.info("executing retry policy");

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("email", user);
    params.add("password", password);

    TokenMap tokenMap = restTemplate.postForObject(loginEndpoint, params, TokenMap.class);
    token = tokenMap.getToken();
  }

  private <T> ResponseEntity<T> executeExchangeWithAuth(String url, HttpMethod method, HttpEntity<?> requestEntity,
                                                        Class<T> responseType, Object... uriVariables) {
    HttpHeaders headers = new HttpHeaders();
    headers.setAll(requestEntity.getHeaders().toSingleValueMap());
    headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);

    HttpEntity<?> request = new HttpEntity<>(requestEntity.getBody(), headers);

    return restTemplate.exchange(basePath + url, method, request, responseType, uriVariables);
  }

}
