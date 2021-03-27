package com.bankaya.demo.web.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.bankaya.demo.AbilitiesRequest;
import com.bankaya.demo.AbilitiesResponse;
import com.bankaya.demo.BaseExperienceRequest;
import com.bankaya.demo.BaseExperienceResponse;
import com.bankaya.demo.HeldItemsRequest;
import com.bankaya.demo.HeldItemsResponse;
import com.bankaya.demo.IdRequest;
import com.bankaya.demo.IdResponse;
import com.bankaya.demo.LocationAreaEncountersRequest;
import com.bankaya.demo.LocationAreaEncountersResponse;
import com.bankaya.demo.NameRequest;
import com.bankaya.demo.NameResponse;
import com.bankaya.demo.Pokemon;
import com.bankaya.demo.service.PokemonService;

@Endpoint

public class PokemonEndpoint {
	
	private static final String NAMESPACE_URI = "http://www.bankaya.com/demo";
	
	private final PokemonService pokemonService;
	
	@Autowired
	public PokemonEndpoint(PokemonService pokemonService) {
		this.pokemonService = pokemonService;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "abilitiesRequest")
	@ResponsePayload
	public AbilitiesResponse getAbilities(@RequestPayload AbilitiesRequest request) {
		AbilitiesResponse response = new AbilitiesResponse();
		Pokemon pokemon = pokemonService.findAbilities(request.getName());
		response.setPokemon(pokemon);
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "base_experienceRequest")
	@ResponsePayload
	public BaseExperienceResponse getBaseExperience(@RequestPayload BaseExperienceRequest request) {
		BaseExperienceResponse response = new BaseExperienceResponse();
		Pokemon pokemon = pokemonService.findBaseExperience(request.getName());
		response.setPokemon(pokemon);
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "idRequest")
	@ResponsePayload
	public IdResponse getId(@RequestPayload IdRequest request) {
		IdResponse response = new IdResponse();
		Pokemon pokemon = pokemonService.findId(request.getName());
		response.setPokemon(pokemon);
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "nameRequest")
	@ResponsePayload
	public NameResponse getName(@RequestPayload NameRequest request) {
		NameResponse response = new NameResponse();
		Pokemon pokemon = pokemonService.findName(request.getName());
		response.setPokemon(pokemon);
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "location_area_encountersRequest")
	@ResponsePayload
	public LocationAreaEncountersResponse getName(@RequestPayload LocationAreaEncountersRequest request) {
		LocationAreaEncountersResponse response = new LocationAreaEncountersResponse();
		Pokemon pokemon = pokemonService.findLocationAreaEncounters(request.getName());
		response.setPokemon(pokemon);
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "held_itemsRequest")
	@ResponsePayload
	public HeldItemsResponse getHeldItems(@RequestPayload HeldItemsRequest request) {
		HeldItemsResponse response = new HeldItemsResponse();
		Pokemon pokemon = pokemonService.findHeldItems(request.getName());
		response.setPokemon(pokemon);
		return response;
	}

}
