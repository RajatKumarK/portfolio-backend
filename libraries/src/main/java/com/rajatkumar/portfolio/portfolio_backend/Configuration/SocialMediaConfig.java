package com.rajatkumar.portfolio.portfolio_backend.Configuration;

import java.time.Duration;
import java.util.function.Supplier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SocialMediaConfig {

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplateBuilder()
        .requestFactory(this.clientHttpRequestFactorySupplier())
        .build();
  }

  private Supplier<ClientHttpRequestFactory> clientHttpRequestFactorySupplier() {
    return () -> {
      HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
      factory.setConnectTimeout(Math.toIntExact(Duration.ofSeconds(5).toMillis()));
      factory.setReadTimeout(Math.toIntExact(Duration.ofSeconds(10).toMillis()));
      return factory;
    };
  }
}