package com.example.config;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig {
  private final S1SRemoteServer S1SRemoteServer = new S1SRemoteServer();

  @Getter
  @Setter
  @ToString
  public static class S1SRemoteServer {
    private String basePathUrl;
    private String user;
    private String password;
  }
}

