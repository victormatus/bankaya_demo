package com.bankaya.demo.config;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class PokemonRestClient {
	
	@Bean
	public RestTemplate pokemonRestTemplate(RestTemplateBuilder builder) {
		return builder
				.setConnectTimeout(Duration.ofMillis(3000))
				.setReadTimeout(Duration.ofMillis(5000))
				.rootUri("https://pokeapi.co/api/v2/pokemon")
				.build();
	}
}
