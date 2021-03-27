package com.bankaya.demo.service.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(value = Include.NON_NULL)
public class PokemonDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7076000613467859776L;
	private String name;
	
	private Integer base_experience;
	
	
}
