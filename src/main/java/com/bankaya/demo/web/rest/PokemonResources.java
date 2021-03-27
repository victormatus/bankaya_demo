package com.bankaya.demo.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankaya.demo.service.PokemonService;
import com.bankaya.demo.service.dto.PokemonDTO;

@RestController
@RequestMapping("/api/pokemon")
public class PokemonResources {
	
	private final PokemonService pokemonService;
	
	
	
	public PokemonResources(PokemonService pokemonService) {
		super();
		this.pokemonService = pokemonService;
	}



	@GetMapping("/name/{name}")
	public ResponseEntity<Object>findByName(@PathVariable(name = "name") String name){
//		PokemonDTO dto = pokemonService.findAbilities(name);
//		return ResponseEntity.ok().body(dto);
		return null;
	}
	// abilities
	@GetMapping("")
	public ResponseEntity<Object>findAbilities(@PathVariable(name = "")String name){
		return null;
	}
	
}
