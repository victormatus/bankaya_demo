package com.bankaya.demo.service;

import java.sql.Timestamp;
import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bankaya.demo.Ability;
import com.bankaya.demo.AbilityDetail;
import com.bankaya.demo.HeldItem;
import com.bankaya.demo.Item;
import com.bankaya.demo.Pokemon;
import com.bankaya.demo.RequestRepository;
import com.bankaya.demo.Version;
import com.bankaya.demo.VersionDetail;
import com.bankaya.demo.domain.RequestEntity;
import com.bankaya.demo.util.UtilCommons;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class PokemonService {

	private final RestTemplate pokemonRestTemplate;
	private final RequestRepository requestRepository;
	private final ObjectMapper objectMapper;
	private final HttpServletRequest request;
	public PokemonService(RestTemplate pokemonRestTemplate,
			RequestRepository requestRepository,
			ObjectMapper objectMapper,
			HttpServletRequest request) {
		this.pokemonRestTemplate = pokemonRestTemplate;
		this.requestRepository = requestRepository;
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		this.objectMapper = objectMapper;
		this.request = request;
		
	}

	public Pokemon findAbilities(String name) {
		Pokemon pokemon = new Pokemon();
		try {
			registerTrack("abilities");
			JsonNode jsonNode = findPokemonByName(name);
			if (jsonNode.has("abilities")) {
				JsonNode abilitiesNode = jsonNode.path("abilities");
				abilitiesNode.forEach(abilityNode->{
					Ability ability = new Ability();
					ability.setHidden(abilityNode.get("is_hidden").asBoolean());
					ability.setSlot(abilityNode.get("slot").asInt());
					JsonNode abilityNode_ = abilityNode.path("ability");
					AbilityDetail abilityD = new AbilityDetail();
					abilityD.setName(abilityNode_.get("name").asText());
					abilityD.setUrl(abilityNode_.get("url").asText());
					ability.setAbility(abilityD);
					pokemon.getAbilities().add(ability);
					pokemon.setName(name);
				});
				
			}
		}catch(Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return pokemon;
	}
	
	public Pokemon findBaseExperience(String name) {
		Pokemon pokemon = new Pokemon();
		try {
			registerTrack("base_experience");
			JsonNode jsonNode = findPokemonByName(name);
			if(jsonNode.has("base_experience")) {
				pokemon.setName(name);
				pokemon.setBaseExperience(jsonNode.get("base_experience").asInt());
			}
		}catch(Exception e) {
			log.error(e.getMessage(),e);
		}
		return pokemon;
	}
	
	public Pokemon findName(String name) {
		Pokemon pokemon = new Pokemon();
		try {
			registerTrack("name");
			JsonNode jsonNode = findPokemonByName(name);
			if(jsonNode.has("name")) {
				pokemon.setName(jsonNode.get("name").asText());
			}
		}catch(Exception e) {
			log.error(e.getMessage(),e);
		}
		return pokemon;
	}
	
	public Pokemon findId(String name) {
		Pokemon pokemon = new Pokemon();
		try {
			registerTrack("id");
			JsonNode jsonNode = findPokemonByName(name);
			if(jsonNode.has("id")) {
				pokemon.setName(name);
				pokemon.setId(jsonNode.get("id").asInt());
			}
		}catch(Exception e) {
			log.error(e.getMessage(), e);
		}
		return pokemon;
	}
	
	public Pokemon findLocationAreaEncounters(String name) {
		Pokemon pokemon = new Pokemon();
		try {
			registerTrack("location_area_encounters");
			JsonNode jsonNode = findPokemonByName(name);
			if(jsonNode.has("location_area_encounters")) {
				pokemon.setName(name);
				pokemon.setLocationAreaEncounters(jsonNode.get("location_area_encounters").asText());
			}
		}catch(Exception e) {
			log.error(e.getMessage(),e);
		}
		return pokemon;
	}
	
	public Pokemon findHeldItems(String name) {
		Pokemon pokemon = new Pokemon();
		try {
			registerTrack("held_items");
			JsonNode jsonNode = findPokemonByName(name);
			if(jsonNode.has("held_items")) {
				JsonNode jsonNodeItems = jsonNode.get("held_items");
				jsonNodeItems.forEach(jsonNodeItem->{
					HeldItem heldItem = new HeldItem();
					Item item = new Item();
					log.info("jsonNodeItem: " +  jsonNodeItem.toString());
					
					String itemName = jsonNodeItem.path("item").get("name").asText();
					String itemUrl = jsonNodeItem.path("item").get("url").asText();
					item.setName(itemName);
					item.setUrl(itemUrl);
					heldItem.setItem(item);
					
					for(JsonNode jsonNodeDetail : jsonNodeItem.path("version_details")) {
						VersionDetail versionDetail = new VersionDetail();
						try {
							Integer rarity = jsonNodeDetail.get("rarity").asInt();
							Version version = objectMapper.treeToValue(jsonNodeDetail.get("version"), Version.class);
							versionDetail.setRarity(rarity);
							versionDetail.setVersion(version);
						} catch (JsonProcessingException e) {
							log.error(e.getMessage(), e);
						}
						heldItem.getVersionDetails().add(versionDetail);
					}
					pokemon.getHeldItems().add(heldItem);
				});
			}
		}catch(Exception e) {
			log.error(e.getMessage(),e);
		}
		return pokemon;
	}
	
	private JsonNode findPokemonByName(String name) {
		try {
			ResponseEntity<String> entity = pokemonRestTemplate.exchange("/" + name, HttpMethod.GET, null, String.class);
			String jsonResponse = entity.getBody();
			JsonNode jsonNode = objectMapper.readTree(jsonResponse);
			return jsonNode;
		}catch(Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	private void registerTrack(String methodName) {
		String remoteAddress = UtilCommons.getIp(request);
		RequestEntity requestEntity = new RequestEntity(remoteAddress, methodName, Timestamp.from(Instant.now()));
		try {
			requestRepository.save(requestEntity);
		}catch(Exception e) {
			log.error(e.getMessage(),  e);
		}
	}

}
