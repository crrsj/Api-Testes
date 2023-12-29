package com.apiteste.resource.dto;



import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDto{
	private Integer id;
	private String name;
	private String email;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Acessa para escrita ou seja para nao aparecer como nulo,mas não para leitura
	private String password;
	
	

}
